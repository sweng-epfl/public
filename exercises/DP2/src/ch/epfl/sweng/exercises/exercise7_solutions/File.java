package ch.epfl.sweng.exercises.exercise7_solutions;

public interface File {

    public String read(String fileName);
    public void write(String fileName, String data);
}
