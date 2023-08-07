package employee;

import employee.persistence.EmployeeDataStore;

import java.io.IOException;
import java.util.*;

/**
 * The EmployeeTracker class maintains a list of Employee objects.
 * It provides functionality to add employees to the list and retrieve the list of employees.
 */
public class EmployeeTracker {

    /** Data store used to manage Employee persistence. */
    private final EmployeeDataStore dataStore;

    /**
     * Constructs a new EmployeeTracker with the specified data store.
     *
     * @param dataStore The data store used for employee persistence.
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
     * Removes the Employee with the specified staff number from the data store.
     *
     * @param staffNumber The staff number of the Employee to be removed.
     * @throws RuntimeException if there's an IO error while removing the employee.
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
     * Retrieves an Employee with the specified employee number from the data store.
     *
     * @param employeeNumber The number of the Employee to be retrieved.
     * @return An Optional containing the Employee if found, or empty if not found.
     */
    public Optional<Employee> getEmployee(int employeeNumber) {
        try {
            return dataStore.get(employeeNumber);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
