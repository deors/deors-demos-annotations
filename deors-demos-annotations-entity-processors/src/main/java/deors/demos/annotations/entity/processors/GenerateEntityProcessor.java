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
@SupportedSourceVersion(SourceVersion.RELEASE_9)
public class GenerateEntityProcessor
    extends AbstractProcessor {

    /** String used to append to an entity name when creating the implementation. */
    private static final String IMPL = "Impl";

    /** Package name of the class to generate. */
    private String packageName = "";

    /** Entity name. */
    private String entityName = "";

    /** Name of the Entity implementation class. */
    private String implName = "";

    /** Qualified name of the Entity implementation class. */
    private String qualifiedName = "";

    /** The field list. */
    private final List<String> fieldNames = new ArrayList<>();

    /** Map containing each field type. */
    private final Map<String, String> fieldTypes = new HashMap<>();

    /** Map containing whether a field is part of the entity key. */
    private final Map<String, Boolean> fieldId = new HashMap<>();

    /**
     * Default constructor.
     */
    public GenerateEntityProcessor() {

        super();
    }

    /**
     * Reads the entity information and writes a full featured
     * JavaBean with the help of an Apache Velocity template.
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

        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateEntity.class)) {
            processElement(element);
        }

        return true;
    }

    /**
     * Process an element annotated by the handled Annotation Type.
     *
     * @param element the annotated element to be processed
     */
    private void processElement(Element element) {

        if (element.getKind() != ElementKind.INTERFACE) {
            return;
        }

        TypeElement interfaceElement = (TypeElement) element;
        setEntityTypeInfo(interfaceElement);

        for (Element interfaceMember : interfaceElement.getEnclosedElements()) {

            if (!(interfaceMember instanceof ExecutableElement)) {
                continue;
            }

            ExecutableElement methodElement = (ExecutableElement) interfaceMember;
            setEntityFieldInfo(methodElement);
        }

        generateEntityClass();

        cleanData();
    }

    /**
     * Sets the Entity type information from the information available in the
     * interface and annotation.
     *
     * @param interfaceElement element describing the interface
     */
    private void setEntityTypeInfo(TypeElement interfaceElement) {

        PackageElement packageElement = (PackageElement) interfaceElement.getEnclosingElement();

        packageName = packageElement.getQualifiedName().toString();
        entityName = interfaceElement.getSimpleName().toString();
        implName = entityName + IMPL;
        qualifiedName = interfaceElement.getQualifiedName().toString() + IMPL;

        processingEnv.getMessager().printMessage(
            Diagnostic.Kind.NOTE,
            "annotated interface: " + entityName, interfaceElement);
    }

    /**
     * Sets the Entity field information from the information of methods in the
     * interface and annotations.
     *
     * @param methodElement element describing the method
     */
    private void setEntityFieldInfo(ExecutableElement methodElement) {

        String fieldName = getFieldName(methodElement);
        String fieldType = getFieldType(methodElement);
        boolean id = getFieldId(methodElement);

        if (!fieldNames.contains(fieldName)) {
            fieldNames.add(fieldName);
            fieldTypes.put(fieldName, fieldType);
            fieldId.put(fieldName, id);

            processingEnv.getMessager().printMessage(
                Diagnostic.Kind.NOTE,
                "  found field: " + fieldName + " // type: " + fieldType, methodElement);
        }
    }

    /**
     * Obtains the field name deriving it from the method name.
     *
     * @param methodElement element describing the method
     *
     * @return the field name or a blank string if the field name could not be determined
     */
    private String getFieldName(ExecutableElement methodElement) {

        final String get = "get";
        final String set = "set";
        final String is = "is";

        String fieldName = "";
        String methodName = methodElement.getSimpleName().toString();
        if (methodName.startsWith(get)
            || methodName.startsWith(set)) {
            fieldName = methodName.substring(get.length()).toLowerCase();
        } else if (methodName.startsWith(is)) {
            fieldName = methodName.substring(is.length()).toLowerCase();
        }
        return fieldName;
    }

    /**
     * Obtains the field type deriving it from the method return type
     * or the type of the first parameter.
     *
     * @param methodElement element describing the method
     *
     * @return the field type or a blank string if the field type could not be determined
     */
    private String getFieldType(ExecutableElement methodElement) {

        String fieldType = "";
        // if getter, the field type is the return type
        // if not, look for first parameter
        if (methodElement.getReturnType().equals(void.class)
            && !methodElement.getParameters().isEmpty()) {
            fieldType = methodElement.getParameters().get(0).asType().toString();
        } else {
            fieldType = methodElement.getReturnType().toString();
        }
        return fieldType;
    }

    /**
     * Obtains the field id flag depending on the presence of a GenerateEntity annotation
     * with the id field explicitly set to true.
     *
     * @param methodElement element describing the method
     *
     * @return the field id flag
     */
    private boolean getFieldId(ExecutableElement methodElement) {

        boolean id = false;
        GenerateEntity annotation = methodElement.getAnnotation(GenerateEntity.class);
        if (annotation != null) {
            id = annotation.id();
        }
        return id;
    }

    /**
     * Generates the Entity class using Apache Velocity templates.
     */
    private void generateEntityClass() {

        if (entityName.isEmpty()) {
            return;
        }

        try {
            VelocityEngine ve = initVelocityEngine();

            VelocityContext vc = initVelocityContext();

            populateModel(vc);

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
    }

    /**
     * Initializes and return the Velocity engine.
     *
     * @return the initialized Velocity engine
     * @throws IOException an I/O exception reading the Velocity properties file
     * @throws Exception an exception while initializing the Velocity engine
     */
    private VelocityEngine initVelocityEngine() throws IOException, Exception {

        Properties props = new Properties();
        URL url = this.getClass().getClassLoader().getResource("velocity.properties");
        props.load(url.openStream());

        VelocityEngine ve = new VelocityEngine(props);
        ve.init();
        return ve;
    }

    /**
     * Initializes and return the Velocity context.
     *
     * @return the initialized Velocity context
     */
    private VelocityContext initVelocityContext() {

        VelocityContext vc = new VelocityContext();

        // adding DisplayTool from Velocity Tools library
        vc.put("display", new DisplayTool());
        return vc;
    }

    /**
     * Populates the Velocity context with the model data.
     *
     * @param vc the Velocity context
     */
    private void populateModel(VelocityContext vc) {

        vc.put("packageName", packageName);
        vc.put("entityName", entityName);
        vc.put("implName", implName);
        vc.put("qualifiedName", qualifiedName);
        vc.put("fieldNames", fieldNames);
        vc.put("fieldTypes", fieldTypes);
        vc.put("fieldId", fieldId);
    }

    /**
     * Cleans data in preparation of an eventual new annotated interface.
     */
    private void cleanData() {

        packageName = "";
        entityName = "";
        implName = "";
        qualifiedName = "";
        fieldNames.clear();
        fieldTypes.clear();
        fieldId.clear();
    }
}
