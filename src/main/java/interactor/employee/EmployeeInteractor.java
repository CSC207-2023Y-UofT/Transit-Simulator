package interactor.employee;

import employee.*;
import model.control.TransitModel;
import model.train.Train;
import model.train.TrainRole;
import model.train.TrainRole;
import util.Preconditions;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation of the IEmployeeInteractor interface, providing functionalities
 * to manage employees.
 */
public class EmployeeInteractor implements IEmployeeInteractor {

    /** Tracker to track employee activities. */
    private final EmployeeTracker tracker;

    /** Model representation for transit. */
    private final TransitModel model;

    /**
     * Constructs a new EmployeeInteractor with the specified tracker and model.
     *
     * @param track The employee tracker.
     * @param model The transit model.
     */
    public EmployeeInteractor(EmployeeTracker track, TransitModel model){
        this.tracker = track;
        this.model = model;
    }

    /**
     * Registers a new employee based on the given request model.
     *
     * @param requestModel The request model for registering the employee.
     * @return The info of the registered employee.
     */
    @Override
    public EmployeeInfo registerEmployee(RegisterEmployeeRequest requestModel) {
        switch (requestModel.type){
            case ENGINEER:
                TrainEngineer eng = new TrainEngineer(ThreadLocalRandom.current().nextInt(999999999));
                tracker.addEmployee(eng);
                return toInfo(eng);
            case OPERATOR:
                TrainOperator ope = new TrainOperator(ThreadLocalRandom.current().nextInt(999999999));
                tracker.addEmployee(ope);
                return toInfo(ope);
            case ADMINISTRATOR:
                Admin adm = new Admin(ThreadLocalRandom.current().nextInt(999999999));
                tracker.addEmployee(adm);
                return toInfo(adm);
        }
        return null;
    }

    /**
     * Retrieves the information of an employee given the staff number.
     *
     * @param staffNumber The staff number of the employee.
     * @return Optional containing the employee's info, or empty if not found.
     */
    @Override
    public Optional<EmployeeInfo> getEmployeeInfo(int staffNumber) {
        Employee employee = tracker.getEmployee(staffNumber)
                .orElse(null);
        if (employee == null) return Optional.empty();
        return Optional.of(toInfo(employee));
    }

    /**
     * Removes an employee with the specified staff number.
     *
     * @param staffNumber The staff number of the employee to be removed.
     */
    @Override
    public void removeEmployee(int staffNumber) {
        tracker.removeEmployee(staffNumber);
    }

    /**
     * Assigns a job role to an employee for a specified train.
     *
     * @param staffNumber The staff number of the employee.
     * @param trainName   The name of the train.
     * @param job         The job role to be assigned.
     */
    @Override
    public void assignJob(int staffNumber, String trainName, TrainRole job) {
        Employee employee = tracker.getEmployee(staffNumber)
                .orElse(null);
        Preconditions.checkArgument(employee != null, "Employee does not exist");

        Train train = model.getTrain(trainName);
        Preconditions.checkArgument(train != null, "Train does not exist");

        // Anyone else on the train with the same job?
        List<EmployeeInfo> assigned = getAssignedEmployees(trainName);
        for (EmployeeInfo employeeInfo : assigned) {
            EmployeeAssignment assignment = employeeInfo.getAssignment().orElse(null);
            if (assignment == null) continue;
            if (assignment.getRole() == job) {
                throw new IllegalStateException("There is already an employee assigned to the job on that train");
            }
        }

        employee.setAssignment(new EmployeeAssignment(trainName, job));
    }

    /**
     * Unassigns an employee from their current job.
     *
     * @param staffNumber The staff number of the employee.
     * @return True if the unassignment was successful, otherwise false.
     */
    @Override
    public boolean unassign(int staffNumber) {
        Employee employee = tracker.getEmployee(staffNumber)
                .orElse(null);
        if (employee == null) return false;
        employee.setAssignment(null);
        return true;
    }

    /**
     * Retrieves a list of employees assigned to a specific train.
     *
     * @param trainName The name of the train.
     * @return A list of employees assigned to the train.
     */
    @Override
    public List<EmployeeInfo> getAssignedEmployees(String trainName) {
        Train train = model.getTrain(trainName);
        if (train == null) return new ArrayList<>();

        List<Employee> employees = tracker.getEmployeeList();
        List<EmployeeInfo> assigned = new ArrayList<>();

        for (Employee employee : employees) {
            Optional<EmployeeAssignment> assignment = employee.getAssignment();
            if (assignment.isEmpty()) continue;
            String name = assignment.get().getTrainName();
            if (name.equals(trainName)) {
                assigned.add(toInfo(employee));
            }
        }

        return assigned;
    }

    /**
     * Converts an Employee object to an EmployeeInfo object.
     *
     * @param employee The employee object to be converted.
     * @return The converted EmployeeInfo object.
     */
    private EmployeeInfo toInfo(Employee employee) {
        EmployeeAssignment assignment = employee.getAssignment().orElse(null);
        if (assignment != null) {
            if (model.getTrain(assignment.getTrainName()) == null) {
                assignment = null;
            }
        }

        return new EmployeeInfo(employee.getStaffNumber(), employee.getEmployeeType(), assignment);
    }
}
