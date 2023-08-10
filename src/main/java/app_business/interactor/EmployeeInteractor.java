package app_business.interactor;

import app_business.boundary.IEmployeeInteractor;
import app_business.dto.EmployeeDTO;
import app_business.common.EmployeeAssignment;
import app_business.common.EmployeeType;
import entity.employee.*;
import entity.model.control.TransitModel;
import entity.model.train.Train;
import entity.model.train.TrainRole;
import util.Preconditions;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Interactor class responsible for operations related to employees.
 * It implements the {@link IEmployeeInteractor} interface.
 */
public class EmployeeInteractor implements IEmployeeInteractor {

    /**
     * Tracks the employees and their details.
     */
    private final EmployeeTracker tracker;

    /**
     * Represents the transit model which might contain trains and other details.
     */
    private final TransitModel model;

    /**
     * Constructs an EmployeeInteractor instance.
     *
     * @param tracker The employee tracker.
     * @param model   The transit model.
     */
    public EmployeeInteractor(EmployeeTracker tracker, TransitModel model) {
        this.tracker = tracker;
        this.model = model;
    }

    // Javadocs are in the interface. This method overload only exists as default value for the bound.
    public int idGenerator() {
        return idGenerator(999999);
    }

    // Javadocs are in the interface.
    public int idGenerator(int bound) {
        int id;
        int tries = 0;
        Random random = new Random();
        do {
            id = random.nextInt(bound);
            if (tries++ >= 10) throw new IllegalStateException("Could not generate a unique ID");
        } while (tracker.getEmployee(id).isPresent());
        return id;
    };

    /**
     * Registers a new employee based on the given request model.
     *
     * @param name The name of the employee.
     * @param type The type of the employee.
     * @return The newly created {@link EmployeeDTO} object.
     */
    @Override
    public EmployeeDTO registerEmployee(String name, EmployeeType type, int id) {
        switch (type) {
            case ENGINEER:
                TrainEngineer eng = new TrainEngineer(id, name);
                tracker.saveEmployee(eng);
                return toDTO(eng);
            case OPERATOR:
                TrainOperator ope = new TrainOperator(id, name);
                tracker.saveEmployee(ope);
                return toDTO(ope);
            case ADMINISTRATOR:
                Admin adm = new Admin(id, name);
                tracker.saveEmployee(adm);
                return toDTO(adm);
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
    public Optional<EmployeeDTO> find(int staffNumber) {
        Employee employee = tracker.getEmployee(staffNumber)
                .orElse(null);
        if (employee == null) return Optional.empty();
        return Optional.of(toDTO(employee));
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
     * @param trainName   The name of the train.
     * @param job         The job role to be assigned.
     * @throws IllegalArgumentException if either the employee or the train does not exist.
     * @throws IllegalStateException    if there's already an employee assigned to that job on the specified train.
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
        Set<TrainRole> filledRoles = assigned.stream()
                .filter(it -> it.getAssignment().isPresent())
                .map(it -> it.getAssignment().get().getRole())
                .collect(Collectors.toSet());

        if (filledRoles.contains(job)) {
            throw new IllegalStateException("There's already an employee assigned to that job on the specified train");
        }

        employee.setAssignment(new EmployeeAssignment(trainName, job));
        tracker.saveEmployee(employee);
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

        return employees.stream()
                .map(this::toDTO)
                .filter(it -> it.getAssignment().isPresent())
                .filter(it -> it.getAssignment().get().getTrainName().equals(trainName))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployees() {
        return tracker.getEmployeeList().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts an {@link Employee} object to an {@link EmployeeDTO}.
     *
     * @param employee The employee to be converted.
     * @return An {@link EmployeeDTO} representation of the provided employee.
     */
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
