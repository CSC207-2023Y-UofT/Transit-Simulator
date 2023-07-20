import java.util.HashMap;

public class TrainOperator extends Employee implements AddToLine {

    HashMap<String, Employee> lineToStaff = new HashMap<String, Employee>();

    public TrainOperator(String name, int num) {
        super(name, num);

    }

    /**
     * returns what this employee obejct is being paid
     */
    @Override
    public double getMonthlySalary() {
        return super.getBaseSalary();
    }

    /**
     * receives payment for an employee object
     */
    @Override
    public void setPaid(boolean isPaid) {
        TrainOperator.isPaid = isPaid; // should i be doing admin.ispaid or super.ispaid?
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





