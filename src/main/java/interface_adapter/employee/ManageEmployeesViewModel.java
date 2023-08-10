package interface_adapter.employee;

import app_business.employee.EmployeeDTO;

import java.util.List;

public class ManageEmployeesViewModel {
    private final EmployeeController controller;

    public ManageEmployeesViewModel(EmployeeController controller) {
        this.controller = controller;
    }

    public Object[][] getTableData() {
        List<EmployeeDTO> employees = controller.getEmployees();

        Object[][] data = new Object[employees.size()][];

        for (int i = 0; i < employees.size(); i++) {
            EmployeeDTO employee = employees.get(i);
            data[i] = new Object[] {employee.getName(), employee.getType(), employee.getStaffNumber(), employee.getAssignment().orElse(null)};
        }

        return data;
    }
}
