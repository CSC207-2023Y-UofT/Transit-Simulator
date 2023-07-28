import employee.Employee;
import employee.TrainOperator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TrainOperatorTest {

    @Test
    public void TrainOperatorCheckSalary() {
        Employee emp = new TrainOperator("Lack", 001);
        Assertions.assertEquals(7000, emp.getBaseSalary());
    }
}
