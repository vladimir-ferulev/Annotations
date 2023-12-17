package com.company.processor;

import com.company.processor.annotation.Implements;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes("com.company.processor.annotation.Implements")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class ImplementsProcessor extends AbstractProcessor {

    private static final String packageName = "com.company.generated";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                String className = element.getAnnotation(Implements.class).className();
                try {
                    Writer file = processingEnv.getFiler().createSourceFile(packageName + "." + className).openWriter();
                    file.write(String.format("package %s; import %s; public class %s implements %s {}",
                            packageName, element, className, element.getSimpleName()));
                    file.flush();
                    file.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return true;
    }
}