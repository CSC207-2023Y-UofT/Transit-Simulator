package app_business.common;

import entity.model.train.TrainRole;

import java.io.Serializable;

/**
 * Immutable Data Class to represent an employee assignment
 */
public class EmployeeAssignment implements Serializable {

    /**
     * The name of the train that the employee is assigned to
     */
    private final String trainName;

    /**
     * The role that the employee is assigned to
     */
    private final TrainRole role;

    /**
     * Create a new EmployeeAssignment object
     *
     * @param trainName The name of the train that the employee is assigned to
     * @param role      The role of the employee on the train
     */
    public EmployeeAssignment(String trainName, TrainRole role) {
        this.trainName = trainName;
        this.role = role;
    }

    /**
     * Get the name of the train that the employee is assigned to
     *
     * @return The name of the train
     */
    public String getTrainName() {
        return trainName;
    }

    /**
     * Get the role of the employee on the train
     *
     * @return The role of the employee
     */
    public TrainRole getRole() {
        return role;
    }

    /**
     * Return a string representation of the employee assignment.
     *
     * @return A string representation of the employee assignment.
     */
    @Override
    public String toString() {
        return role + " on " + trainName;
    }
}
