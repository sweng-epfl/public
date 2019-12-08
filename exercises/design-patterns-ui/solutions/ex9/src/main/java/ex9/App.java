package ex9;

public class App {
    public static void main(String[] args) {
        // English course
        Course course = new Course();
        course.start();

        // French course
        Course frenchCourse = new FrenchCourse();
        frenchCourse.start();
    }
}
