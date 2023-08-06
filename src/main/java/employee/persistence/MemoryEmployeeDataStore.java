package employee.persistence;

import employee.Employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryEmployeeDataStore implements EmployeeDataStore {
    private final List<Employee> employeeList = new ArrayList<>();

    @Override
    public void remove(int staffNumber) throws IOException {
        employeeList.removeIf(employee -> employee.getStaffNumber() == staffNumber);
    }

    @Override
    public void save(Employee employee) throws IOException {
        remove(employee.getStaffNumber());
        employeeList.add(employee);
    }

    @Override
    public Optional<Employee> get(int staffNumber) throws IOException {
        return employeeList.stream().filter(employee -> employee.getStaffNumber() == staffNumber).findFirst();
    }

    @Override
    public List<Employee> getEmployees() throws IOException {
        return employeeList;
    }
}
