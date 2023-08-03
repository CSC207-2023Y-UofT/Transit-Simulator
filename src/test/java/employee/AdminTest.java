package employee;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    private static Admin admin;
    private static Employee employee1;
    private static Employee employee2;

    @BeforeAll
    public static void setUp() {
        admin = new Admin(1);
        employee1 = new TrainOperator(101);
        employee2 = new TrainOperator(102);
    }

    @Test
    public void testGetMonthlySalary() {
        double expectedSalary = 4 * 7000.0;
        assertEquals(expectedSalary, admin.getMonthlySalary(), 0.001);
    }

    @Test
    public void testSetPaid() {
        admin.setPaid(true);
        assertTrue(admin.getPaid());

        admin.setPaid(false);
        assertFalse(admin.getPaid());
    }

    @Test
    public void testPayEmployee() {
        assertFalse(employee1.getPaid());
        assertFalse(employee2.getPaid());

        admin.payEmployee(employee1);
        admin.payEmployee(employee2);

        assertTrue(employee1.getPaid());
        assertTrue(employee2.getPaid());
    }

    @Test
    public void testAddEmployeeToLine() {
        admin.clearLineToStaff();
        admin.addEmployeeToLine(1, employee1);
        admin.addEmployeeToLine(1, employee2);

        List<Employee> employeesOnLine1 = admin.getLineToStaff().get(1);
        assertNotNull(employeesOnLine1);
        assertEquals(2, employeesOnLine1.size());
        assertTrue(employeesOnLine1.contains(employee1));
        assertTrue(employeesOnLine1.contains(employee2));
    }

    @Test
    public void testCheckLine() {
        admin.addEmployeeToLine(1, employee1);
        admin.addEmployeeToLine(2, employee2);

        Integer lineForEmployee1 = admin.checkLine(employee1);
        Integer lineForEmployee2 = admin.checkLine(employee2);

        assertEquals(Integer.valueOf(1), lineForEmployee1);
        assertEquals(Integer.valueOf(2), lineForEmployee2);
    }

    @Test
    public void testClearLineToStaff() {
        // Add employees to lines
        admin.addEmployeeToLine(1, employee1);
        admin.addEmployeeToLine(2, employee2);
        admin.addEmployeeToLine(1, employee2);

        // Verify that the lines are not empty before clearing
        assertFalse(admin.getLineToStaff().isEmpty());
        assertNotNull(admin.getLineToStaff().get(1));
        assertNotNull(admin.getLineToStaff().get(2));

        // Clear the lineToStaff mapping
        admin.clearLineToStaff();

        // Verify that the lines are empty after clearing
        assertTrue(admin.getLineToStaff().isEmpty());
        assertNull(admin.getLineToStaff().get(1));
        assertNull(admin.getLineToStaff().get(2));
    }

    @Test
    public void testGetEmployeeOnLine() {
        // Add employees to lines
        admin.addEmployeeToLine(1, employee1);
        admin.addEmployeeToLine(2, employee2);
        admin.addEmployeeToLine(1, employee2);

        // get the employees on line 1
        List<Employee> emp = admin.getEmployeeOnLine(1);

        // verify that the employees on line 1 are correct
        assertEquals(101, emp.get(0).getStaffNumber());
        assertEquals(102, emp.get(1).getStaffNumber());

    }

}
