package persistence.impl;

import entity.employee.Admin;
import entity.employee.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import persistence.DataStorage;
import persistence.boundary.EmployeeDataStore;
import persistence.impl.file.FileEmployeeDataStore;
import util.AsyncWriteIOProvider;
import util.DeflateCompressionProvider;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileEmployeeDataStoreTest {

    private static EmployeeDataStore data;

    @BeforeAll
    public static void setUp() {
        File directory = new File("test-employees");
        data = new FileEmployeeDataStore(directory);

        DataStorage.init(
                new AsyncWriteIOProvider(),
                new DeflateCompressionProvider()
        );
    }

    @Test
    void delete() {
        Employee employee = new Admin(123, "James");
        data.save(employee);
        assert data.existsById(123);
        data.delete(123);
        assert !data.existsById(123);
    }

    @Test
    void deleteAll() {
        Employee employee = new Admin(123, "James");
        data.save(employee);
        assert data.existsById(123);

        Employee employee2 = new Admin(456, "James2");
        data.save(employee2);
        assert data.existsById(456);

        data.deleteAll();
        assert !data.existsById(123);
        assert !data.existsById(456);
    }

    @Test
    void save() {
        Employee employee = new Admin(123, "James");
        data.save(employee);
        assert data.existsById(123);
    }

    @Test
    void find() {
        Employee employee = new Admin(123, "James");
        data.save(employee);
        assert data.existsById(123);
        Employee copy = data.find(123).orElseThrow();
        assertEquals(employee.getStaffNumber(), copy.getStaffNumber());
        assertEquals(employee.getName(), copy.getName());
    }

    @Test
    void findAll() {
        Employee employee = new Admin(123, "James");
        data.save(employee);
        assert data.existsById(123);

        Employee employee2 = new Admin(456, "James2");
        data.save(employee2);
        assert data.existsById(456);

        assert data.findAll().size() == 2;
    }

    @Test
    void existsById() {
        Employee employee = new Admin(123, "James");
        data.save(employee);
        assert data.existsById(123);
    }
}