package com.example.libnavannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface ActivityDestination {

    String routerUrl();

    boolean needLogin() default false;

    boolean asStarter() default false;
}

