package employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin extends Employee {

    private boolean isPaid = false;

    Map<Integer, List<Employee>> lineToStaff = new HashMap<Integer, List<Employee>>();

    public Admin(int num) {
        super(num);
    }

    /**
     * returns what this employee obejct is being paid
     */
    @Override
    public double getMonthlySalary() {
        return 4 * super.getMonthlySalary();
    }

    /**
     * sets the isPaid boolean to true or false depending on if this TrainOperator was paid
     * @param isPaid
     */
    @Override
    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    /**
     * pays inferior employees
     * @param employee
     */
    public void payEmployee(Employee employee) {
        employee.setPaid(true);
    }


    /**
     * add the employee object to the specified line
     * @param employee
     * @param line
     */
    public void addEmployeeToLine(Integer line, Employee employee) {

        List<Employee> curr = lineToStaff.computeIfAbsent(line, ArrayList::new);
        curr.add(employee);
    }


    /**
     * returns the line that the employee is assigned to
     * @param employee
     * @return line
     */
    public Integer checkLine(Employee employee) {
        for (Integer key : lineToStaff.keySet()) {
            if (lineToStaff.get(key).contains(employee)) {
                return key;
            }
        }
        return null;
    }
}
