package InteractorTest;
import app_business.common.EmployeeAssignment;
import app_business.common.EmployeeType;
import app_business.dto.EmployeeDTO;
import app_business.interactor.EmployeeInteractor;
import entity.employee.*;
import entity.model.control.TransitModel;
import entity.model.train.MemoryTrackRepo;
import entity.model.train.Train;
import entity.model.train.TrainPosition;
import entity.model.train.TrainRole;
import entity.model.train.repo.TrackRepo;
import entity.model.train.track.TrackSegment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import persistence.DataStorage;
import persistence.boundary.EmployeeDataStore;
import persistence.impl.MemoryEmployeeDataStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.io.IOException;

public class EmployeeInteractorTest {
    @Test
    public void testRegisterEmployee() {
        TransitModel transit = new TransitModel();
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, transit);
        int id = interact.idGenerator();
        EmployeeDTO dto = new EmployeeDTO(id, "Mama Mia", EmployeeType.ENGINEER, null);
        Assert.assertEquals(dto, interact.registerEmployee("Mama Mia", EmployeeType.ENGINEER, id));
    }

    @Test
    public void testFind() throws IOException {
        TransitModel model = new TransitModel();
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        Employee emp = new TrainEngineer(101, "Hill");
        data.save(emp);
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, model);
        Optional<EmployeeDTO> dto = Optional.of(new EmployeeDTO(101, "Hill", EmployeeType.ENGINEER, null));
        Assert.assertEquals(dto, interact.find(101));
    }

    @Test
    public void testRemove() throws IOException {
        TransitModel model = new TransitModel();
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        Employee emp = new TrainEngineer(101, "Hill");
        data.save(emp);
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, model);
        interact.removeEmployee(101);
        Assert.assertNull(interact.find(101));
    }

    @Test
    public void testAssignJob() {
        MemoryTrackRepo repo = new MemoryTrackRepo();
        TrackSegment segment = new TrackSegment(repo, "23", 10);
        TrainPosition position = new TrainPosition(segment, 3);
        TransitModel transit = new TransitModel();
        transit.createTrain(segment, "ICE10151", 500);
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, transit);
        Employee emp = new TrainOperator(69, "Nutcracker");
        track.saveEmployee(emp);
        interact.assignJob(69, "ICE10151", TrainRole.OPERATOR);
        Optional<EmployeeAssignment> assign = Optional.of(new EmployeeAssignment("ICE10151", TrainRole.OPERATOR));
        Assert.assertEquals(assign, emp.getAssignment());
    }

    @Test
    public void testUnassign() {
        MemoryTrackRepo repo = new MemoryTrackRepo();
        TrackSegment segment = new TrackSegment(repo, "23", 10);
        TransitModel transit = new TransitModel();
        transit.createTrain(segment, "ICE10151", 500);
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, transit);
        Employee emp = new TrainOperator(69, "Nutcracker");
        track.saveEmployee(emp);
        interact.assignJob(69, "ICE10151", TrainRole.OPERATOR);
        interact.unassign(69);
        Assert.assertNull(interact.find(69));
    }

    @Test
    public void testGetAssignedEmployee(){
        MemoryTrackRepo repo = new MemoryTrackRepo();
        TrackSegment segment = new TrackSegment(repo, "23", 10);
        TransitModel transit = new TransitModel();
        transit.createTrain(segment, "IC580", 500);
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, transit);
        EmployeeAssignment assignjuan = new EmployeeAssignment("IC580", TrainRole.ENGINEER);
        EmployeeDTO juandto = new EmployeeDTO(10, "Juan", EmployeeType.ENGINEER, assignjuan);
        List<EmployeeDTO> lst = new ArrayList<>();
        lst.add(juandto);
        interact.assignJob(10, "IC580", TrainRole.ENGINEER);
        interact.assignJob(21, "IC580", TrainRole.OPERATOR);
        Assert.assertEquals(lst, interact.getAssignedEmployees("IC580"));
    }

    @Test
    public void testGetEmployee(){
        MemoryTrackRepo repo = new MemoryTrackRepo();
        TrackSegment segment = new TrackSegment(repo, "23", 10);
        TransitModel transit = new TransitModel();
        transit.createTrain(segment, "IC580", 500);
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, transit);
        EmployeeAssignment assignjuan = new EmployeeAssignment("IC580", TrainRole.ENGINEER);
        EmployeeAssignment assigndoug = new EmployeeAssignment("IC580", TrainRole.OPERATOR);
        EmployeeDTO juandto = new EmployeeDTO(10, "Juan", EmployeeType.ENGINEER, assignjuan);
        EmployeeDTO dougdto = new EmployeeDTO(21, "Doug", EmployeeType.OPERATOR, assigndoug);
        List<EmployeeDTO> lst = new ArrayList<>();
        lst.add(juandto);
        lst.add(dougdto);
        Assert.assertEquals(lst, interact.getEmployees());
    }
    @Disabled
    @Test
    public void testtoDTO(){
        Employee juan = new TrainOperator(10, "Juan");
        Employee doug = new TrainEngineer(21, "Doug");
        MemoryTrackRepo repo = new MemoryTrackRepo();
        TrackSegment segment = new TrackSegment(repo, "23", 10);
        TransitModel transit = new TransitModel();
        transit.createTrain(segment, "IC580", 500);
        MemoryEmployeeDataStore data = new MemoryEmployeeDataStore();
        EmployeeTracker track = new EmployeeTracker(data);
        EmployeeInteractor interact = new EmployeeInteractor(track, transit);
        track.saveEmployee(juan);
        track.saveEmployee(doug);
        EmployeeAssignment assignJuan = new EmployeeAssignment("IC580", TrainRole.OPERATOR);
        EmployeeAssignment assignDoug = new EmployeeAssignment("IC580", TrainRole.ENGINEER);
        EmployeeDTO juandto = new EmployeeDTO(10, "Juan", EmployeeType.OPERATOR, assignJuan);
        EmployeeDTO dougdto = new EmployeeDTO(21, "Doug", EmployeeType.ENGINEER, assignDoug);
        Assert.assertEquals(juandto, interact.toDTO(juan));
    }
}