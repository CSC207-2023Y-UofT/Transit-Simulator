package app_business.boundary;

import app_business.dto.EmployeeDTO;
import app_business.common.EmployeeType;
import entity.model.train.TrainRole;

import java.util.List;
import java.util.Optional;

/**
 * Input boundary for the employee management use case.
 */
public interface IEmployeeInteractor {

    /**
     * Generate a random ID for an employee, as well as checking if the id is already taken.
     * The default upper bound for the random number is 999999.
     *
     * @return The generated ID.
     */
    int idGenerator();

    /**
     * Generate a random ID for an employee, as well as checking if the id is already taken.
     * The bound specified overwrites the default value of 999999.
     *
     * @param bound The upper bound for the random number.
     * @return The generated ID.
     */
    int idGenerator(int bound);

    /**
     * Register a new employee.
     *
     * @param name The name of the employee.
     * @param type The type of the employee.
     * @param id   The ID of the employee.
     * @return An EmployeeInfo object representing the newly registered employee.
     */
    EmployeeDTO registerEmployee(String name, EmployeeType type, int id);

    /**
     * Get the employee info of an employee.
     *
     * @param staffNumber The staff number of the employee.
     * @return An EmployeeInfo object representing the employee, or an empty Optional if the employee does not exist.
     */
    Optional<EmployeeDTO> find(int staffNumber);

    /**
     * Remove an employee.
     *
     * @param staffNumber The staff number of the employee.
     */
    void removeEmployee(int staffNumber);

    /**
     * Assign a job to an employee.
     *
     * @param staffNumber The staff number of the employee.
     * @param trainName   The name of the train.
     * @param job         The job to assign.
     * @return True if the job was assigned successfully, false if the employee does not exist or the job is already assigned.
     * @throws IllegalArgumentException If the train does not exist, or the employee does not exist.
     * @throws IllegalStateException    If there is already an employee assigned to the job on that train.
     */
    void assignJob(int staffNumber, String trainName, TrainRole job);

    /**
     * Unassign a job from an employee.
     *
     * @param staffNumber The staff number of the employee.
     * @return True if the job was unassigned successfully, false if the employee does not exist or the job is not assigned.
     */
    boolean unassign(int staffNumber);

    /**
     * Get a list of employees assigned to a train.
     *
     * @param trainName The name of the train.
     * @return A list of employees assigned to the train.
     */
    List<EmployeeDTO> getAssignedEmployees(String trainName);

    List<EmployeeDTO> getEmployees();

}
