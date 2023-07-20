public class TrainOperator extends Employee {

    public TrainOperator(String name, int num) {
        super(name, num);
    }

    /**
     * returns what this employee obejct is being paid
     */
    @Override
    public double getMonthlySalary() {
        return 7000;
    }


}
