package se.kth.iv1350.electricBike.integration;

/**
 * Data transfer object containing information about a repair order.
 */
public class RepairOrderDTO {
    private String id;
    private String state;
    private String problemDescr;

    /**
     * Creates a new repair order DTO.
     * @param id The unique id of the repair order.
     * @param state The current state of the repair order.
     * @param problemDescr The problem description registered for the repair order.
     */
    public RepairOrderDTO(String id, String state, String problemDescr) {
        this.id = id;
        this.state = state;
        this.problemDescr = problemDescr;
    }

    /**
     * Gets the unique id of the repair order.
     * @return The repair order id.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the current state of the repair order.
     * @return The repair order state.
     */
    public String getState() {
        return state;
    }

    /**
     * Gets the problem description registered for the repair order.
     * @return The problem description.
     */
    public String getProblemDescr() {
        return problemDescr;
    }
}
