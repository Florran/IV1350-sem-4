package se.kth.iv1350.electricBike.model;

/**
 * Represents a single piece of work that needs to be done to fix a bike.
 */
public class RepairTask {
    private final String description;
    private boolean isComplete;

    /**
     * Creates a new repair task.
     * By default, a new task is not marked as complete.
     *
     * @param description A short explanation of what needs to be repaired.
     */
    public RepairTask(String description) {
        this.description = description;
        this.isComplete = false;
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
     * Checks if the repair task has been finished.
     *
     * @return true if the task is done, otherwise false.
     */
    public boolean isComplete() {
        return this.isComplete;
    }
}