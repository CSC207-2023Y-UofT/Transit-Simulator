package employee;

import java.util.*;

/**
 *
 */
public class EmployeeTracker {

    private List<Employee> employees = new ArrayList<>();

    /**
     * adds staff to employee list
     * @param employee is an Employee
     */
    public void AddToEmployees(Employee employee) {
        employees.add(employee);
    }

    /**
     * returns list of employees at this transit company
     * @return a List of Employees
     */
    public List<Employee> GetEmployeeList() {
        return employees;
    }

}
