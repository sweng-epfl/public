package ex9;

public class FrenchCourse extends Course {
    protected Teacher getTeacher(){
        return new FrenchTeacher();
    }
}
