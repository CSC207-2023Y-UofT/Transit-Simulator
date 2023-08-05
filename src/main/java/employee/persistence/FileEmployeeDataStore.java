package employee.persistence;

import employee.Employee;

import java.io.*;
import java.nio.file.Files;
import java.util.Optional;

public class FileEmployeeDataStore implements EmployeeDataStore {

    private final File directory;

    public FileEmployeeDataStore(File directory) {
        this.directory = directory;
    }

    private File getFile(int staffNumber) {
        return new File(directory, staffNumber + ".staff");
    }

    @Override
    public void remove(int staffNumber) throws IOException {
        File file = getFile(staffNumber);
        if (!file.exists()) return;
        Files.delete(getFile(staffNumber).toPath());
    }

    @Override
    public void save(Employee employee) throws IOException {
        int staffNumber = employee.getStaffNumber();
        File file = getFile(staffNumber);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(out);
        objectOut.writeObject(employee);
        objectOut.close();
        byte[] data = out.toByteArray();
        Files.write(file.toPath(), data);
    }

    @Override
    public Optional<Employee> get(int staffNumber) throws IOException {
        File file = getFile(staffNumber);
        if (!file.exists()) {
            return Optional.empty();
        }
        try {
            byte[] data = Files.readAllBytes(file.toPath());
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            return Optional.ofNullable((Employee) new ObjectInputStream(in).readObject());
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }
}
