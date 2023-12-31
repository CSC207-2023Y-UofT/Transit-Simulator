package entity.employee;

import app_business.common.EmployeeType;
import entity.employee.Admin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    private static Admin admin;

    @BeforeAll
    public static void setUp() {
        admin = new Admin(100, "");
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
        assertTrue(admin.isPaid());

        admin.setPaid(false);
        assertFalse(admin.isPaid());
    }

}
