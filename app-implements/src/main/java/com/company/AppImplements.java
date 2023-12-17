package com.company;

import com.company.generated.FirstInterfaceImpl;
import com.company.generated.SecondInterfaceImpl;

public class AppImplements {
    public static void main(String[] args) {
        System.out.println(new FirstInterfaceImpl());
        System.out.println(new SecondInterfaceImpl());
    }
}