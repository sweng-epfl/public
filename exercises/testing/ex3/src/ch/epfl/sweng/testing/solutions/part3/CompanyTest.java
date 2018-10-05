package ch.epfl.sweng.testing.solutions.part3;

import ch.epfl.sweng.testing.part3.Company;
import ch.epfl.sweng.testing.part3.Employee;
import org.junit.Before;
import org.junit.Test;

import static ch.epfl.sweng.testing.part3.Level.SENIOR;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CompanyTest {
    private Employee employee;

    private Company company;



    @Before
    public void setUp() {
        employee = new Employee("Marc", 10);

        company = new Company();
    }

    @Test
    public void getEmployeeTest() {
        company.addEmployee(employee);

        assertTrue(company.getEmployees().contains(employee));
    }

    @Test(expected = IllegalStateException.class)
    public void newProjectExceptionTest() {
        company.newProject("Failure");
    }

    @Test
    public void newProjectTest() {
        company.addEmployee(employee);

        company.newProject("E-bank");

        assertFalse(company.getProjects().isEmpty());
        assertTrue(company.getEmployees().isEmpty());
    }

    @Test
    public void projectDeadlineTest() {
        company.addEmployee(employee);

        company.newProject("Sweng is awesome");
        assertFalse(company.getProjects().isEmpty());
        assertTrue(company.getEmployees().isEmpty());

        company.projectsDeadline();
        assertTrue(company.getProjects().isEmpty());
        assertFalse(company.getEmployees().isEmpty());

        assertEquals(employee.getLevel(), SENIOR);
    }


}
