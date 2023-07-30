package employee;

import java.util.*;

/**
 * The EmployeeTracker class maintains a list of Employee objects.
 * It provides functionality to add employees to the list and retrieve the list of employees.
 */
public class EmployeeTracker {

    /**
     * The list of employees.
     */
    private List<Employee> employees = new ArrayList<>();

    /**
     * Adds the given Employee to the list of employees.
     *
     * @param employee The Employee object to be added.
     */
    public void addToEmployees(Employee employee) {
        employees.add(employee);
    }

    /**
     * Returns the list of all employees.
     *
     * @return The list of all Employee objects.
     */
    public List<Employee> getEmployeeList() {
        return employees;
    }

}
