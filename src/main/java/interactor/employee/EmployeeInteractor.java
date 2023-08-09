package interactor.employee;

import employee.*;
import model.control.TransitModel;
import model.train.Train;
import model.train.TrainRole;
import util.Preconditions;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class EmployeeInteractor implements IEmployeeInteractor {
    private final EmployeeTracker tracker;
    private final TransitModel model;

    public EmployeeInteractor(EmployeeTracker tracker, TransitModel model){
        this.tracker = tracker;
        this.model = model;
    }

    @Override
    public EmployeeDTO registerEmployee(RegisterEmployeeRequest requestModel) {
        switch (requestModel.type){
            case ENGINEER:
                TrainEngineer eng = new TrainEngineer(ThreadLocalRandom.current().nextInt(999999999), requestModel.getName());
                tracker.addEmployee(eng);
                return toDTO(eng);
            case OPERATOR:
                TrainOperator ope = new TrainOperator(ThreadLocalRandom.current().nextInt(999999999), requestModel.getName());
                tracker.addEmployee(ope);
                return toDTO(ope);
            case ADMINISTRATOR:
                Admin adm = new Admin(ThreadLocalRandom.current().nextInt(999999999), requestModel.getName());
                tracker.addEmployee(adm);
                return toDTO(adm);
        }
        return null;
    }

    @Override
    public Optional<EmployeeDTO> getEmployeeInfo(int staffNumber) {
        Employee employee = tracker.getEmployee(staffNumber)
                .orElse(null);
        if (employee == null) return Optional.empty();
        return Optional.of(toDTO(employee));
    }

    @Override
    public void removeEmployee(int staffNumber) {
        tracker.removeEmployee(staffNumber);
    }

    @Override
    public void assignJob(int staffNumber, String trainName, TrainRole job) {
        Employee employee = tracker.getEmployee(staffNumber)
                .orElse(null);

        Preconditions.checkArgument(employee != null, "Employee does not exist");

        Train train = model.getTrain(trainName);
        Preconditions.checkArgument(train != null, "Train does not exist");

        // Anyone else on the train with the same job?
        List<EmployeeDTO> assigned = getAssignedEmployees(trainName);
        for (EmployeeDTO employeeDTO : assigned) {
            EmployeeAssignment assignment = employeeDTO.getAssignment().orElse(null);
            if (assignment == null) continue;
            if (assignment.getRole() == job) {
                throw new IllegalStateException("There is already an employee assigned to the job on that train");
            }
        }

        employee.setAssignment(new EmployeeAssignment(trainName, job));
    }

    @Override
    public boolean unassign(int staffNumber) {
        Employee employee = tracker.getEmployee(staffNumber)
                .orElse(null);
        if (employee == null) return false;
        employee.setAssignment(null);
        return true;
    }

    @Override
    public List<EmployeeDTO> getAssignedEmployees(String trainName) {
        Train train = model.getTrain(trainName);
        if (train == null) return new ArrayList<>();

        List<Employee> employees = tracker.getEmployeeList();
        List<EmployeeDTO> assigned = new ArrayList<>();

        for (Employee employee : employees) {
            Optional<EmployeeAssignment> assignment = employee.getAssignment();
            if (assignment.isEmpty()) continue;
            String name = assignment.get().getTrainName();
            if (name.equals(trainName)) {
                assigned.add(toDTO(employee));
            }
        }

        return assigned;
    }

    private EmployeeDTO toDTO(Employee employee) {
        EmployeeAssignment assignment = employee.getAssignment().orElse(null);
        if (assignment != null) {
            if (model.getTrain(assignment.getTrainName()) == null) {
                assignment = null;
            }
        }

        return new EmployeeDTO(employee.getStaffNumber(), employee.getName(), employee.getEmployeeType(), assignment);
    }
}
