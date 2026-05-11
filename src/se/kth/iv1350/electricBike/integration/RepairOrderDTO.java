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
    private final double totalCost;
    private final List<String> diagnosticResults;
    private final List<RepairTaskDTO> repairTasks;

    /**
     * Creates a new repair order DTO.
     * 
     * @param id                      The unique id of the repair order.
     * @param state                   The current state of the repair order.
     * @param problemDescr            The problem description registered for the
     *                                repair order.
     * @param customerPhone           The phone number of the customer who owns the
     *                                bike.
     * @param bikeSerialNo            The serial number of the bike.
     * @param date                    The date and time when the repair order was
     *                                created.
     * @param estimatedCompletionDate The estimated date and time when the repair
     *                                will be done.
     * @param totalCost               The total cost of all repair tasks.
     * @param diagnosticResults       The diagnostic findings recorded for the
     *                                order.
     * @param repairTasks             The repair tasks attached to the order, as
     *                                DTOs.
     */
    public RepairOrderDTO(String id, String state, String problemDescr,
            String customerPhone, String bikeSerialNo,
            String date, String estimatedCompletionDate,
            double totalCost,
            List<String> diagnosticResults,
            List<RepairTaskDTO> repairTasks) {
        this.id = id;
        this.state = state;
        this.problemDescr = problemDescr;
        this.customerPhone = customerPhone;
        this.bikeSerialNo = bikeSerialNo;
        this.date = date;
        this.estimatedCompletionDate = estimatedCompletionDate;
        this.totalCost = totalCost;
        this.diagnosticResults = List.copyOf(diagnosticResults);
        this.repairTasks = List.copyOf(repairTasks);
    }

    /**
     * Gets the unique id of the repair order.
     * 
     * @return The repair order id.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the current state of the repair order.
     * 
     * @return The repair order state.
     */
    public String getState() {
        return state;
    }

    /**
     * Gets the problem description registered for the repair order.
     * 
     * @return The problem description.
     */
    public String getProblemDescr() {
        return problemDescr;
    }

    /**
     * Gets the phone number of the customer who owns the bike.
     * 
     * @return The customer's phone number.
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Gets the serial number of the bike being repaired.
     * 
     * @return The bike's serial number.
     */
    public String getBikeSerialNo() {
        return bikeSerialNo;
    }

    /**
     * Gets the date and time when the repair order was created.
     * 
     * @return The creation date and time.
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the estimated date and time when the repair will be completed.
     * 
     * @return The estimated completion date and time.
     */
    public String getEstimatedCompletionDate() {
        return estimatedCompletionDate;
    }

    /**
     * Gets the total calculated cost of the repair order.
     * 
     * @return The total cost.
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Gets the diagnostic findings recorded for this order.
     * 
     * @return A list of diagnostic results.
     */
    public List<String> getDiagnosticResults() {
        return diagnosticResults;
    }

    /**
     * Gets the repair tasks attached to this order.
     * 
     * @return A list of repair task DTOs.
     */
    public List<RepairTaskDTO> getRepairTasks() {
        return repairTasks;
    }

    /**
     * Returns a human-readable string with all fields of this DTO,
     * including diagnostic results, repair tasks, and total cost.
     * 
     * @return A multi-line string containing every attribute.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order id:               ").append(id).append("\n");
        sb.append("State:                  ").append(state).append("\n");
        sb.append("Customer phone:         ").append(customerPhone).append("\n");
        sb.append("Bike serial number:     ").append(bikeSerialNo).append("\n");
        sb.append("Problem description:    ").append(problemDescr).append("\n");
        sb.append("Created:                ").append(date).append("\n");
        sb.append("Estimated completion:   ").append(estimatedCompletionDate).append("\n");
        sb.append("Diagnostic results:");
        if (diagnosticResults.isEmpty()) {
            sb.append(" (none)");
        } else {
            for (String result : diagnosticResults) {
                sb.append("\n  - ").append(result);
            }
        }
        sb.append("\n").append("Repair tasks:");
        if (repairTasks.isEmpty()) {
            sb.append(" (none)");
        } else {
            for (RepairTaskDTO task : repairTasks) {
                sb.append("\n  - ").append(task);
            }
        }
        sb.append("\nTotal cost:             ").append(totalCost).append(" kr\n");
        return sb.toString();
    }
}