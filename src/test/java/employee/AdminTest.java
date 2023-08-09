package employee;

import interactor.employee.EmployeeType;
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
        admin = new Admin(100, "");
        employee1 = new TrainOperator(101, "");
        employee2 = new TrainOperator(102, "");
    }

    @Test
    public void testGetMonthlySalary() {
        double expectedSalary = 4 * 7000.0;
        assertEquals(expectedSalary, admin.getMonthlySalary(), 0.001);
    }

    @Test
    public void testGetEmployeeType() {
        assertEquals(EmployeeType.ADMINISTRATOR, admin.getEmployeeType());
    }

    @Test
    public void testSetPaid() {
        admin.setPaid(true);
        assertTrue(admin.getPaymentStatus());

        admin.setPaid(false);
        assertFalse(admin.getPaymentStatus());
    }

}
