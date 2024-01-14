package com.company.processor;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.MethodInfo;

public class LogMethodProcessor {
    public static void main(String[] args) {
        process();
    }

    public static void process() {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.get("com.company.Main");

            for (CtMethod method : ctClass.getDeclaredMethods()) {
                MethodInfo methodInfo = method.getMethodInfo();
                for (AttributeInfo attribute : methodInfo.getAttributes()) {
                    if (attribute instanceof AnnotationsAttribute) {
                        AnnotationsAttribute annotationsAttribute = (AnnotationsAttribute) attribute;
                        if (annotationsAttribute.getAnnotation("com.company.processor.annotation.LogMethod") != null) {
                            method.insertBefore("System.out.println(\"Entering method: " + method.getName() + "\");");
                            method.insertAfter("System.out.println(\"Exiting method: " + method.getName() + "\");", true);
                        }
                    }
                }
            }

            ctClass.writeFile("app-log-method/target/classes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
