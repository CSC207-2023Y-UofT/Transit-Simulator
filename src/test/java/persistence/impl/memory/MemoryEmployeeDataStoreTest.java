package persistence.impl.memory;

import entity.employee.Admin;
import entity.employee.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link MemoryEmployeeDataStore}
 */
class MemoryEmployeeDataStoreTest {

    /**
     * Data store
     */
    private static final MemoryEmployeeDataStore STORE = new MemoryEmployeeDataStore();

    /**
     * Test adding an employee
     */
    @Test
    public void testAdd() {
        Employee employee = new Admin(1, "John");
        STORE.save(employee);
        assertTrue(STORE.existsById(1));
    }

    /**
     * Test deleting an employee
     */
    @Test
    public void testDelete() {
        Employee employee = new Admin(1, "John");
        STORE.save(employee);
        assertTrue(STORE.existsById(1));
        STORE.delete(1);
        assertFalse(STORE.existsById(1));
    }

    /**
     * Test deleting all employees
     */
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

    /**
     * Test finding an employee
     */
    @Test
    public void testFind() {
        Employee employee = new Admin(1, "John");
        STORE.save(employee);
        assertTrue(STORE.existsById(1));
        assertEquals(employee, STORE.find(1).orElseThrow());
    }

    /**
     * Test finding all employees
     */
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

    /**
     * Test checking if an employee exists
     */
    @Test
    public void testExistsById() {
        Employee employee = new Admin(1, "John");
        STORE.save(employee);
        assertTrue(STORE.existsById(1));
        assertFalse(STORE.existsById(23));
    }

}