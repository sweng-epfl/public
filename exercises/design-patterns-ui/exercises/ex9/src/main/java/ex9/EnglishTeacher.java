package ex9;

public class EnglishTeacher implements Teacher {
    @Override
    public void sayHi() {
        System.out.println("Hi!");
    }

    @Override
    public void sayMyName(String myName) {
        System.out.format("My name is %s.", myName);
    }

}
