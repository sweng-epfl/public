package sweng;

import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        CSVReader reader = new CSVReader("res/students.txt", new StudentCache());
        List<String> emails = reader.read(10).stream().map(Student::getEmail).collect(Collectors.toList());
        System.out.println(emails);
    }
}
