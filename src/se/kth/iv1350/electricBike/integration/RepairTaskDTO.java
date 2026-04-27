package se.kth.iv1350.electricBike.integration;

/**
 * Data transfer object containing information about a repair task.
 */
public final class RepairTaskDTO {
    private final String description;
    private final boolean complete;

    /**
     * Creates a new repair task DTO.
     * @param description The task description.
     * @param complete Whether the task is done.
     */
    public RepairTaskDTO(String description, boolean complete) {
        this.description = description;
        this.complete = complete;
    }

    /**
     * Gets the description of this task.
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if the repair task has been finished.
     * @return true if the task is done, otherwise false.
     */
    public boolean isComplete() {
        return complete;
    }
}
