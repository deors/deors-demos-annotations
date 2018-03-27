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

                    model.setPackageName(packageElement.getQualifiedName().toString());
                    model.setClassName(classElement.getSimpleName().toString());
                    model.setQualifiedName(classElement.getQualifiedName().toString());
                    model.setBeanInfoClassName(model.getClassName() + BEAN_INFO);
                    model.setBeanInfoQualifiedName(model.getQualifiedName() + BEAN_INFO);
                    model.setDescription(annotation.description());

                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.NOTE,
                        "annotated class: " + model.getQualifiedName(), e);

                } else if (e.getKind() == ElementKind.FIELD) {

                    VariableElement varElement = (VariableElement) e;

                    BeanInfoPropertyModel property = new BeanInfoPropertyModel();
                    BeanInfo annotation = varElement.getAnnotation(BeanInfo.class);

                    property.setName(varElement.getSimpleName().toString());
                    property.setQualifiedType(varElement.asType().toString());
                    property.setDescription(annotation.description());
                    property.setExpert(annotation.expert());
                    property.setHidden(annotation.hidden());
                    property.setPreferred(annotation.preferred());

                    properties.put(property.getName(), property);

                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.NOTE,
                        "annotated field: " + property.getName() + " // field type: " + property.getQualifiedType(), e);

                } else if (e.getKind() == ElementKind.METHOD) {

                    ExecutableElement exeElement = (ExecutableElement) e;
                    List<? extends VariableElement> paramElements = exeElement.getParameters();
                    BeanInfo annotation = exeElement.getAnnotation(BeanInfo.class);

                    BeanInfoMethodModel method = new BeanInfoMethodModel();
                    List<BeanInfoPropertyModel> parameters = new ArrayList<>();

                    method.setName(exeElement.getSimpleName().toString());
                    method.setQualifiedType(exeElement.getReturnType().toString());
                    method.setDescription(annotation.description());
                    method.setExpert(annotation.expert());
                    method.setHidden(annotation.hidden());
                    method.setPreferred(annotation.preferred());
                    method.setParameters(parameters);

                    for (VariableElement paramElement : paramElements) {
                        BeanInfoPropertyModel parameter = new BeanInfoPropertyModel();
                        BeanInfo paramAnnotation = paramElement.getAnnotation(BeanInfo.class);

                        parameter.setName(paramElement.getSimpleName().toString());
                        parameter.setQualifiedType(paramElement.asType().toString());
                        parameter.setDescription(paramAnnotation.description());
                        parameter.setExpert(paramAnnotation.expert());
                        parameter.setHidden(paramAnnotation.hidden());
                        parameter.setPreferred(paramAnnotation.preferred());

                        parameters.add(parameter);
                    }

                    methods.put(method.getName(), method);

                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.NOTE,
                        "annotated method: " + method.getName() + " // return type: " + method.getQualifiedType(), e);
                    for (BeanInfoPropertyModel parameter : parameters) {
                        processingEnv.getMessager().printMessage(
                            Diagnostic.Kind.NOTE,
                            "parameter: " + parameter.getName() + " // parameter type: " + parameter.getQualifiedType(), e);
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
                    model.getBeanInfoQualifiedName());

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
