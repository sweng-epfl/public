package ch.epfl.sweng.testing.part3;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private List<Project> projects;
    private List<Employee> employees;

    public Company() {
        this.projects = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public List<Project> getProjects() {
        return new ArrayList<>(projects);
    }

    public void newProject(String name) {
        if (employees.size() == 0) {
            throw new IllegalStateException("No employees to work on the project");
        }

        projects.add(new Project(name, employees));
        employees.clear();
    }

    public void projectsDeadline() {
        for (Project p : projects) {
            for (Employee e : p.getWorkers()) {
                if (p.isSuccessfull()) {
                    e.improveLevel();
                }
                employees.add(e);
            }
        }
        projects.clear();
    }
}
