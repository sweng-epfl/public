package ch.epfl.sweng.dp1.solutions.ex8;

public class FrenchCourse extends Course {
    protected Teacher getTeacher(){
        return new FrenchTeacher();
    }
}
