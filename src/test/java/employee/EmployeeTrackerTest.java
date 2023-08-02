package employee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class EmployeeTrackerTest {

    private EmployeeTracker employeeTracker;

    @BeforeEach
    public void setUp() {
        employeeTracker = new EmployeeTracker();
    }

    @Test
    public void testAddToEmployees() {
        Employee employee1 = new TrainOperator(327);
        Employee employee2 = new TrainEngineer(328);

        // Add employees to the list
        employeeTracker.addToEmployees(employee1);
        employeeTracker.addToEmployees(employee2);

        List<Employee> employeeList = employeeTracker.getEmployeeList();

        // Check if the list contains the added employees
        Assertions.assertTrue(employeeList.contains(employee1));
        Assertions.assertTrue(employeeList.contains(employee2));
    }

    @Test
    public void testGetEmployeeList() {
        Employee employee1 = new TrainOperator(250);
        Employee employee2 = new Admin(251);

        // Add employees to the list
        employeeTracker.addToEmployees(employee1);
        employeeTracker.addToEmployees(employee2);

        List<Employee> employeeList = employeeTracker.getEmployeeList();

        // Check if the retrieved list matches the added employees
        Assertions.assertEquals(2, employeeList.size());
        Assertions.assertTrue(employeeList.contains(employee1));
        Assertions.assertTrue(employeeList.contains(employee2));
    }
}
