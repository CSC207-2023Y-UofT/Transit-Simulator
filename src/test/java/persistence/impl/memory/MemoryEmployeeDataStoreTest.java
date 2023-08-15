package persistence.impl.memory;

import entity.employee.Admin;
import entity.employee.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryEmployeeDataStoreTest {

    private static final MemoryEmployeeDataStore STORE = new MemoryEmployeeDataStore();

    @Test
    public void testAdd() {
        Employee employee = new Admin(1, "John");
        STORE.save(employee);
        assertTrue(STORE.existsById(1));
    }

    @Test
    public void testDelete() {
        Employee employee = new Admin(1, "John");
        STORE.save(employee);
        assertTrue(STORE.existsById(1));
        STORE.delete(1);
        assertFalse(STORE.existsById(1));
    }

    @Test
    public void testDeleteAll() {
        Employee employee = new Admin(1, "John");
        Employee employee2 = new Admin(2, "John");
        STORE.save(employee);
        STORE.save(employee2);
        assertTrue(STORE.existsById(1));
        assertTrue(STORE.existsById(2));
        STORE.deleteAll();
        assertFalse(STORE.existsById(1));
        assertFalse(STORE.existsById(2));
    }

    @Test
    public void testFind() {
        Employee employee = new Admin(1, "John");
        STORE.save(employee);
        assertTrue(STORE.existsById(1));
        assertEquals(employee, STORE.find(1).orElseThrow());
    }

    @Test
    public void testFindAll() {
        Employee employee = new Admin(1, "John");
        Employee employee2 = new Admin(2, "John");
        STORE.save(employee);
        STORE.save(employee2);
        assertTrue(STORE.existsById(1));
        assertTrue(STORE.existsById(2));
        assertEquals(2, STORE.findAll().size());
    }

    @Test
    public void testExistsById() {
        Employee employee = new Admin(1, "John");
        STORE.save(employee);
        assertTrue(STORE.existsById(1));
        assertFalse(STORE.existsById(2));
    }



}