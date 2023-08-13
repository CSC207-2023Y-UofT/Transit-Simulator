package app_business.interactor;

import app_business.boundary.IEmployeeInteractor;
import app_business.common.EmployeeAssignment;
import app_business.common.EmployeeType;
import app_business.dto.EmployeeDTO;
import entity.employee.*;
import entity.model.control.TransitModel;
import entity.model.train.TrainRole;
import entity.model.train.repo.TrackRepo;  // interface here
// import entity.model.train.repo.impl.MemoryTrackRepo;  // depend on the interface, not the implementation!
import entity.model.train.track.TrackSegment;

import persistence.boundary.EmployeeDataStore;
import persistence.impl.MemoryEmployeeDataStore;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;

public class EmployeeInteractorTest {  // Note: Tests are not necessarily run by intelliJ in the same order as written here.
    TransitModel model;
    TrackRepo repo;
    EmployeeDataStore data;
    EmployeeTracker tracker;
    IEmployeeInteractor interactor;
    EmployeeInteractor interactorImpl;
    TrackSegment segment;
    @DisplayName("EmployeeInteractorTest Class Setup")
    @BeforeEach  // This is run BeforeEach test case.
    public void setup() {  // The setup is here to ensure that test cases are not crowded with setup code.
        model = new TransitModel();   // Repo is automatically made when model is instantiated.
        repo = model.getTrackRepo();  // Note: the repo must belong to the instantiated TransitModel. It cannot be a new MemoryTrackRepo() instance like written earlier.
        data = new MemoryEmployeeDataStore();
        tracker = new EmployeeTracker(data);
        interactorImpl = new EmployeeInteractor(tracker, model);
        interactor = interactorImpl;  // This is the interface that we will be testing.

        // Creating the test tracks and trains; it's okay even if some test cases don't use this.
        segment = new TrackSegment(repo, "23", 10);
        repo.addTrack(segment);  // Don't forget to add the track to the repo; otherwise, the model will not be able to find the track.
        model.createTrain(segment, "ICE10151", 500);  // Don't need IC580, one train is sufficient for testing.
    }

    @Test
    public void testRegisterEmployee() {
        int id = interactor.idGenerator();
        EmployeeDTO dto = new EmployeeDTO(id, "Mama Mia", EmployeeType.ENGINEER, null);
        // Assertions.assertEquals(dto, interactor.registerEmployee("Mama Mia", EmployeeType.ENGINEER, id));  // DTObjects do not override Equals, it's not possible to compare using Equals
        // Instead, we have to check equality of properties manually.
        EmployeeDTO dto_Test_Variable = interactor.registerEmployee("Mama Mia", EmployeeType.ENGINEER, id);

        Assertions.assertEquals(dto.getStaffNumber(), dto_Test_Variable.getStaffNumber());
        Assertions.assertEquals(dto.getName(), dto_Test_Variable.getName());
        Assertions.assertEquals(dto.getType(), dto_Test_Variable.getType());
        Assertions.assertFalse(dto.getAssignment().isPresent());
        Assertions.assertFalse(dto_Test_Variable.getAssignment().isPresent());
    }

    @Test
    public void testFind() {
        Employee emp = new TrainEngineer(101, "Hill");
        data.save(emp);
        EmployeeDTO dto = new EmployeeDTO(101, "Hill", EmployeeType.ENGINEER, null);  // We don't need to make this an Optional! It's given as a real object; DTObjects don't even override Equals

        Optional<EmployeeDTO> dto_Test_Variable = interactor.find(101);
        Assertions.assertTrue(dto_Test_Variable.isPresent());
        EmployeeDTO dto_Test_Variable_2 = dto_Test_Variable.get();

        Assertions.assertEquals(dto.getStaffNumber(), dto_Test_Variable_2.getStaffNumber());
        Assertions.assertEquals(dto.getName(), dto_Test_Variable_2.getName());
        Assertions.assertEquals(dto.getType(), dto_Test_Variable_2.getType());
        Assertions.assertFalse(dto.getAssignment().isPresent());
        Assertions.assertFalse(dto_Test_Variable_2.getAssignment().isPresent());
    }

