package entity.employee;

import persistence.boundary.EmployeeDataStore;

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
    public void saveEmployee(Employee employee) {
        dataStore.save(employee);
    }

    /**
     * Removes the employee with the given staff number from the list of employees.
     *
     * @param staffNumber The staff number of the employee to be removed.
     */
    public void removeEmployee(int staffNumber) {
        dataStore.delete(staffNumber);
    }

    /**
     * Returns the list of all employees.
     *
     * @return The list of all Employee objects.
     */
    public List<Employee> getEmployeeList() {
        return dataStore.findAll();
    }

    /**
     * Returns the employee with the given staff number.
     *
     * @param employeeNumber The staff number of the employee to be returned.
     * @return The Employee object with the given staff number, or null if no such employee exists.
     */
    public Optional<Employee> getEmployee(int employeeNumber) {
        return dataStore.find(employeeNumber);
    }

}
