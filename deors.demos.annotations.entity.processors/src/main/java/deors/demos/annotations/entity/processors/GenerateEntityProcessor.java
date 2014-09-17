package deors.demos.annotations.entity.processors;

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
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.generic.DisplayTool;

import deors.demos.annotations.entity.GenerateEntity;

/**
 * Annotation processor for GenerateEntity annotation type. It generates a full featured
 * JavaBean from an interface containing only getter and setter declarations.
 *
 * @author deors
 * @version 1.0
 */
@SupportedAnnotationTypes("deors.demos.annotations.entity.GenerateEntity")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class GenerateEntityProcessor
    extends AbstractProcessor {

    /** String used to append to an entity name when creating the implementation. */
    private static final String IMPL = "Impl";

    /**
     * Default constructor.
     */
    public GenerateEntityProcessor() {

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
            String packageName = "";
            String entityName = "";
            String implName = "";
            String qualifiedName = "";
            List<String> fieldNames = new ArrayList<>();
            Map<String, String> fieldTypes = new HashMap<>();
            Map<String, Boolean> fieldId = new HashMap<>();

            for (Element e : roundEnv.getElementsAnnotatedWith(GenerateEntity.class)) {

                if (e.getKind() == ElementKind.INTERFACE) {

                    TypeElement interfaceElement = (TypeElement) e;
                    PackageElement packageElement = (PackageElement) interfaceElement.getEnclosingElement();

                    packageName = packageElement.getQualifiedName().toString();
                    entityName = interfaceElement.getSimpleName().toString();
                    implName = entityName + IMPL;
                    qualifiedName = interfaceElement.getQualifiedName().toString() + IMPL;

                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.NOTE,
                        "annotated interface: " + entityName, e);

                    for (Element member : interfaceElement.getEnclosedElements()) {

                        if (!(member instanceof ExecutableElement)) {
                            continue;
                        }

                        ExecutableElement method = (ExecutableElement) member;
                        GenerateEntity annotation = method.getAnnotation(GenerateEntity.class);

                        String fieldName = "";
                        String methodName = method.getSimpleName().toString();
                        if (methodName.startsWith("get")
                            || methodName.startsWith("set")) {
                            fieldName = methodName.substring(3).toLowerCase();
                        } else if (methodName.startsWith("is")) {
                            fieldName = methodName.substring(2).toLowerCase();
                        } else {
                            continue;
                        }

                        String fieldType = "";
                        // if getter we know the field type is the return type
                        // if not, look for first parameter
                        if (method.getReturnType().equals(void.class)) {
                            if (method.getParameters().isEmpty()) {
                                continue;
                            }
                            fieldType = method.getParameters().get(0).asType().toString();
                        } else {
                            fieldType = method.getReturnType().toString();
                        }

                        boolean id = false;
                        if (annotation != null) {
                            id = annotation.id();
                        }

                        if (!fieldNames.contains(fieldName)) {
                            fieldNames.add(fieldName);
                            fieldTypes.put(fieldName, fieldType);
                            fieldId.put(fieldName, id);
                        }
                    }
                }
            }

            if (!entityName.isEmpty()) {

                Properties props = new Properties();
                URL url = this.getClass().getClassLoader().getResource("velocity.properties");
                props.load(url.openStream());

                VelocityEngine ve = new VelocityEngine(props);
                ve.init();

                VelocityContext vc = new VelocityContext();

                vc.put("packageName", packageName);
                vc.put("entityName", entityName);
                vc.put("implName", implName);
                vc.put("fieldNames", fieldNames);
                vc.put("fieldTypes", fieldTypes);
                vc.put("fieldId", fieldId);

                // adding DisplayTool from Velocity Tools library
                vc.put("display", new DisplayTool());

                Template vt = ve.getTemplate("entity.vm");

                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(qualifiedName);

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