    @Test
    public void testRemove() {
        Employee emp = new TrainEngineer(101, "Hill");
        data.save(emp);
        interactor.removeEmployee(101);

        Assertions.assertFalse(interactor.find(101).isPresent());  // With Optionals, we have to assert that it is false that the Optional is present, instead of asserting that the Optional is null.
    }

    @Test
    public void testAssignJob() {
        // TrainPosition position = new TrainPosition(segment, 3);  // Unnecessary position object; contributes to java warnings
        Employee emp = new TrainOperator(69, "Nutcracker");
        tracker.saveEmployee(emp);
        interactor.assignJob(69, "ICE10151", TrainRole.OPERATOR);

        Optional<EmployeeAssignment> testVariable_1 = emp.getAssignment();
        Assertions.assertTrue(testVariable_1.isPresent());
        EmployeeAssignment testVariable_2 = testVariable_1.get();

        Assertions.assertEquals("ICE10151", testVariable_2.getTrainName());
        Assertions.assertEquals(TrainRole.OPERATOR, testVariable_2.getRole());
    }

    @Test
    public void testAssignJob_NonExistentEmployee() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> interactor.assignJob(69, "ICE10151", TrainRole.OPERATOR)); // This is a lambda expression. It's a shorthand for creating an anonymous class that implements the interface "needs to be executable".
    }

    @Test
    public void testAssignJob_NonExistentTrain() {
        model.removeTrain("ICE10151");

        Employee emp = new TrainOperator(69, "Jarrett");
        tracker.saveEmployee(emp);

        Assertions.assertThrows(IllegalArgumentException.class, () -> interactor.assignJob(69, "ICE10151", TrainRole.OPERATOR));
    }

    @Test
    public void testAssignJob_AssignmentAlreadyExists() {
        Employee empJuan = new TrainOperator(10, "Juan");
        Employee empDoug = new TrainEngineer(21, "Doug");
        tracker.saveEmployee(empJuan);
        tracker.saveEmployee(empDoug);
        interactor.assignJob(10, "ICE10151", TrainRole.OPERATOR);

        Assertions.assertThrows(IllegalStateException.class, () -> interactor.assignJob(21, "ICE10151", TrainRole.OPERATOR));
    }

    @Test
    public void testUnassign() {
        Employee emp = new TrainOperator(69, "Nutcracker");
        tracker.saveEmployee(emp);
        interactor.assignJob(69, "ICE10151", TrainRole.OPERATOR);

        Assertions.assertTrue(interactor.find(69).isPresent());  // no guarantees that .testAssign() is run before this test method
        Assertions.assertNotNull(interactor.find(69).get().getAssignment());

        interactor.unassign(69);

        Assertions.assertTrue(interactor.find(69).isPresent());
        Assertions.assertFalse(interactor.find(69).get().getAssignment().isPresent());
    }

    @Test
    public void testGetAssignedEmployees(){
        Employee empJuan = new TrainEngineer(10, "Juan");  // Don't forget to create the actual employees
        Employee empDoug = new TrainOperator(21, "Doug");
        tracker.saveEmployee(empJuan);
        tracker.saveEmployee(empDoug);
        interactor.assignJob(10, "ICE10151", TrainRole.ENGINEER);
        interactor.assignJob(21, "ICE10151", TrainRole.OPERATOR);

        List<EmployeeDTO> assignedEmployees = interactor.getAssignedEmployees("ICE10151");
        Assertions.assertEquals(2, assignedEmployees.size());
        Assertions.assertEquals(10, assignedEmployees.get(0).getStaffNumber());  // TODO: Consider whether the order of the list is guaranteed or not
        Assertions.assertEquals(21, assignedEmployees.get(1).getStaffNumber());
    }

    @Test
    public void testGetEmployees(){
        Employee empJuan = new TrainEngineer(10, "Juan");
        Employee empDoug = new TrainOperator(21, "Doug");

        Assertions.assertInstanceOf(List.class, interactor.getEmployees());

        Assertions.assertEquals(0, interactor.getEmployees().size());
        tracker.saveEmployee(empJuan);
        Assertions.assertEquals(1, interactor.getEmployees().size());
        Assertions.assertEquals(10, interactor.getEmployees().get(0).getStaffNumber());
        tracker.saveEmployee(empDoug);
        Assertions.assertEquals(2, interactor.getEmployees().size());

        Assertions.assertEquals(empJuan.getName(), interactor.getEmployees().get(0).getName());  // TODO Consider whether the order of the list is guaranteed or not
        Assertions.assertEquals(empJuan.getEmployeeType(), interactor.getEmployees().get(0).getType());
        Assertions.assertEquals(empJuan.getStaffNumber(), interactor.getEmployees().get(0).getStaffNumber());
        Assertions.assertEquals(empJuan.getAssignment(), interactor.getEmployees().get(0).getAssignment());

        Assertions.assertEquals(empDoug.getName(), interactor.getEmployees().get(1).getName());
        Assertions.assertEquals(empDoug.getEmployeeType(), interactor.getEmployees().get(1).getType());
        Assertions.assertEquals(empDoug.getStaffNumber(), interactor.getEmployees().get(1).getStaffNumber());
        Assertions.assertEquals(empDoug.getAssignment(), interactor.getEmployees().get(1).getAssignment());
    }
    
    @Test
    public void testToDTO(){
        Employee juan = new TrainOperator(10, "Juan");
        Employee doug = new TrainEngineer(21, "Doug");
        tracker.saveEmployee(juan);
        tracker.saveEmployee(doug);
        interactor.assignJob(10, "ICE10151", TrainRole.OPERATOR);
        interactor.assignJob(21, "ICE10151", TrainRole.ENGINEER);

        EmployeeAssignment assignJuan = new EmployeeAssignment("ICE10151", TrainRole.OPERATOR);
        EmployeeAssignment assignDoug = new EmployeeAssignment("ICE10151", TrainRole.ENGINEER);
        EmployeeDTO juanDTO = new EmployeeDTO(10, "Juan", EmployeeType.OPERATOR, assignJuan);
        EmployeeDTO dougDTO = new EmployeeDTO(21, "Doug", EmployeeType.ENGINEER, assignDoug);

        // Assertions.assertEquals(juanDTO, interactor.toDTO(juan));  // DTObjects do not override Equals, it's not possible to compare using Equals
        EmployeeDTO test_Variable_Juan = interactorImpl.toDTO(juan);
        EmployeeDTO test_Variable_Doug = interactorImpl.toDTO(doug);

        Assertions.assertEquals(juanDTO.getStaffNumber(), test_Variable_Juan.getStaffNumber());
        Assertions.assertEquals(juanDTO.getName(), test_Variable_Juan.getName());
        Assertions.assertEquals(juanDTO.getType(), test_Variable_Juan.getType());
        Assertions.assertTrue(juanDTO.getAssignment().isPresent());
        Assertions.assertTrue(test_Variable_Juan.getAssignment().isPresent());
        Assertions.assertEquals(juanDTO.getAssignment().get().getTrainName(), test_Variable_Juan.getAssignment().get().getTrainName());
        Assertions.assertEquals(juanDTO.getAssignment().get().getRole(), test_Variable_Juan.getAssignment().get().getRole());

        Assertions.assertEquals(dougDTO.getStaffNumber(), test_Variable_Doug.getStaffNumber());
        Assertions.assertEquals(dougDTO.getName(), test_Variable_Doug.getName());
        Assertions.assertEquals(dougDTO.getType(), test_Variable_Doug.getType());
        Assertions.assertTrue(dougDTO.getAssignment().isPresent());
        Assertions.assertTrue(test_Variable_Doug.getAssignment().isPresent());
        Assertions.assertEquals(dougDTO.getAssignment().get().getTrainName(), test_Variable_Doug.getAssignment().get().getTrainName());
        Assertions.assertEquals(dougDTO.getAssignment().get().getRole(), test_Variable_Doug.getAssignment().get().getRole());
    }
}