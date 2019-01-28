package com.example.util.exampledemoutils.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldAttributes {

    boolean nullable() default true;

    int min() default Integer.MIN_VALUE;

    int max() default Integer.MAX_VALUE;

    String[] dateFormat() default {};

    int size() default 0;

    double minValue() default -Double.MAX_VALUE; // only support int, double & float

    double maxValue() default Double.MAX_VALUE; // only support int, double & float

    String[] allowableValues() default {};
}