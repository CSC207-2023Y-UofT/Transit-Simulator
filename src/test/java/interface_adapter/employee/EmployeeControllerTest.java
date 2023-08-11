package interface_adapter.employee;

import static org.junit.jupiter.api.Assertions.*;

import app_business.boundary.IEmployeeInteractor;
import app_business.common.EmployeeType;
import app_business.dto.EmployeeDTO;
import entity.employee.Employee;
import entity.model.train.TrainRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.boundary.EmployeeDataStore;

import java.io.IOException;
import java.util.*;

public class EmployeeControllerTest {

    private EmployeeController controller;
    private MockEmployeeInteractor interactor;

    @BeforeEach
    public void setUp() {
        interactor = new MockEmployeeInteractor();
        controller = new EmployeeController(interactor);
    }


    @Test
    public void testAssignJobToNonExistentEmployee() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.assignEmployee(100, "Train !", TrainRole.OPERATOR);
        }, "Expected IllegalArgumentException for non-existent employee");
    }

    @Test
    public void testAssignJobTwiceToSameEmployee() {
        controller.registerEmployee("John", EmployeeType.OPERATOR);
        controller.assignEmployee(1, "Train 1", TrainRole.OPERATOR);

        assertThrows(IllegalArgumentException.class, () -> {
            controller.assignEmployee(1, "Train 1", TrainRole.OPERATOR);
        }, "Expected IllegalArgumentException when assigning same job twice to the same employee");
    }

    @Test
    public void testAssignSameJobToDifferentEmployees() {
        controller.registerEmployee("John", EmployeeType.OPERATOR);
        controller.registerEmployee("Doe", EmployeeType.OPERATOR);
        controller.assignEmployee(1, "Train 1", TrainRole.OPERATOR);

        assertThrows(IllegalArgumentException.class, () -> {
            controller.assignEmployee(2, "Train 1", TrainRole.OPERATOR);
        }, "Expected IllegalArgumentException when assigning same job to different employees");
    }

    @Test
    public void testSuccessfulUnassign() {
        controller.registerEmployee("John", EmployeeType.OPERATOR);
        controller.assignEmployee(1, "Train 1", TrainRole.OPERATOR);

        assertDoesNotThrow(() -> {
            controller.unassignEmployee(1);
        });

        // After unassignment, the same employee should be able to be assigned again
        assertDoesNotThrow(() -> {
            controller.assignEmployee(1, "Train 1", TrainRole.OPERATOR);
        });
    }

    @Test
    public void testLoginWithValidStaffNumber() {
        EmployeeDTO dto = interactor.registerEmployee("Alice", EmployeeType.ADMINISTRATOR);
        assertEquals(Optional.of(dto), controller.login(dto.getStaffNumber()));
    }

    @Test
    public void testLoginWithInvalidStaffNumber() {
        assertFalse(controller.login(999).isPresent());
    }

    @Test
    public void testRegisterEmployee() {
        EmployeeDTO dto = controller.registerEmployee("Bob", EmployeeType.ENGINEER);
        assertEquals("Bob", dto.getName());
        assertEquals(EmployeeType.ENGINEER, dto.getType());
    }

    @Test
    public void testAssignEmployee() {
        EmployeeDTO dto = interactor.registerEmployee("Alice", EmployeeType.ADMINISTRATOR);
        controller.assignEmployee(dto.getStaffNumber(), "Train 1", TrainRole.OPERATOR);
        assertTrue(interactor.find(dto.getStaffNumber()).map(EmployeeDTO::getAssignment).isPresent());
    }

    @Test
    public void testUnassignEmployee() {
        controller.unassignEmployee(2);
        assertTrue(interactor.find(2).map(EmployeeDTO::getAssignment).isEmpty());
    }

    @Test
    public void testRemoveEmployee() {
        EmployeeDTO dto = interactor.registerEmployee("Charlie", EmployeeType.OPERATOR);
        controller.removeEmployee(dto.getStaffNumber());
        assertFalse(controller.find(dto.getStaffNumber()).isPresent());
    }

    @Test
    public void testGetEmployees() {
        assertEquals(Collections.emptyList(), controller.getEmployees());
    }

    @Test
    public void testAssignNonExistingEmployee() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.assignEmployee(999, "Train 1", TrainRole.OPERATOR);
        });
    }

    @Test
    public void testAssignEmployeeToInvalidTrain() {
        // Assuming 100 is a valid staff number in your mock
        assertThrows(IllegalArgumentException.class, () -> {
            controller.assignEmployee(100, "InvalidTrain", TrainRole.OPERATOR);
        });
    }

    @Test
    public void testAssignEmployeeSuccessfully() {
        EmployeeDTO dto = controller.registerEmployee("Example Name", EmployeeType.OPERATOR);
        assertDoesNotThrow(() -> {
            controller.assignEmployee(dto.getStaffNumber(), "Train 1", TrainRole.OPERATOR);
        });
    }


    @Test
    public void testUnassignEmployeeSuccessfully() {
        // Assuming 100 is a valid staff number in your mock
        assertDoesNotThrow(() -> {
            controller.unassignEmployee(100);
        });
    }



    public class MockEmployeeInteractor implements IEmployeeInteractor {
        private final Map<Integer, EmployeeDTO> employeeDatabase = new HashMap<>();
        private final Map<Integer, String> employeeAssignments = new HashMap<>();

        @Override
        public EmployeeDTO registerEmployee(String name, EmployeeType type) {
            int newStaffNumber = employeeDatabase.size() + 1;
            EmployeeDTO newEmployee = new EmployeeDTO(newStaffNumber, name, type, null);
            employeeDatabase.put(newStaffNumber, newEmployee);
            return newEmployee;
        }

        @Override
        public Optional<EmployeeDTO> find(int staffNumber) {
            return Optional.ofNullable(employeeDatabase.get(staffNumber));
        }

        @Override
        public void removeEmployee(int staffNumber) {
            employeeDatabase.remove(staffNumber);
        }

        @Override
        public void assignJob(int staffNumber, String trainName, TrainRole job) {
            if (!employeeDatabase.containsKey(staffNumber)) {
                throw new IllegalArgumentException("Employee not found");
            }

            if (employeeAssignments.containsKey(staffNumber)) {
                throw new IllegalArgumentException("Employee is already assigned a job");
            }

            // Check if another employee is already assigned to this job on the train
            if (employeeAssignments.values().contains(trainName + job.name())) {
                throw new IllegalArgumentException("Job is already assigned to another employee");
            }

            employeeAssignments.put(staffNumber, trainName + job.name());
        }

        @Override
        public boolean unassign(int staffNumber) {
            return employeeAssignments.remove(staffNumber) != null;
        }

        @Override
        public List<EmployeeDTO> getAssignedEmployees(String trainName) {
            // Extract those from the mock database that are assigned to the given train
            List<EmployeeDTO> assignedEmployees = new ArrayList<>();
            for (Map.Entry<Integer, String> entry : employeeAssignments.entrySet()) {
                if (entry.getValue().startsWith(trainName)) {
                    assignedEmployees.add(employeeDatabase.get(entry.getKey()));
                }
            }
            return assignedEmployees;
        }

        @Override
        public List<EmployeeDTO> getEmployees() {
            return new ArrayList<>(employeeDatabase.values());
        }
    }


    /**
     * An in-memory implementation of the EmployeeDataStore interface.
     */
    public class MemoryEmployeeDataStore implements EmployeeDataStore {

        // In-memory store using a HashMap
        private final HashMap<Integer, Employee> employeeMap = new HashMap<>();

        @Override
        public void remove(int staffNumber) throws IOException {
            employeeMap.remove(staffNumber);
        }

        @Override
        public void save(Employee employee) throws IOException {
            if(employee == null) {
                throw new IOException("Invalid employee data");
            }
            employeeMap.put(employee.getStaffNumber(), employee);
        }

        @Override
        public Optional<Employee> get(int staffNumber) throws IOException {
            if(!employeeMap.containsKey(staffNumber)) {
                return Optional.empty();
            }
            return Optional.ofNullable(employeeMap.get(staffNumber));
        }

        @Override
        public List<Employee> getEmployees() throws IOException {
            return new ArrayList<>(employeeMap.values());
        }
    }
}