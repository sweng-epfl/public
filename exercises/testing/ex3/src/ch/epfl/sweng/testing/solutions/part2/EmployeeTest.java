package ch.epfl.sweng.testing.solutions.part3;

import ch.epfl.sweng.testing.part3.Employee;
import org.junit.Before;
import org.junit.Test;

import static ch.epfl.sweng.testing.part3.Level.*;
import static org.junit.Assert.assertEquals;

public class EmployeeTest {
    private Employee employee;

    @Before
    public void setUp() {
        employee = new Employee("Sarah", 2);
    }


    @Test
    public void getNameTest() {
        assertEquals(employee.getName(), "Sarah");
    }

    @Test
    public void getExperienceTest() {
        assertEquals(employee.getExperience(), 2);
    }

    @Test
    public void getLevelTest() {
        assertEquals(employee.getLevel(), JUNIOR);
    }

    @Test
    public void improveLevelTest() {
        employee.improveLevel();
        assertEquals(employee.getLevel(), SENIOR);

        employee.improveLevel();
        assertEquals(employee.getLevel(), STAFF);

        employee.improveLevel();
        assertEquals(employee.getLevel(), PRINCIPAL);

        employee.improveLevel();
        assertEquals(employee.getLevel(), PRINCIPAL);
    }


}
