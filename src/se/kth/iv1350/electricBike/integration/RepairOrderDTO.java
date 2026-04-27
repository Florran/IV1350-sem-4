package se.kth.iv1350.electricBike.integration;

import java.util.List;

/**
 * Data transfer object containing information about a repair order.
 */
public final class RepairOrderDTO {
    private final String id;
    private final String state;
    private final String problemDescr;
    private final String customerPhone;
    private final String bikeSerialNo;
    private final String date;
    private final String estimatedCompletionDate;
    private final List<String> diagnosticResults;
    private final List<RepairTaskDTO> repairTasks;

    /**
     * Creates a new repair order DTO.
     * @param id The unique id of the repair order.
     * @param state The current state of the repair order.
     * @param problemDescr The problem description registered for the repair order.
     * @param customerPhone The phone number of the customer who owns the bike.
     * @param bikeSerialNo The serial number of the bike.
     * @param date The date and time when the repair order was created.
     * @param estimatedCompletionDate The estimated date and time when the repair will be done.
     * @param diagnosticResults The diagnostic findings recorded for the order.
     * @param repairTasks The repair tasks attached to the order, as DTOs.
     */
    public RepairOrderDTO(String id, String state, String problemDescr,
                          String customerPhone, String bikeSerialNo,
                          String date, String estimatedCompletionDate,
                          List<String> diagnosticResults,
                          List<RepairTaskDTO> repairTasks) {
        this.id = id;
        this.state = state;
        this.problemDescr = problemDescr;
        this.customerPhone = customerPhone;
        this.bikeSerialNo = bikeSerialNo;
        this.date = date;
        this.estimatedCompletionDate = estimatedCompletionDate;
        this.diagnosticResults = List.copyOf(diagnosticResults);
        this.repairTasks = List.copyOf(repairTasks);
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

    /**
     * Gets the phone number of the customer who owns the bike.
     * @return The customer's phone number.
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Gets the serial number of the bike being repaired.
     * @return The bike's serial number.
     */
    public String getBikeSerialNo() {
        return bikeSerialNo;
    }

    /**
     * Gets the date and time when the repair order was created.
     * @return The creation date and time.
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the estimated date and time when the repair will be completed.
     * @return The estimated completion date and time.
     */
    public String getEstimatedCompletionDate() {
        return estimatedCompletionDate;
    }

    /**
     * Gets the diagnostic findings recorded for this order.
     * @return A list of diagnostic results.
     */
    public List<String> getDiagnosticResults() {
        return diagnosticResults;
    }

    /**
     * Gets the repair tasks attached to this order.
     * @return A list of repair task DTOs.
     */
    public List<RepairTaskDTO> getRepairTasks() {
        return repairTasks;
    }
}
