package ch.epfl.sweng.grading;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GradedCategory {
    String DEFAULT_NAME = "##DEFAULT";
    String DEFAULT_DESCRIPTION = "##DEFAULT";

    String name() default DEFAULT_NAME;

    String description() default DEFAULT_DESCRIPTION;
}