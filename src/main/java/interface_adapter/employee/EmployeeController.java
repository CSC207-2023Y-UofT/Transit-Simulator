package interface_adapter.employee;

import app_business.boundary.IEmployeeInteractor;
import app_business.common.EmployeeType;
import app_business.dto.EmployeeDTO;
import entity.model.train.TrainRole;

import java.util.List;
import java.util.Optional;

/**
 * Controller responsible for managing employee-related operations.
 * This class serves as a bridge between the interface layer and the business logic layer,
 * utilizing the {@link IEmployeeInteractor} for its operations.
 */
public class EmployeeController {
    private final IEmployeeInteractor interactor;

    /**
     * Constructs a new EmployeeController with a given employee interactor.
     *
     * @param interactor The interactor responsible for employee-related operations.
     */
    public EmployeeController(IEmployeeInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Finds and retrieves an employee based on the provided staff number.
     *
     * @param staffNumber The unique identifier of the employee.
     * @return An {@link Optional} containing the {@link EmployeeDTO} if found, or an empty Optional if not.
     */
    public Optional<EmployeeDTO> login(int staffNumber) {
        return interactor.find(staffNumber);
    }

    /**
     * Registers a new employee with the provided name and type.
     *
     * @param name The name of the employee.
     * @param type The type of the employee, represented by {@link EmployeeType}.
     * @return An {@link EmployeeDTO} object representing the newly registered employee.
     */
    public EmployeeDTO registerEmployee(String name, EmployeeType type) {
        return interactor.registerEmployee(name, type);
    }

    /**
     * Assigns a specific role to an employee on a train.
     *
     * @param staffNumber The unique identifier of the employee.
     * @param trainName   The name of the train.
     * @param role        The role to be assigned, represented by {@link TrainRole}.
     * @throws IllegalArgumentException If the train does not exist.
     * @throws IllegalStateException    If there is already an employee assigned to the job on that train.
     */
    public void assignEmployee(int staffNumber, String trainName, TrainRole role) {
        interactor.assignJob(staffNumber, trainName, role);
    }

    /**
     * Unassigns an employee from their current role.
     *
     * @param staffNumber The unique identifier of the employee.
     */
    public void unassignEmployee(int staffNumber) {
        interactor.unassign(staffNumber);
    }

    /**
     * Removes an employee record based on the provided staff number.
     *
     * @param staffNumber The unique identifier of the employee to be removed.
     */
    public void removeEmployee(int staffNumber) {
        interactor.removeEmployee(staffNumber);
    }

    /**
     * Finds and retrieves an employee based on the provided staff number.
     *
     * @param staffNumber The unique identifier of the employee.
     * @return An {@link Optional} containing the {@link EmployeeDTO} if found, or an empty Optional if not.
     */
    public Optional<EmployeeDTO> find(int staffNumber) {
        return interactor.find(staffNumber);
    }

    /**
     * Retrieves a list of all registered employees.
     *
     * @return A list of {@link EmployeeDTO} objects representing all employees.
     */
    public List<EmployeeDTO> getEmployees() {
        return interactor.getEmployees();
    }
}
