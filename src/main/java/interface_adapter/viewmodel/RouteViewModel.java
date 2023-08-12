package interface_adapter.viewmodel;

import app_business.common.EmployeeAssignment;
import app_business.dto.EmployeeDTO;
import interface_adapter.controller.EmployeeController;

public class RouteViewModel {
    private EmployeeDTO employee;
    private final EmployeeController employees;
    public RouteViewModel(EmployeeDTO employee, EmployeeController employees) {
        this.employee = employee;
        this.employees = employees;
    }

    /**
     * Update the employee object
     */
    private void updateEmployee() {
        employees.find(employee.getStaffNumber()).ifPresent(e -> employee = e);
    }

    /**
     * Get what to say on screen
     */
    public String getWhatToSay() {

        updateEmployee();
        if (employee.getAssignment().isEmpty()) return "You are unassigned";

        EmployeeAssignment assignment = employee.getAssignment().get();
        String trainName = assignment.getTrainName();

        var assigned = employees.byAssignment(trainName);

        String otherNames = assigned.stream()
                .filter(e -> e.getStaffNumber() != employee.getStaffNumber())
                .map(EmployeeDTO::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("no one");

        return "You are assigned to " + trainName + " as " + assignment.getRole() + " with " + otherNames;

    }
}
