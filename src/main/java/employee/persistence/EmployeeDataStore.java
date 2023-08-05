package employee.persistence;

import employee.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmployeeDataStore {
    void remove(int staffNumber) throws IOException;
    void save(Employee employee) throws IOException;
    Optional<Employee> get(int staffNumber) throws IOException;
    List<Employee> getEmployees() throws IOException;
}
