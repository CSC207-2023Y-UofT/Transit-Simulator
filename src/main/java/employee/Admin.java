package employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admin extends Employee {

    Map<Integer, List<Employee>> lineToStaff = new HashMap<>();

    public Admin(int num) {
        super(num);
    }

    /**
     * returns what this employee object is being paid
     */
    @Override
    public double getMonthlySalary() {
        return 4 * super.getMonthlySalary();
    }

    /**
     * sets the isPaid boolean to true or false depending on if this TrainOperator was paid
     * @param isPaid is a boolean
     */
    @Override
    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    /**
     * pays inferior employees
     * @param employee is an Employee
     */
    public void payEmployee(Employee employee) {
        employee.setPaid(true);
    }


    /**
     * add the employee object to the specified line
     * @param employee is an Employee
     * @param line is an Integer
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
