package interactor.employee;

import model.train.TrainRole;

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
     * @throws IllegalArgumentException If an employee with the same staff number already exists.
     */
    EmployeeInfo registerEmployee(RegisterEmployeeRequest requestModel) throws IllegalArgumentException;

    /**
     * Get the employee info of an employee.
     *
     * @param staffNumber The staff number of the employee.
     * @return An EmployeeInfo object representing the employee, or an empty Optional if the employee does not exist.
     */
    Optional<EmployeeInfo> getEmployeeInfo(int staffNumber);

    /**
     * Remove an employee.
     *
     * @param staffNumber The staff number of the employee.
     */
    void removeEmployee(int staffNumber);

    /**
     * Assign a job to an employee.
     * @param staffNumber The staff number of the employee.
     * @param trainName The name of the train.
     * @param job The job to assign.
     * @return True if the job was assigned successfully, false if the employee does not exist or the job is already assigned.
     */
    boolean assignJob(int staffNumber, String trainName, TrainRole job);

    /**
     * Unassign a job from an employee.
     * @param staffNumber The staff number of the employee.
     * @return True if the job was unassigned successfully, false if the employee does not exist or the job is not assigned.
     */
    boolean unassign(int staffNumber);

}