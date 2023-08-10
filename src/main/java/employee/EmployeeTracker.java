package employee;

import employee.persistence.EmployeeDataStore;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * The EmployeeTracker class maintains a list of Employee objects.
 * It provides functionality to add employees to the list and retrieve the list of employees.
 */
public class EmployeeTracker {

    /**
     * The data store for employees
     */
    private final EmployeeDataStore dataStore;

    /**
     * Creates a new EmployeeTracker object.
     */
    public EmployeeTracker(EmployeeDataStore dataStore) {
        this.dataStore = dataStore;
    }

    /**
     * Adds the given Employee to the list of employees.
     *
     * @param employee The Employee object to be added.
     */
    public void addEmployee(Employee employee) {
        try {
            dataStore.save(employee);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes the given Employee from the list of employees.
     *
     * @param employee The Employee object to be removed.
     */
    public void removeEmployee(Employee employee) {
        try {
            dataStore.remove(employee.getStaffNumber());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Removes the employee with the given staff number from the list of employees.
     *
     * @param staffNumber The staff number of the employee to be removed.
     */
    public void removeEmployee(int staffNumber) {
        try {
            dataStore.remove(staffNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the list of all employees.
     *
     * @return The list of all Employee objects.
     */
    public List<Employee> getEmployeeList() {
        try {
            return dataStore.getEmployees();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the employee with the given staff number.
     *
     * @param employeeNumber The staff number of the employee to be returned.
     * @return The Employee object with the given staff number, or null if no such employee exists.
     */
    public Optional<Employee> getEmployee(int employeeNumber) {
        try {
            return dataStore.get(employeeNumber);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
