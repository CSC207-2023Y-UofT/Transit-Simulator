public class Admin extends Employee {
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

}
