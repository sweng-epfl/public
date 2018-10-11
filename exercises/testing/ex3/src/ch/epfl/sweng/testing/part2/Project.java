package ch.epfl.sweng.testing.part3;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private List<Employee> employees;

    public Project(String name, List<Employee> employees) {
        this.name = name;
        this.employees = new ArrayList<>(employees);
    }

    public String getName() {
        return name;
    }

    public List<Employee> getWorkers() {
        return employees;
    }

    public boolean isSuccessfull() {
        int experiencedWorkers = 0;

        for(Employee e : employees) {
            experiencedWorkers += ((e.getLevel().ordinal() > 0 || e.getExperience() >= 2) ? 1 : 0);
        }

        return ((experiencedWorkers >= employees.size()/2) || name.startsWith("Sweng"));
    }
}
