package employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EmployeeTrackerTest {

    private EmployeeTracker employeeTracker;
    private Employee employee1;
    private Employee employee2;
    private Employee trainOperator;
    private Employee trainEngineer;


    @BeforeEach
    public void setUp() {
        employeeTracker = new EmployeeTracker(dataStore);
        employee1 = new TrainOperator(101);
        employee2 = new TrainEngineer(102);
        trainOperator = new TrainOperator(201);
        trainEngineer = new TrainEngineer(202);

    }

    @Test
    public void testAddToEmployees() {
        assertTrue(employeeTracker.getEmployeeList().isEmpty());

        // Add employees to the tracker
        employeeTracker.addEmployee(employee1);
        employeeTracker.addEmployee(employee2);
        employeeTracker.addEmployee(trainOperator);

        assertEquals(3, employeeTracker.getEmployeeList().size());
        assertTrue(employeeTracker.getEmployeeList().contains(employee1));
        assertTrue(employeeTracker.getEmployeeList().contains(employee2));
        assertTrue(employeeTracker.getEmployeeList().contains(trainOperator));

    }

    @Test
    public void testGetEmployeeList() {
        assertTrue(employeeTracker.getEmployeeList().isEmpty());

        // Add employees to the tracker
        employeeTracker.addEmployee(employee1);
        employeeTracker.addEmployee(employee2);
        employeeTracker.addEmployee(trainOperator);

        // Verify the list of employees
        assertEquals(3, employeeTracker.getEmployeeList().size());
        assertTrue(employeeTracker.getEmployeeList().contains(employee1));
        assertTrue(employeeTracker.getEmployeeList().contains(employee2));
        assertTrue(employeeTracker.getEmployeeList().contains(trainOperator));

        // Add more employees to the tracker
        employeeTracker.addEmployee(trainEngineer);

        // Verify the updated list of employees
        assertEquals(4, employeeTracker.getEmployeeList().size());
        assertTrue(employeeTracker.getEmployeeList().contains(trainEngineer));

    }
}
