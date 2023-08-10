package interactor.employee;

import employee.*;
import model.control.TransitModel;
import model.train.Train;
import model.train.TrainRole;
import util.Preconditions;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Interactor class responsible for operations related to employees.
 * It implements the {@link IEmployeeInteractor} interface.
 */
public class EmployeeInteractor implements IEmployeeInteractor {

    /** Tracks the employees and their details. */
    private final EmployeeTracker tracker;

    /** Represents the transit model which might contain trains and other details. */
    private final TransitModel model;

    /**
     * Constructs an EmployeeInteractor instance.
     *
     * @param tracker The employee tracker.
     * @param model The transit model.
     */
    public EmployeeInteractor(EmployeeTracker tracker, TransitModel model){
        this.tracker = tracker;
        this.model = model;
    }

    /**
     * Registers a new employee based on the given request model.
     *
     * @param requestModel The request model containing the type of employee to be registered.
     * @return The newly created {@link EmployeeDTO} object.
     */
    @Override
    public EmployeeDTO registerEmployee(RegisterEmployeeRequest requestModel) {
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
     * Retrieves the information for an employee by their staff number.
     *
     * @param staffNumber The unique identifier of the employee.
     * @return An optional containing the {@link EmployeeDTO} if found, otherwise empty.
     */
    @Override
    public Optional<EmployeeDTO> getEmployeeInfo(int staffNumber) {
        Employee employee = tracker.getEmployee(staffNumber)
                .orElse(null);
        if (employee == null) return Optional.empty();
        return Optional.of(toInfo(employee));
    }

    /**
     * Removes an employee from the tracker based on their staff number.
     *
     * @param staffNumber The unique identifier of the employee.
     */
    @Override
    public void removeEmployee(int staffNumber) {
        tracker.removeEmployee(staffNumber);
    }

    /**
     * Assigns a job role on a specific train to an employee.
     *
     * @param staffNumber The unique identifier of the employee.
     * @param trainName The name of the train.
     * @param job The job role to be assigned.
     * @throws IllegalArgumentException if either the employee or the train does not exist.
     * @throws IllegalStateException if there's already an employee assigned to that job on the specified train.
     */
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

    /**
     * Unassigns any job from an employee.
     *
     * @param staffNumber The unique identifier of the employee.
     * @return true if the job was successfully unassigned, false otherwise.
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
     * @return A list of {@link EmployeeDTO} objects representing the employees assigned to the train.
     */
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
                assigned.add(toInfo(employee));
            }
        }

        return assigned;
    }

    /**
     * Converts an {@link Employee} object to an {@link EmployeeDTO}.
     *
     * @param employee The employee to be converted.
     * @return An {@link EmployeeDTO} representation of the provided employee.
     */
    private EmployeeDTO toInfo(Employee employee) {
        EmployeeAssignment assignment = employee.getAssignment().orElse(null);
        if (assignment != null) {
            if (model.getTrain(assignment.getTrainName()) == null) {
                assignment = null;
            }
        }
        return new EmployeeDTO(employee.getStaffNumber(), employee.getEmployeeType(), assignment);
    }

}
