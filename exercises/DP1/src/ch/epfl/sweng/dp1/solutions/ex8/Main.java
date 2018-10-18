package ch.epfl.sweng.dp1.solutions.ex8;

public class Main {
    public static void main(String[] args) {
        // English course
        Course course = new Course();
        course.start();

        // French course
        Course frenchCourse = new FrenchCourse();
        frenchCourse.start();
    }
}