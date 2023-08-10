package controller.employee;

import interactor.employee.*;
import model.train.TrainRole;

import java.util.List;
import java.util.Optional;

public class EmployeeController {
    private final IEmployeeInteractor interactor;

    public EmployeeController(IEmployeeInteractor interactor) {
        this.interactor = interactor;
    }

    public Optional<EmployeeDTO> login(int staffNumber) {
        return interactor.getEmployeeInfo(staffNumber);
    }

    public EmployeeDTO registerEmployee(String name, EmployeeType type) {
        return interactor.registerEmployee(new RegisterEmployeeRequest(type, name));
    }

    public void assignEmployee(int staffNumber, String trainName, TrainRole role) {
        interactor.assignJob(staffNumber, trainName, role);
    }

    public void unassignEmployee(int staffNumber) {
        interactor.unassign(staffNumber);
    }

    public void removeEmployee(int staffNumber) {
        interactor.removeEmployee(staffNumber);
    }

    public List<EmployeeDTO> getEmployees() {
        return interactor.getEmployees();
    }
}
