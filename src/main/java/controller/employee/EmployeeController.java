package controller.employee;

import interactor.employee.EmployeeDTO;
import interactor.employee.EmployeeType;
import interactor.employee.IEmployeeInteractor;
import interactor.employee.RegisterEmployeeRequest;

import java.util.Optional;

public class EmployeeController {
    private final IEmployeeInteractor interactor;

    public EmployeeController(IEmployeeInteractor interactor) {
        this.interactor = interactor;
    }

    public Optional<EmployeeDTO> login(int staffNumber) {
        return interactor.getEmployeeInfo(staffNumber);
    }


    public EmployeeDTO registerEmployee(String name, EmployeeType type) throws EmployeeRegistrationException {
        return interactor.registerEmployee(
                new RegisterEmployeeRequest(type)
        );
    }
}
