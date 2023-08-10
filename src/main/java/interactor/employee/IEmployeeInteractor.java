package interactor.employee;

import employee.Employee;
import model.train.TrainRole;

import java.util.List;
import java.util.Optional;

/**
 * Input boundary for the employee management use case.
 */
public interface IEmployeeInteractor {

    /**
     * Register a new employee.
     *
     * @param requestModel The request model.
     * @return An EmployeeInfo object representing the newly registered employee.
     */
    EmployeeDTO registerEmployee(RegisterEmployeeRequest requestModel);

    /**
     * Get the employee info of an employee.
     *
     * @param staffNumber The staff number of the employee.
     * @return An EmployeeInfo object representing the employee, or an empty Optional if the employee does not exist.
     */
    Optional<EmployeeDTO> getEmployeeInfo(int staffNumber);

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

    List<EmployeeDTO> getAssignedEmployees(String trainName);

    List<EmployeeDTO> getEmployees();

}
