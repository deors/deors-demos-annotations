package deors.demos.annotations.beaninfo.processors;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import deors.demos.annotations.beaninfo.BeanInfo;

/**
 * Annotation processor for BeanInfo annotation type. It generates a simple skeleton
 * of a BeanInfo type by directly writing to the file output stream.
 *
 * @author deors
 * @version 1.0
 */
@SupportedAnnotationTypes("deors.demos.annotations.beaninfo.BeanInfo")
@SupportedSourceVersion(SourceVersion.RELEASE_9)
public class BeanInfoProcessor
    extends AbstractProcessor {

    /**
     * Default constructor.
     */
    public BeanInfoProcessor() {

        super();
    }

    /**
     * Reads the BeanInfo information and writes a simple skeleton for the
     * BeanInfo class by directly writing to the file output stream.
     *
     * @param annotations set of annotations found
     * @param roundEnv the environment for this processor round
     *
     * @return whether a new processor round would be needed
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        BufferedWriter bw = null;

        try {
            for (Element e : roundEnv.getElementsAnnotatedWith(BeanInfo.class)) {

                if (e.getKind() == ElementKind.CLASS) {

                    TypeElement classElement = (TypeElement) e;
                    PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

                    log(Diagnostic.Kind.NOTE,
                        "annotated class: " + classElement.getQualifiedName(), e);

                    JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                        classElement.getQualifiedName() + "BeanInfo");

                    log(Diagnostic.Kind.NOTE,
                        "creating source file: " + jfo.toUri(), e);

                    bw = new BufferedWriter(jfo.openWriter());

                    writeTypeStart(bw, classElement, packageElement);

                } else if (e.getKind() == ElementKind.FIELD && bw != null) {

                    VariableElement varElement = (VariableElement) e;

                    log(Diagnostic.Kind.NOTE,
                        "annotated field: " + varElement.getSimpleName(), e);

                    writePropertyDescriptor(bw, varElement);
                }
            }

            // class is finished
            if (bw != null) {
                writeTypeEnd(bw);
            }
        } catch (IOException ioe) {
            log(Diagnostic.Kind.ERROR,
                "i/o error writing the BeanInfo class: " + ioe.getLocalizedMessage());
        } finally {
            closeWriter(bw);
        }

        return true;
    }

    /**
     * Writes the BeanInfo class start.
     *
     * @param bw the writer object pointing to the target file
     * @param classElement the class element
     * @param packageElement the package element
     *
     * @throws IOException an I/O error while writing the file contents
     */
    private void writeTypeStart(BufferedWriter bw, TypeElement classElement,
                                PackageElement packageElement) throws IOException {

        bw.append("package ");
        bw.append(packageElement.getQualifiedName());
        bw.append(";");
        bw.newLine();
        bw.newLine();
        bw.append("import javax.annotation.processing.Generated;");
        bw.newLine();
        bw.newLine();
        bw.append("@Generated(value=\"" + this.getClass().getName() + "\" , date = \"" + LocalDateTime.now() + 
        		"\", comments = \"Test generator\")");
        bw.newLine();
        bw.append("public class ");
        bw.append(classElement.getSimpleName());
        bw.append("BeanInfo extends java.beans.SimpleBeanInfo {");
        bw.newLine();
    }

    /**
     * Writes a skeleton for a Bean Info property descriptor.
     *
     * @param bw the writer object pointing to the target file
     * @param varElement the variable element
     *
     * @throws IOException an I/O error while writing the file contents
     */
    private void writePropertyDescriptor(BufferedWriter bw, VariableElement varElement)
        throws IOException {

        bw.newLine();

        bw.append("    public java.beans.PropertyDescriptor ");
        bw.append(varElement.getSimpleName());
        bw.append("PropertyDescriptor() {");
        bw.newLine();
        bw.newLine();

        bw.append("        java.beans.PropertyDescriptor theDescriptor = null;");
        bw.newLine();

        bw.append("        return theDescriptor;");
        bw.newLine();

        bw.append("    }");
        bw.newLine();
    }

    /**
     * Writes the BeanInfo class end.
     *
     * @param bw the writer object pointing to the target file
     *
     * @throws IOException an I/O error while writing the file contents
     */
    private void writeTypeEnd(BufferedWriter bw) throws IOException {

        bw.append("}");
        bw.newLine();
        bw.newLine();
    }

    /**
     * Closes the given writer object.
     *
     * @param bw the writer object to be closed.
     */
    private void closeWriter(Writer bw) {

        if (bw != null) {
            try {
                bw.close();
            } catch (IOException ioe) {
                log(Diagnostic.Kind.ERROR,
                    "i/o error closing an opened file: " + ioe.getLocalizedMessage());
            }
        }
    }

    /**
     * Logs a message.
     *
     * @param kind the message kind (priority level)
     * @param message the message text
     */
    private void log(Kind kind, String message) {

        processingEnv.getMessager().printMessage(kind, message);
    }

    /**
     * Logs a message associated with some processing element.
     *
     * @param kind the message kind (priority level)
     * @param message the message text
     * @param e the processing element
     */
    private void log(Kind kind, String message, Element e) {

        processingEnv.getMessager().printMessage(kind, message, e);
    }
}
