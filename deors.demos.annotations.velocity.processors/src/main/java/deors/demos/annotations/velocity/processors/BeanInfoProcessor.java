package deors.demos.annotations.velocity.processors;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.generic.DisplayTool;

import deors.demos.annotations.velocity.BeanInfo;

/**
 * Annotation processor for BeanInfo annotation type. It generates a full featured
 * BeanInfo type with the help of an Apache Velocity template.
 *
 * @author deors
 * @version 1.0
 */
@SupportedAnnotationTypes("deors.demos.annotations.velocity.BeanInfo")
@SupportedSourceVersion(SourceVersion.RELEASE_9)
public class BeanInfoProcessor
    extends AbstractProcessor {

    /** String used to append to a class name when creating the BeanInfo class name. */
    private static final String BEAN_INFO = "BeanInfo";

    /**
     * Default constructor.
     */
    public BeanInfoProcessor() {

        super();
    }

    /**
     * Reads the BeanInfo information and writes a full featured
     * BeanInfo type with the help of an Apache Velocity template.
     *
     * @param annotations set of annotations found
     * @param roundEnv the environment for this processor round
     *
     * @return whether a new processor round would be needed
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (annotations.isEmpty()) {
            return true;
        }

        try {
            BeanInfoTypeModel model = null;
            Map<String, BeanInfoPropertyModel> properties = new HashMap<>();
            Map<String, BeanInfoMethodModel> methods = new HashMap<>();

            for (Element e : roundEnv.getElementsAnnotatedWith(BeanInfo.class)) {

                if (e.getKind() == ElementKind.CLASS) {

                    model = new BeanInfoTypeModel();

                    TypeElement classElement = (TypeElement) e;
                    PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();
                    BeanInfo annotation = classElement.getAnnotation(BeanInfo.class);

                    model.packageName = packageElement.getQualifiedName().toString();
                    model.className = classElement.getSimpleName().toString();
                    model.qualifiedName = classElement.getQualifiedName().toString();
                    model.beanInfoClassName = model.className + BEAN_INFO;
                    model.beanInfoQualifiedName = model.qualifiedName + BEAN_INFO;
                    model.description = annotation.description();

                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.NOTE,
                        "annotated class: " + model.qualifiedName, e);

                } else if (e.getKind() == ElementKind.FIELD) {

                    VariableElement varElement = (VariableElement) e;

                    BeanInfoPropertyModel property = new BeanInfoPropertyModel();
                    BeanInfo annotation = varElement.getAnnotation(BeanInfo.class);

                    property.name = varElement.getSimpleName().toString();
                    property.qualifiedType = varElement.asType().toString();
                    property.description = annotation.description();
                    property.expert = annotation.expert();
                    property.hidden = annotation.hidden();
                    property.preferred = annotation.preferred();

                    properties.put(property.name, property);

                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.NOTE,
                        "annotated field: " + property.name + " // field type: " + property.qualifiedType, e);

                } else if (e.getKind() == ElementKind.METHOD) {

                    ExecutableElement exeElement = (ExecutableElement) e;
                    List<? extends VariableElement> paramElements = exeElement.getParameters();
                    BeanInfo annotation = exeElement.getAnnotation(BeanInfo.class);

                    BeanInfoMethodModel method = new BeanInfoMethodModel();
                    List<BeanInfoPropertyModel> parameters = new ArrayList<>();

                    method.name = exeElement.getSimpleName().toString();
                    method.qualifiedType = exeElement.getReturnType().toString();
                    method.description = annotation.description();
                    method.expert = annotation.expert();
                    method.hidden = annotation.hidden();
                    method.preferred = annotation.preferred();
                    method.parameters = parameters;

                    for (VariableElement paramElement : paramElements) {
                        BeanInfoPropertyModel parameter = new BeanInfoPropertyModel();
                        BeanInfo paramAnnotation = paramElement.getAnnotation(BeanInfo.class);

                        parameter.name = paramElement.getSimpleName().toString();
                        parameter.qualifiedType = paramElement.asType().toString();
                        parameter.description = paramAnnotation.description();
                        parameter.expert = paramAnnotation.expert();
                        parameter.hidden = paramAnnotation.hidden();
                        parameter.preferred = paramAnnotation.preferred();

                        parameters.add(parameter);
                    }

                    methods.put(method.name, method);

                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.NOTE,
                        "annotated method: " + method.name + " // return type: " + method.qualifiedType, e);
                    for (BeanInfoPropertyModel parameter : parameters) {
                        processingEnv.getMessager().printMessage(
                            Diagnostic.Kind.NOTE,
                            "parameter: " + parameter.name + " // parameter type: " + parameter.qualifiedType, e);
                    }
                }
            }

            if (model != null) {

                Properties props = new Properties();
                URL url = this.getClass().getClassLoader().getResource("velocity.properties");
                props.load(url.openStream());

                VelocityEngine ve = new VelocityEngine(props);
                ve.init();

                VelocityContext vc = new VelocityContext();

                vc.put("model", model);
                vc.put("properties", properties);
                vc.put("methods", methods);

                // adding DisplayTool from Velocity Tools library
                vc.put("display", new DisplayTool());

                Template vt = ve.getTemplate("beaninfo.vm");

                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                    model.beanInfoQualifiedName);

                processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.NOTE,
                    "creating source file: " + jfo.toUri());

                Writer writer = jfo.openWriter();

                processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.NOTE,
                    "applying velocity template: " + vt.getName());

                vt.merge(vc, writer);

                writer.close();
            }
        } catch (ResourceNotFoundException rnfe) {
            processingEnv.getMessager().printMessage(
                Diagnostic.Kind.ERROR,
                rnfe.getLocalizedMessage());
        } catch (ParseErrorException pee) {
            processingEnv.getMessager().printMessage(
                Diagnostic.Kind.ERROR,
                pee.getLocalizedMessage());
        } catch (IOException ioe) {
            processingEnv.getMessager().printMessage(
                Diagnostic.Kind.ERROR,
                ioe.getLocalizedMessage());
        } catch (Exception e) {
            processingEnv.getMessager().printMessage(
                Diagnostic.Kind.ERROR,
                e.getLocalizedMessage());
        }

        return true;
    }
}
