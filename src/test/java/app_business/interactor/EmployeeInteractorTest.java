package app_business.interactor;

import app_business.common.EmployeeAssignment;
import app_business.common.EmployeeType;
import app_business.dto.EmployeeDTO;
import app_business.interactor.EmployeeInteractor;
import entity.employee.*;
import entity.model.control.TransitModel;
import entity.model.train.TrainPosition;
import entity.model.train.TrainRole;
import entity.model.train.repo.impl.MemoryTrackRepo;
import entity.model.train.track.TrackSegment;

import persistence.impl.MemoryEmployeeDataStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;

public class EmployeeInteractorTest {
    TransitModel model;
    @DisplayName("EmployeeInteractorTest Class Setup")
    @BeforeEach
    public void setup() {
        model = new TransitModel();

    }

    @Test
    public void testRegisterEmployee() {
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, model);
        int id = interact.idGenerator();
        EmployeeDTO dto = new EmployeeDTO(id, "Mama Mia", EmployeeType.ENGINEER, null);
        Assertions.assertEquals(dto, interact.registerEmployee("Mama Mia", EmployeeType.ENGINEER, id));
    }

    @Test
    public void testFind() {
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        Employee emp = new TrainEngineer(101, "Hill");
        data.save(emp);
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, model);
        Optional<EmployeeDTO> dto = Optional.of(new EmployeeDTO(101, "Hill", EmployeeType.ENGINEER, null));
        Assertions.assertEquals(dto, interact.find(101));
    }

    @Test
    public void testRemove() {
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        Employee emp = new TrainEngineer(101, "Hill");
        data.save(emp);
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, model);
        interact.removeEmployee(101);
        Assertions.assertNull(interact.find(101));
    }

    @Test
    public void testAssignJob() {
        MemoryTrackRepo repo = new MemoryTrackRepo();
        TrackSegment segment = new TrackSegment(repo, "23", 10);
        TrainPosition position = new TrainPosition(segment, 3);
        model.createTrain(segment, "ICE10151", 500);
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, model);
        Employee emp = new TrainOperator(69, "Nutcracker");
        track.saveEmployee(emp);
        interact.assignJob(69, "ICE10151", TrainRole.OPERATOR);
        Optional<EmployeeAssignment> assign = Optional.of(new EmployeeAssignment("ICE10151", TrainRole.OPERATOR));
        Assertions.assertEquals(assign, emp.getAssignment());
    }

    @Test
    public void testUnassign() {
        MemoryTrackRepo repo = new MemoryTrackRepo();
        TrackSegment segment = new TrackSegment(repo, "23", 10);
        model.createTrain(segment, "ICE10151", 500);
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, model);
        Employee emp = new TrainOperator(69, "Nutcracker");
        track.saveEmployee(emp);
        interact.assignJob(69, "ICE10151", TrainRole.OPERATOR);
        interact.unassign(69);
        Assertions.assertNull(interact.find(69));
    }

    @Test
    public void testGetAssignedEmployee(){
        MemoryTrackRepo repo = new MemoryTrackRepo();
        TrackSegment segment = new TrackSegment(repo, "23", 10);
        model.createTrain(segment, "IC580", 500);
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, model);
        EmployeeAssignment assignjuan = new EmployeeAssignment("IC580", TrainRole.ENGINEER);
        EmployeeDTO juandto = new EmployeeDTO(10, "Juan", EmployeeType.ENGINEER, assignjuan);
        List<EmployeeDTO> lst = new ArrayList<>();
        lst.add(juandto);
        interact.assignJob(10, "IC580", TrainRole.ENGINEER);
        interact.assignJob(21, "IC580", TrainRole.OPERATOR);
        Assertions.assertEquals(lst, interact.getAssignedEmployees("IC580"));
    }

    @Test
    public void testGetEmployee(){
        MemoryTrackRepo repo = new MemoryTrackRepo();
        TrackSegment segment = new TrackSegment(repo, "23", 10);
        model.createTrain(segment, "IC580", 500);
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, model);
        EmployeeAssignment assignjuan = new EmployeeAssignment("IC580", TrainRole.ENGINEER);
        EmployeeAssignment assigndoug = new EmployeeAssignment("IC580", TrainRole.OPERATOR);
        EmployeeDTO juandto = new EmployeeDTO(10, "Juan", EmployeeType.ENGINEER, assignjuan);
        EmployeeDTO dougdto = new EmployeeDTO(21, "Doug", EmployeeType.OPERATOR, assigndoug);
        List<EmployeeDTO> lst = new ArrayList<>();
        lst.add(juandto);
        lst.add(dougdto);
        Assertions.assertEquals(lst, interact.getEmployees());
    }
    @Disabled
    @Test
    public void testtoDTO(){
        Employee juan = new TrainOperator(10, "Juan");
        Employee doug = new TrainEngineer(21, "Doug");
        MemoryTrackRepo repo = new MemoryTrackRepo();
        TrackSegment segment = new TrackSegment(repo, "23", 10);
        model.createTrain(segment, "IC580", 500);
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, model);
        track.saveEmployee(juan);
        track.saveEmployee(doug);
        EmployeeAssignment assignJuan = new EmployeeAssignment("IC580", TrainRole.OPERATOR);
        EmployeeAssignment assignDoug = new EmployeeAssignment("IC580", TrainRole.ENGINEER);
        EmployeeDTO juandto = new EmployeeDTO(10, "Juan", EmployeeType.OPERATOR, assignJuan);
        EmployeeDTO dougdto = new EmployeeDTO(21, "Doug", EmployeeType.ENGINEER, assignDoug);
        Assertions.assertEquals(juandto, interact.toDTO(juan));
    }
}