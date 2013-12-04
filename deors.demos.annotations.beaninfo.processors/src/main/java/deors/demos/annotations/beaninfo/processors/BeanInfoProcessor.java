package deors.demos.annotations.beaninfo.processors;

import java.io.BufferedWriter;
import java.io.IOException;
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
import javax.tools.JavaFileObject;

import deors.demos.annotations.beaninfo.BeanInfo;

@SupportedAnnotationTypes("deors.demos.annotations.beaninfo.BeanInfo")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class BeanInfoProcessor
    extends AbstractProcessor {

    public BeanInfoProcessor() {

        super();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        BufferedWriter bw = null;

        try {
            for (Element e : roundEnv.getElementsAnnotatedWith(BeanInfo.class)) {

                if (e.getKind() == ElementKind.CLASS) {

                    TypeElement classElement = (TypeElement) e;
                    PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.NOTE,
                        "annotated class: " + classElement.getQualifiedName(), e);

                    JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                        classElement.getQualifiedName() + "BeanInfo");

                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.NOTE,
                        "creating source file: " + jfo.toUri(), e);

                    bw = new BufferedWriter(jfo.openWriter());

                    bw.append("package ");
                    bw.append(packageElement.getQualifiedName());
                    bw.append(";");
                    bw.newLine();
                    bw.newLine();

                    bw.append("public class ");
                    bw.append(classElement.getSimpleName());
                    bw.append("BeanInfo extends java.beans.SimpleBeanInfo {");
                    bw.newLine();

                } else if (e.getKind() == ElementKind.FIELD && bw != null) {

                    VariableElement varElement = (VariableElement) e;

                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.NOTE,
                        "annotated field: " + varElement.getSimpleName(), e);

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
            }

            // class is finished
            if (bw != null) {

                bw.append("}");
                bw.newLine();
                bw.newLine();
            }
        } catch (IOException ioe) {
            processingEnv.getMessager().printMessage(
                Diagnostic.Kind.ERROR,
                "i/o error: " + ioe.getLocalizedMessage());
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe) {
                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        "i/o error: " + ioe.getLocalizedMessage());
                }
            }
        }

        return true;
    }
}
