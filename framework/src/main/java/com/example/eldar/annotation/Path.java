package com.example.eldar.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Path {

    String value();

    MethodType methodType() default MethodType.GET;
}
