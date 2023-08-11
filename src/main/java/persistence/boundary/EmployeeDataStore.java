package persistence.boundary;

import entity.employee.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Data store interface for employees
 */
public interface EmployeeDataStore extends IntIndexedDataStore<Employee> {}
