package com.example.libnavannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface FragmentDestination {
    String routerUrl();

    boolean asStarter() default false;

    boolean needLogin() default false;
}
