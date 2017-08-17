package deors.demos.annotations.entity.processors;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
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
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.tools.generic.DisplayTool;

import deors.demos.annotations.entity.GenerateDataAccess;

/**
 * Annotation processor for GenerateDataAccess annotation type. It generates a
 * data access object with basic CRUD methods and any custom queries that
 * may be defined in the annotated interface.
 *
 * @author deors
 * @version 1.0
 */
@SupportedAnnotationTypes("deors.demos.annotations.entity.GenerateDataAccess")
@SupportedSourceVersion(SourceVersion.RELEASE_9)
public class GenerateDataAccessProcessor
    extends AbstractProcessor {

    /** String used to append to an entity name when creating the implementation. */
    private static final String IMPL = "Impl";

    /** Package name of the class to generate. */
    private String packageName = "";

    /** Data Access object name. */
    private String dataAccessName = "";

    /** Name of the Data Access implementation class. */
    private String implName = "";

    /** Qualified name of the Data Access implementation class. */
    private String qualifiedName = "";

    /** The query method list. */
    private final List<String> queryNames = new ArrayList<>();

    /** Map containing each query string. */
    private final Map<String, String> queryStrings = new HashMap<>();

    /** Entity type name (fully qualified). */
    private String entityType = "";

    /** Entity name (class name only). */
    private String entityName = "";

    /** Key type name (fully qualified). */
    private String keyType = "";

    /** Key name (class name only). */
    private String keyName = "";

    /**
     * Default constructor.
     */
    public GenerateDataAccessProcessor() {

        super();
    }

    /**
     * Reads the annotated interface information and writes a full featured
     * data access object with the help of an Apache Velocity template.
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

        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateDataAccess.class)) {
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
        setDataAccessTypeInfo(interfaceElement);

        for (Element interfaceMember : interfaceElement.getEnclosedElements()) {

            if (!(interfaceMember instanceof ExecutableElement)) {
                continue;
            }

            ExecutableElement methodElement = (ExecutableElement) interfaceMember;
            setDataAccessQueryInfo(methodElement);
        }

        generateDataAccessClass();

        cleanData();
    }

    /**
     * Sets the Data Access type information from the information available in the
     * interface and annotation.
     *
     * @param interfaceElement element describing the interface
     */
    private void setDataAccessTypeInfo(TypeElement interfaceElement) {

        PackageElement packageElement = (PackageElement) interfaceElement.getEnclosingElement();

        packageName = packageElement.getQualifiedName().toString();
        dataAccessName = interfaceElement.getSimpleName().toString();
        implName = dataAccessName + IMPL;
        qualifiedName = interfaceElement.getQualifiedName().toString() + IMPL;

        processingEnv.getMessager().printMessage(
            Diagnostic.Kind.NOTE,
            "annotated interface: " + dataAccessName, interfaceElement);

        List<? extends TypeMirror> superInterfaces = interfaceElement.getInterfaces();
        if (superInterfaces.isEmpty()) {
            return;
        }

        setEntityAndKeyTypeInfo(superInterfaces.get(0));
    }

    /**
     * Sets the entity and primary key types information by parsing the given
     * interface full name.
     *
     * @param iface the interface type to be parsed
     */
    private void setEntityAndKeyTypeInfo(TypeMirror iface) {

        Scanner s = new Scanner(iface.toString());
        s.useDelimiter("<|,|>");
        if (s.hasNext()) {
            // ignoring interface name
            s.next();
        }
        if (s.hasNext()) {
            // entity name is the first parameterized type
            entityType = s.next();
            entityName = entityType.substring(entityType.lastIndexOf('.') + 1);
        }
        if (s.hasNext()) {
            // key type is the second parameterized type
            keyType = s.next();
            keyName = keyType.substring(keyType.lastIndexOf('.') + 1);
        }
        s.close();
    }

    /**
     * Sets the custom query method information from the information of methods in the
     * interface and annotations.
     *
     * @param methodElement element describing the method
     */
    private void setDataAccessQueryInfo(ExecutableElement methodElement) {

        String queryName = methodElement.getSimpleName().toString();
        String queryString = methodElement.getAnnotation(GenerateDataAccess.class).value();

        if (!queryNames.contains(queryName)) {
            queryNames.add(queryName);
            queryStrings.put(queryName, queryString);

            processingEnv.getMessager().printMessage(
                Diagnostic.Kind.NOTE,
                "  found query method: " + queryName + " // query: " + queryString, methodElement);
        }
    }

    /**
     * Generates the Data Access class using Apache Velocity templates.
     */
    private void generateDataAccessClass() {

        if (dataAccessName.isEmpty() || entityType.isEmpty() || keyType.isEmpty()) {
            return;
        }

        try {
            VelocityEngine ve = initVelocityEngine();

            VelocityContext vc = initVelocityContext();

            populateModel(vc);

            Template vt = ve.getTemplate("dataaccess.vm");

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
        vc.put("dataAccessName", dataAccessName);
        vc.put("implName", implName);
        vc.put("qualifiedName", qualifiedName);
        vc.put("queryNames", queryNames);
        vc.put("queryStrings", queryStrings);
        vc.put("entityType", entityType);
        vc.put("entityName", entityName);
        vc.put("keyType", keyType);
        vc.put("keyName", keyName);
    }

    /**
     * Cleans data in preparation of an eventual new annotated interface.
     */
    private void cleanData() {

        packageName = "";
        dataAccessName = "";
        implName = "";
        qualifiedName = "";
        queryNames.clear();
        queryStrings.clear();
        entityType = "";
        entityName = "";
        keyType = "";
        keyName = "";
    }
}
