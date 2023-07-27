public interface AddToLine {

    /**
     * add the employee object to the specified line
     * @param line
     * @param employee
     */
    public void addEmployeeToLine(Integer line, Employee employee);

    /**
     * returns the line that the employee is assigned to
     * @param employee
     */
    public Integer checkLine(Employee employee);

}

