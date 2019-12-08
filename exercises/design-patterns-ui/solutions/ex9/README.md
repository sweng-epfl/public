# DP - UI -- Ex9 - Solutions

You are in charge of implementing a course management system. A `Course` can be in different languages such as English or French. And each `Course` has a main `Teacher`:

```java
public class Course {

    protected Teacher getTeacher(){
        return new EnglishTeacher();
    }

    public void start(){
        Teacher teacher = getTeacher();
        teacher.sayHi();
        teacher.sayMyName("SWeng teacher");
    }

}
```

Currently, you have in your system only English courses. In the future, you want to create French courses, which require French teachers. Use the GOF Factory Method to change the current code to accommodate this future requirement. 

Hint: What you can do is to create a `FrenchCourse` subclass that extends the `Course` class. 