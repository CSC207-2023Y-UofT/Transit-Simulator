package employee.persistence;

import employee.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Data store interface for employees
 */
public interface EmployeeDataStore {
    /**
     * Removes an employee
     * @param staffNumber The staff number of the employee to remove
     * @throws IOException If an I/O error occurs
     */
    void remove(int staffNumber) throws IOException;

    /**
     * Saves an employee
     * @param employee The employee to save
     * @throws IOException If an I/O error occurs
     */
    void save(Employee employee) throws IOException;

    /**
     * Gets an employee
     * @param staffNumber The staff number of the employee to get
     * @return The employee, or empty if the employee was not found
     * @throws IOException If an I/O error occurs
     */
    Optional<Employee> get(int staffNumber) throws IOException;

    /**
     * Gets all employees
     * @return The list of all employees
     * @throws IOException If an I/O error occurs
     */
    List<Employee> getEmployees() throws IOException;
}
