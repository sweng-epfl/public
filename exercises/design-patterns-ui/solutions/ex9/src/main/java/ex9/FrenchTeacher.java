package ex9;

public class FrenchTeacher implements Teacher {
    @Override
    public void sayHi() {
        System.out.println("Salut!");
    }

    @Override
    public void sayMyName(String myName) {
        System.out.format("Je m'appelle %s.\n", myName);
    }
}
