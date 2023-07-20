import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrainEngineer extends Employee implements AddToLine {

    private List<Employee> employees = new ArrayList<>();
    HashMap<String, Employee> lineToStaff = new HashMap<String, Employee>();

    public TrainEngineer(String name, int num) {
        super(name, num);
    }

    /**
     * returns what this employee obejct is being paid
     */
    @Override
    public double getMonthlySalary() {
        return 1.2 * super.getBaseSalary();
    }

    /**
     * receives payment for an employee object
     */
    @Override
    public void setPaid(boolean isPaid) {
        TrainEngineer.isPaid = isPaid; //when this object is paid, how is this information retained or updated?
    }

    /**
     * fixes Train object's maintenance issues
     */
    public void fixMaintenanceIssue(Train train) {
        if (train.needsMaintenance() = True) {
            train.setMaintenance(False);
        }
    }

    /**
     * add the employee object to the specified line
     * @param employee
     * @param line
     */
    public void addEmployeeToLine(String line, Employee employee){
        lineToStaff.put(line, employee);
    }

}
