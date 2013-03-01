package com.retro.core.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlDateFormat {

    /**
     * The custom pattern to use to format the field.
     * Defaults to empty String, indicating no custom pattern String has been specified.
     * Set this attribute when you wish to format your field in accordance with a custom date time pattern not represented by a style or ISO format.
     */
    String pattern() default "";
}