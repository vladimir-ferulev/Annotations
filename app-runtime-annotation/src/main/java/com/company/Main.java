package com.company;

import com.company.annotation.OutputInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Set<Class> allClassesUsingClassLoader = findAllClassesUsingClassLoader("com.company.classes");
        for (Class aClass : allClassesUsingClassLoader) {
            processAnnotations(aClass);
        }
    }

    public static void processAnnotations(Class aClass) {
        if (aClass.getAnnotation(OutputInfo.class) != null) {
            System.out.println("Class " + aClass.getName() + " has annotation OutputInfo") ;
        }
    }

    public static Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    private static Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }
}