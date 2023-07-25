import java.util.HashMap;
import java.util.List;

public class Admin extends Employee implements AddToLine{

    HashMap<Integer, List<Employee>> lineToStaff = new HashMap<Integer, List<Employee>>();

    public Admin(String name, int num) {
        super(name, num);
    }

    /**
     * returns what this employee obejct is being paid
     */
    @Override
    public double getMonthlySalary() {
        return 4 * super.getBaseSalary();
    }

    /**
     * receives payment for an employee object
     */
    @Override
    public void setPaid(boolean isPaid) {
        Admin.isPaid = isPaid; // should i be doing admin.ispaid or super.ispaid?
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
    public void addEmployeesToLine(Integer line, List<Employee> employee){

        lineToStaff.put(line, employee);
    }

    /**
     * returns the line that the employee is assigned to
     * @param employee
     * @return
     */
    public String checkLine(Employee employee) {
        for (String key : lineToStaff.keySet()) {
            if (lineToStaff.get(key).contains(employee)) {
                return key;
            }
        }
        return null;
    }
}
