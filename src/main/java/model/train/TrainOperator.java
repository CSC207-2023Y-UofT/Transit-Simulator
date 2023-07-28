package model.train;

import employee.Employee;

public class TrainOperator extends Employee {

    public TrainOperator(String name, int num) {
        super(name, num);

    }

    @Override
    public double getBaseSalary() {
        return super.getBaseSalary() + 1000; // TODO change or whatever
    }
}





