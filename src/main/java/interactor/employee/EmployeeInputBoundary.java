package interactor.employee;

import employee.Employee;
import model.train.TrainJob;

import java.util.Optional;

/**
 * Input boundary for the employee management use case.
 */
public interface EmployeeInputBoundary {

    /**
     * Register a new employee.
     * @param requestModel The request model.
     * @return An EmployeeInfo object representing the newly registered employee.
     * @throws IllegalArgumentException If an employee with the same staff number already exists.
     */
    EmployeeInfo registerEmployee(RegisterEmployeeRequest requestModel) throws IllegalArgumentException;

    Optional<EmployeeInfo> getEmployeeInfo(int staffNumber);

    void removeEmployee(int staffNumber);

    boolean assignJob(int staffNumber, String trainName, TrainJob job);

    boolean unassign(int staffNumber);

}
