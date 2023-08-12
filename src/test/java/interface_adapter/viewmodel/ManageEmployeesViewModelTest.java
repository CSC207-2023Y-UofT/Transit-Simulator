package interface_adapter.viewmodel;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import app_business.common.EmployeeAssignment;
import app_business.common.EmployeeType;
import app_business.dto.EmployeeDTO;
import entity.model.train.TrainRole;
import interface_adapter.controller.EmployeeController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)  // Integrate JUnit 5 with Mockito
public class ManageEmployeesViewModelTest {

    @Mock
    private EmployeeController controller;

    @InjectMocks  // Automatically inject the mock into the viewModel
    private ManageEmployeesViewModel viewModel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);  // Initialization for Mockito in JUnit 5
        reset(controller);
    }

    @Test
    public void testGetTableData_Empty() {
        when(controller.getEmployees()).thenReturn(Collections.emptyList());

        Object[][] result = viewModel.getTableData();

        assertEquals(0, result.length);

        verify(controller, times(1)).getEmployees();
    }

    @Test
    public void testGetTableData_Filled() {
        List<EmployeeDTO> mockEmployees = Arrays.asList(
                new EmployeeDTO(1, "Alice", EmployeeType.OPERATOR, null),
                new EmployeeDTO(2, "Bob", EmployeeType.ENGINEER, new EmployeeAssignment("Train 1", TrainRole.OPERATOR))
        );
        when(controller.getEmployees()).thenReturn(mockEmployees);

        Object[][] result = viewModel.getTableData();

        assertEquals(2, result.length);
        assertEquals("Alice", result[0][0]);
        assertEquals(EmployeeType.OPERATOR, result[0][1]);
        assertNull(result[0][3]);

        assertEquals("Bob", result[1][0]);
        assertEquals(EmployeeType.ENGINEER, result[1][1]);
        assertEquals(TrainRole.OPERATOR + " on Train 1", result[1][3].toString());

        verify(controller, times(1)).getEmployees();
    }

    @Test
    public void testGetTableData_AssignmentPresentAndAbsent() {
        List<EmployeeDTO> mockEmployees = Arrays.asList(
                new EmployeeDTO(1, "Charlie", EmployeeType.ADMINISTRATOR, new EmployeeAssignment("Train 2", TrainRole.ENGINEER)),
                new EmployeeDTO(2, "David", EmployeeType.OPERATOR, null)
        );
        when(controller.getEmployees()).thenReturn(mockEmployees);


        Object[][] result = viewModel.getTableData();

        assertEquals("Charlie", result[0][0]);
        assertEquals(EmployeeType.ADMINISTRATOR, result[0][1]);
        assertEquals(TrainRole.ENGINEER + " on Train 2", result[0][3].toString());

        assertEquals("David", result[1][0]);
        assertEquals(EmployeeType.OPERATOR, result[1][1]);
        assertNull(result[1][3]);

        verify(controller, times(1)).getEmployees();
    }
}
