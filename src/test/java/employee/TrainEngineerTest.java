import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.train.Train;

public class TrainEngineerTest {

    private TrainEngineer trainEngineer;
    private Train train;

    @Before
    public void setUp() {
        trainEngineer = new TrainEngineer(123);
        train = new Train("Express", "12345");
    }

    @Test
    public void testGetMonthlySalary() {
        // Assuming the base salary for the Employee class is 1000.0
        double expectedSalary = 1.2 * 1000.0;
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
    public void testFixTrain() {
        assertEquals(Train.Status.IN_SERVICE, train.getStatus());

        trainEngineer.fixTrain(train);

        assertEquals(Train.Status.OUT_OF_SERVICE, train.getStatus());
    }

    @Test
    public void testCheckTrainOperatorLine() {
        Admin admin = new Admin(456);
        admin.addEmployeeToLine(1, trainEngineer);

        Integer line = trainEngineer.checkTrainOperatorLine(admin);
        assertEquals(Integer.valueOf(1), line);
    }
}
