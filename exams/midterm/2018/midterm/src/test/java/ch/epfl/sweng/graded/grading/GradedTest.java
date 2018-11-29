package ch.epfl.sweng.graded.grading;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GradedTest {
    String DEFAULT_NAME = "##DEFAULT";
    String DEFAULT_DESCRIPTION = "##DEFAULT";
    double DEFAULT_POINTS = 1;


    String name() default DEFAULT_NAME;

    String description() default DEFAULT_DESCRIPTION;

    double points() default DEFAULT_POINTS;
}
