package employee;

import employee.Admin;
import employee.Employee;
import employee.TrainOperator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TrainOperatorTest {

    @Test
    public void TrainOperatorCheckSalary() {
        Employee emp = new TrainOperator(1);
        Assertions.assertEquals(7000, emp.getMonthlySalary());
    }

    @Test
    public void testGetMonthlySalary() {
        TrainOperator trainOperator = new TrainOperator(123);
        double monthlySalary = trainOperator.getMonthlySalary();
        double expectedSalary = 7000.0; // Replace this with the expected salary value
        Assertions.assertEquals(expectedSalary, monthlySalary, 0.001); // You may adjust the delta (0.001) based on precision
    }

    @Test
    public void testSetPaid() {
        TrainOperator trainOperator = new TrainOperator(123);

        trainOperator.setPaid(true);
        Assertions.assertTrue(trainOperator.getPaid()); // Assuming isPaid is a boolean field in the Employee class

        trainOperator.setPaid(false);
        Assertions.assertFalse(trainOperator.getPaid());
    }

}


