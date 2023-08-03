package employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainEngineerTest {

    private TrainEngineer trainEngineer;

    @BeforeEach
    public void setUp() {
        trainEngineer = new TrainEngineer(123);
    }

    @Test
    public void testGetMonthlySalary() {
        double expectedSalary = 1.2 * 7000.0;
        assertEquals(expectedSalary, trainEngineer.getMonthlySalary(), 0.001);
    }

    @Test
    public void testSetPaid() {
        trainEngineer.setPaid(true);
        assertTrue(trainEngineer.getPaid());

        trainEngineer.setPaid(false);
        assertFalse(trainEngineer.getPaid());
    }

    @Test
    public void testCheckTrainOperatorLine() {
        Admin admin = new Admin(456);
        admin.addEmployeeToLine(1, trainEngineer);

        Integer line = trainEngineer.checkTrainOperatorLine(admin);
        assertEquals(Integer.valueOf(1), line);
    }
}
