package se.kth.iv1350.electricBike.integration;

/**
 * Data transfer object containing information about a repair task.
 */
public final class RepairTaskDTO {
    private final String description;
    private final double cost;
    private final boolean complete;

    /**
     * Creates a new repair task DTO.
     * 
     * @param description The task description.
     * @param cost        The cost of the repair task.
     * @param complete    Whether the task is done.
     */
    public RepairTaskDTO(String description, double cost, boolean complete) {
        this.description = description;
        this.cost = cost;
        this.complete = complete;
    }

    /**
     * Gets the description of this task.
     * 
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the cost of this task.
     * 
     * @return The task cost.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Checks if the repair task has been finished.
     * 
     * @return true if the task is done, otherwise false.
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Returns a human-readable string with the task description, cost, and status.
     * 
     * @return A single-line string.
     */
    @Override
    public String toString() {
        String status = complete ? "done" : "pending";
        return description + " - " + cost + " kr (" + status + ")";
    }
}