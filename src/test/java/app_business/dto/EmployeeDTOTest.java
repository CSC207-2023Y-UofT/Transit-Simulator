package app_business.dto;

import app_business.common.EmployeeAssignment;
import app_business.common.EmployeeType;
import entity.model.train.TrainRole;

import org.junit.jupiter.api.*;

/**
 * The EmployeeDTOTest class contains tests for the EmployeeDTO class. A test for a data class.
 */
public class EmployeeDTOTest {
    static EmployeeDTO employeeDTO;
    static int staffNumber;
    static String name;
    static EmployeeType type;
    static EmployeeAssignment assignment;

    @DisplayName("EmployeeDTOTest Class Setup")
    @BeforeAll
    static void setup() {
        staffNumber = 99;
        name = "Matthew Lack";
        type = EmployeeType.OPERATOR;
        assignment = new EmployeeAssignment("Train 1", TrainRole.OPERATOR);
        employeeDTO = new EmployeeDTO(staffNumber, name, type, assignment);  // convenient test constructor
    }

    @Test
    void testGetStaffNumber() {
        Assertions.assertSame(staffNumber, employeeDTO.getStaffNumber());
    }

    @Test
    void testGetName() {
        Assertions.assertSame(name, employeeDTO.getName());
    }

    @Test
    void testGetType() {
        Assertions.assertSame(type, employeeDTO.getType());
    }

    @Test
    void testGetAssignment() {
        Assertions.assertTrue(employeeDTO.getAssignment().isPresent());
        Assertions.assertSame(assignment, employeeDTO.getAssignment().get());
    }

    @DisplayName("EmployeeDTOTest Class Teardown")
    @AfterAll
    static void teardown() {
        staffNumber = 0;
        name = null;
        type = null;
        assignment = null;
        employeeDTO = null;
    }
}
