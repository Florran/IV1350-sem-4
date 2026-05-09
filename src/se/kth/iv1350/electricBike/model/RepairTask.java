package se.kth.iv1350.electricBike.model;

import se.kth.iv1350.electricBike.integration.RepairTaskDTO;

/**
 * Represents a single piece of work that needs to be done to fix a bike.
 */
public class RepairTask {
    private final String description;
    private final double cost;
    private boolean isComplete;

    /**
     * Creates a new repair task.
     *
     * @param description A short explanation of what needs to be repaired.
     * @param cost        The cost of this specific repair task.
     */
    public RepairTask(String description, double cost) {
        this.description = description;
        this.cost = cost;
        this.isComplete = false;
    }

    /**
     * Creates a new repair task with a specific completion status.
     *
     * @param description A short explanation of what needs to be repaired.
     * @param cost        The cost of this specific repair task.
     * @param isComplete  The completion status.
     */
    public RepairTask(String description, double cost, boolean isComplete) {
        this.description = description;
        this.cost = cost;
        this.isComplete = isComplete;
    }

    /**
     * Gets the description of this task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the cost of this task.
     *
     * @return The task cost.
     */
    public double getCost() {
        return this.cost;
    }

    /**
     * Checks if the repair task has been finished.
     *
     * @return true if the task is done, otherwise false.
     */
    public boolean isComplete() {
        return this.isComplete;
    }

    /**
     * Creates a Data Transfer Object representing this task.
     * * @return A new instance of RepairTaskDTO containing task details.
     */
    public RepairTaskDTO createDTO() {
        return new RepairTaskDTO(description, cost, isComplete);
    }
}