package ch.epfl.sweng.testing.solutions.part3;

import ch.epfl.sweng.testing.part3.Employee;
import ch.epfl.sweng.testing.part3.Project;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProjectTest {
    private Employee firstEmployee;
    private Employee secondEmployee;
    private Employee thirdEmployee;
    private Employee fourthEmployee;

    private List<Employee> employees;
    private Project sweng;
    private Project success;
    private Project fail;

    @Before
    public void setUp() {
        firstEmployee = new Employee("Marc", 1);
        secondEmployee = new Employee("Jean", 0);
        thirdEmployee = new Employee("Elise", 2);
        fourthEmployee = new Employee("John", 5);

        employees = new ArrayList<>();

        employees.add(firstEmployee);
        employees.add(secondEmployee);

        fail = new Project("Shopping", employees);

        employees.add(thirdEmployee);

        sweng = new Project("Sweng is awesome", new ArrayList<Employee>());

        employees.add(fourthEmployee);

        success = new Project("Bitcoin", employees);

    }

    @Test
    public void getNameTest() {
        assertEquals(sweng.getName(), "Sweng is awesome");
    }

    @Test
    public void getWorkersTest() {
        List<Employee> get = fail.getWorkers();

        assertTrue(get.contains(employees.get(0)));
        assertTrue(get.contains(employees.get(1)));
    }

    @Test
    public void isSuccessfullTest() {
        assertTrue(sweng.isSuccessfull());
        assertTrue(success.isSuccessfull());
        assertFalse(fail.isSuccessfull());
    }
}
