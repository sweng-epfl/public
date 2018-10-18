package ch.epfl.sweng.dp1.ex8;

public class Course {

    protected Teacher getTeacher(){
        return new EnglishTeacher();
    }

    protected void start(){
        Teacher teacher = getTeacher();
        teacher.sayHi();
        teacher.sayMyName("SWeng teacher");
    }

}