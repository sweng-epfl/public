package ch.epfl.sweng.dp1.solutions.ex8;

public class Course {

    protected Teacher getTeacher(){
        return new EnglishTeacher();
    }

    protected void start(){
        Teacher teacher = getTeacher();
        teacher.sayHi();
        teacher.sayMyName("SwEng teacher");
    }

}
