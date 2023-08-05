package employee.persistence;

import employee.Employee;

import java.util.Optional;

public interface EmployeeDataStore {
    void save(Employee employee);
    Optional<Employee> get(int staffNumber);
}
