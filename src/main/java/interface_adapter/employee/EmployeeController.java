package interface_adapter.employee;

import app_business.boundary.IEmployeeInteractor;
import app_business.common.EmployeeType;
import app_business.dto.EmployeeDTO;
import entity.model.train.TrainRole;

import java.util.List;
import java.util.Optional;

public class EmployeeController {
    private final IEmployeeInteractor interactor;

    public EmployeeController(IEmployeeInteractor interactor) {
        this.interactor = interactor;
    }

    public Optional<EmployeeDTO> login(int staffNumber) {
        return interactor.find(staffNumber);
    }

    public EmployeeDTO registerEmployee(String name, EmployeeType type) {
        return interactor.registerEmployee(name, type);
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

    public Optional<EmployeeDTO> find(int staffNumber) {
        return interactor.find(staffNumber);
    }

    public List<EmployeeDTO> getEmployees() {
        return interactor.getEmployees();
    }
}
