package interface_adapter.viewmodel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import app_business.common.EmployeeAssignment;
import app_business.common.EmployeeType;
import app_business.dto.EmployeeDTO;
import entity.model.train.TrainRole;
import interface_adapter.controller.EmployeeController;
import java.util.Arrays;
import java.util.Optional;


public class RouteViewModelTest {

    private RouteViewModel routeViewModel;
    private EmployeeController employeeControllerMock;

    @BeforeEach
    public void setUp() {
        employeeControllerMock = mock(EmployeeController.class);
    }

    @Test
    public void testGetWhatToSay_NoAssignment() {
        EmployeeDTO employeeDTO = new EmployeeDTO(1, "John", EmployeeType.OPERATOR, null);
        routeViewModel = new RouteViewModel(employeeDTO, employeeControllerMock);
        assertEquals("You are unassigned", routeViewModel.getWhatToSay());
    }

    @Test
    public void testGetWhatToSay_Assigned_NoCoworkers() {
        EmployeeAssignment assignment = new EmployeeAssignment("TrainX", TrainRole.OPERATOR);
        EmployeeDTO employeeDTO = new EmployeeDTO(1, "John", EmployeeType.OPERATOR, assignment);
        routeViewModel = new RouteViewModel(employeeDTO, employeeControllerMock);

        when(employeeControllerMock.byAssignment("TrainX")).thenReturn(Arrays.asList(employeeDTO));

        assertEquals("You are assigned to TrainX as OPERATOR with no one", routeViewModel.getWhatToSay());
    }

    @Test
    public void testGetWhatToSay_Assigned_WithCoworkers() {
        EmployeeAssignment assignment = new EmployeeAssignment("TrainX", TrainRole.OPERATOR);
        EmployeeDTO employeeDTO = new EmployeeDTO(1, "John", EmployeeType.OPERATOR, assignment);
        EmployeeDTO coworker = new EmployeeDTO(2, "Jane", EmployeeType.ENGINEER, assignment);
        routeViewModel = new RouteViewModel(employeeDTO, employeeControllerMock);

        when(employeeControllerMock.byAssignment("TrainX")).thenReturn(Arrays.asList(employeeDTO, coworker));

        assertEquals("You are assigned to TrainX as OPERATOR with Jane", routeViewModel.getWhatToSay());
    }

    @Test
    public void testGetWhatToSay_Assigned_MultipleCoworkers() {
        EmployeeAssignment assignment = new EmployeeAssignment("TrainX", TrainRole.OPERATOR);
        EmployeeDTO employeeDTO = new EmployeeDTO(1, "John", EmployeeType.OPERATOR, assignment);
        EmployeeDTO coworker1 = new EmployeeDTO(2, "Jane", EmployeeType.ENGINEER, assignment);
        EmployeeDTO coworker2 = new EmployeeDTO(3, "Doe", EmployeeType.ADMINISTRATOR, assignment);
        routeViewModel = new RouteViewModel(employeeDTO, employeeControllerMock);

        when(employeeControllerMock.byAssignment("TrainX")).thenReturn(Arrays.asList(employeeDTO, coworker1, coworker2));

        assertEquals("You are assigned to TrainX as OPERATOR with Jane, Doe", routeViewModel.getWhatToSay());
    }

    @Test
    public void testUpdateEmployee() {
        // Old Employee without assignment
        EmployeeDTO oldEmployeeDTO = new EmployeeDTO(1, "John", EmployeeType.OPERATOR, null);
        routeViewModel = new RouteViewModel(oldEmployeeDTO, employeeControllerMock);
        assertEquals("You are unassigned", routeViewModel.getWhatToSay());

        // Mock the updated Employee with an assignment
        EmployeeAssignment assignment = new EmployeeAssignment("TrainY", TrainRole.ENGINEER);
        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO(1, "John Updated", EmployeeType.OPERATOR, assignment);
        when(employeeControllerMock.find(1)).thenReturn(Optional.of(updatedEmployeeDTO));

        // Since the employee is updated internally, we expect the next call to reflect this change
        assertEquals("You are assigned to TrainY as ENGINEER with no one", routeViewModel.getWhatToSay());
    }






}

