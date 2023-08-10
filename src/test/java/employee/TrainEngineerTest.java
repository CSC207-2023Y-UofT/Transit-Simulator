package employee;

import entity.employee.TrainEngineer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainEngineerTest {

    private TrainEngineer trainEngineer;

    @BeforeEach
    public void setUp() {
        trainEngineer = new TrainEngineer(123, "");
    }

    @Test
    public void testGetMonthlySalary() {
        double expectedSalary = 1.2 * 7000.0;
        assertEquals(expectedSalary, trainEngineer.getMonthlySalary(), 0.001);
    }

    @Test
    public void testSetPaid() {
        trainEngineer.setPaid(true);
        assertTrue(trainEngineer.getPaymentStatus());

        trainEngineer.setPaid(false);
        assertFalse(trainEngineer.getPaymentStatus());
    }

}
