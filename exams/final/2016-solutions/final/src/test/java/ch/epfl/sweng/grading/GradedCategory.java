package ch.epfl.sweng.grading;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.epfl.sweng.grading.models.GradingPoints;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GradedCategory {
    String DEFAULT_NAME = "##DEFAULT";
    String DEFAULT_DESCRIPTION = "##DEFAULT";
    String DEFAULT_GROUP = "##DEFAULT";

    String name() default DEFAULT_NAME;

    String description() default DEFAULT_DESCRIPTION;

    String group() default DEFAULT_GROUP;

    GradingPoints.PointsFormat pointsFormat() default GradingPoints.PointsFormat.DETAILED;
}
