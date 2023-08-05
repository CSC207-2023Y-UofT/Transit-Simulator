package interactor.employee;

import employee.Employee;
import model.train.TrainJob;

import java.util.Optional;

public interface EmployeeInputBoundary {
    EmployeeInfo registerEmployee(RegisterEmployeeRequest requestModel);

    Optional<EmployeeInfo> getEmployeeInfo(int staffNumber);

    void removeEmployee(int staffNumber);

    boolean assignJob(int staffNumber, String trainName, TrainJob job);

    boolean unassign(int staffNumber);

}
