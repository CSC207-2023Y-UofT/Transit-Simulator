package interface_adapter.employee;

import app_business.dto.EmployeeDTO;

import java.util.List;

/**
 * The ManageEmployeesViewModel class represents the view model for the manage employees use case.
 */
public class ManageEmployeesViewModel {
    /**
     * The controller used by the view model to interact with the domain layer.
     */
    private final EmployeeController controller;

    /**
     * Constructs a new ManageEmployeesViewModel with the given controller.
     *
     * @param controller the controller
     */
    public ManageEmployeesViewModel(EmployeeController controller) {
        this.controller = controller;
    }

    /**
     * Gets the data for the table.
     *
     * @return the data in the form of a 2D array
     */
    public Object[][] getTableData() {
        List<EmployeeDTO> employees = controller.getEmployees();

        Object[][] data = new Object[employees.size()][];

        for (int i = 0; i < employees.size(); i++) {
            EmployeeDTO employee = employees.get(i);
            data[i] = new Object[]{employee.getName(), employee.getType(), employee.getStaffNumber(), employee.getAssignment().orElse(null)};
        }

        return data;
    }
}
