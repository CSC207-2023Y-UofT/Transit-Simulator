package persistence.impl;

import entity.employee.Employee;
import persistence.DataStorage;
import persistence.boundary.EmployeeDataStore;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * File data store for employees.
 */
public class FileEmployeeDataStore implements EmployeeDataStore {

    /**
     * The directory where the employee files are stored
     */
    private final File directory;

    /**
     * Creates a new file employee data store
     *
     * @param directory The directory where the employee files are stored
     */
    public FileEmployeeDataStore(File directory) {
        this.directory = directory;
        directory.mkdirs();
    }

    /**
     * Returns the file for the given staff number
     */
    private File getFile(int staffNumber) {
        return new File(directory, staffNumber + ".staff");
    }

    /**
     * Removes an employee from
     *
     * @param staffNumber The staff number of the employee to remove
     * @throws IOException If an I/O error occurs
     */
    // Java docs for the following methods are in the interface
    @Override
    public void remove(int staffNumber) throws IOException {
        File file = getFile(staffNumber);
        if (!DataStorage.getIO().exists(file)) return;
        Files.delete(getFile(staffNumber).toPath());
    }

    /**
     * Saves an employee
     *
     * @param employee The employee to save
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void save(Employee employee) throws IOException {
        int staffNumber = employee.getStaffNumber();
        File file = getFile(staffNumber);
        boolean unused = file.createNewFile(); // So no warning
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(out);
        objectOut.writeObject(employee);
        objectOut.close();
        byte[] data = out.toByteArray();
        DataStorage.getIO().write(file, data);
    }

    /**
     * Gets an employee
     *
     * @param staffNumber The staff number of the employee to get
     * @return The employee, or empty if the employee was not found
     * @throws IOException If an I/O error occurs
     */
    @Override
    public Optional<Employee> get(int staffNumber) throws IOException {
        File file = getFile(staffNumber);
        if (!DataStorage.getIO().exists(file)) return Optional.empty();
        return read(file);
    }

    /**
     * Reads an employee from a file
     *
     * @param file The file to read from
     * @return The employee, or empty if the employee could not be read
     */
    private Optional<Employee> read(File file) {
        try {
            byte[] bytes = DataStorage.getIO().read(file);
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            return Optional.ofNullable((Employee) new ObjectInputStream(in).readObject());
        } catch (ClassNotFoundException | IOException e) {
            return Optional.empty();
        }
    }

    /**
     * Gets all employees
     *
     * @return The list of all employees
     * @throws IOException If an I/O error occurs
     */
    @Override
    public List<Employee> getEmployees() {
        File[] files = directory.listFiles();
        if (files == null) return new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        for (File file : files) {
            if (!file.getName().endsWith(".staff")) {
                continue;
            }

            Optional<Employee> employee = read(file);
            employee.ifPresent(employees::add);
        }

        return employees;
    }
}
