package persistence.impl;

import entity.employee.Employee;
import persistence.boundary.EmployeeDataStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Memory data store for employees, used in tests for other classes
 */
public class MemoryEmployeeDataStore implements EmployeeDataStore {

    /**
     * The list of employees
     */
    private final List<Employee> employeeList = new ArrayList<>();

    // Inherited javadocs
    @Override
    public void delete(int staffNumber) {
        employeeList.removeIf(employee -> employee.getStaffNumber() == staffNumber);
    }

    // Inherited javadocs
    @Override
    public void deleteAll() {
        employeeList.clear();
    }

    // Inherited javadocs
    @Override
    public void save(Employee employee) {
        delete(employee.getStaffNumber());
        employeeList.add(employee);
    }

    // Inherited javadocs
    @Override
    public Optional<Employee> find(int staffNumber) {
        return employeeList.stream()
                .filter(employee -> employee.getStaffNumber() == staffNumber)
                .findFirst();
    }

    // Inherited javadocs
    @Override
    public List<Employee> findAll()  {
        return employeeList;
    }

    @Override
    public boolean existsById(int id) {
        return employeeList.stream().anyMatch(employee -> employee.getStaffNumber() == id);
    }
}
