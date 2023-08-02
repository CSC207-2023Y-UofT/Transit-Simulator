package employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Admin class extends the Employee class.
 * It represents an administrative employee who has the authority to manage, pay, and assign other employees to lines.
 * The Admin employee earns four times the base monthly salary of a standard Employee.
 */
public class Admin extends Employee {

    /**
     * A mapping of line numbers to the list of Employees assigned to each line.
     */
    private Map<Integer, List<Employee>> lineToStaff = new HashMap<>();

    /**
     * Constructs a new Admin object with the given employee number.
     *
     * @param num The unique employee number.
     */
    public Admin(int num) {
        super(num);
    }

    /**
     * Returns the monthly salary of this Admin, which is four times the base salary.
     *
     * @return The monthly salary of this Admin.
     */
    @Override
    public double getMonthlySalary() {
        return 4 * super.getMonthlySalary();
    }

    /**
     * Sets the payment status of this Admin.
     *
     * @param isPaid The new payment status. True if the Admin has been paid; otherwise false.
     */
    @Override
    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    /**
     * Returns the payment status of this Admin.
     *
     * @return True if the Admin has been paid; otherwise false.
     */
    @Override
    boolean getPaid() {
        return this.isPaid;
    }

    /**
     * Pays the specified employee, changing their payment status to true.
     *
     * @param employee The Employee to be paid.
     */
    public void payEmployee(Employee employee) {
        employee.setPaid(true);
    }

    /**
     * Assigns the specified employee to the specified line.
     *
     * @param line     The line number to which the employee is to be assigned.
     * @param employee The Employee to be assigned.
     */
    public void addEmployeeToLine(Integer line, Employee employee) {
        List<Employee> curr = lineToStaff.computeIfAbsent(line, ArrayList::new);
        curr.add(employee);
    }

    /**
     * Returns the line number to which the specified employee is assigned, or null if the employee is not assigned.
     *
     * @param employee The Employee whose assignment is to be checked.
     * @param employee is an Employee
     * @return The line number to which the employee is assigned, or null if the employee is not assigned.
     * returns the line that the employee is assigned to
     * @return line which is an Integer
     */
    public Integer checkLine(Employee employee) {
        for (Integer key : lineToStaff.keySet()) {
            if (lineToStaff.get(key).contains(employee)) {
                return key;
            }
        }
        return null;
    }


    /**
     * returns lineToStaff which is a HashMap of Lines to List of Employees
     *
     * @return a HashMap of Lines to List of Employees
     */
    public Map<Integer, List<Employee>> getLineToStaff() {
        return lineToStaff;
    }

    /**
     * removes all Employees from the lineToStaff HashMap
     */
    public void clearLineToStaff() {
        lineToStaff.clear();

    }
}