package com.company;

import com.company.processor.annotation.LogMethod;

public class Main {
    public static void main(String[] args) {
        method();
    }

    @LogMethod
    public static void method() {
        System.out.println("Hello world!");
    }
}