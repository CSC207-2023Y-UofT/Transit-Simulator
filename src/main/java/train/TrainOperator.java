package train;

import train.old.Employee;

import java.util.HashMap;

public class TrainOperator extends Employee {

    public TrainOperator(String name, int num) {
        super(name, num);

    }

    @Override
    public double getMonthlySalary() {
        return super.getMonthlySalary() + 1000; // TODO change or whatever
    }
}





