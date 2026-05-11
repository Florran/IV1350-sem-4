package se.kth.iv1350.electricBike.integration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * A Data Transfer Object representing the state of a repair order.
 * This object is immutable and carries data between layers.
 */
public class RepairOrderDTO {
    private final String id;
    private final String state;
    private final String problemDescr;
    private final String customerPhone;
    private final String bikeSerialNo;
    private final String date;
    private final String estimatedCompletionDate;
    private final double totalCost;
    private final double appliedDiscount;
    private final List<String> diagnosticResults;
    private final List<RepairTaskDTO> repairTasks;

    /**
     * Creates a new instance representing a specific repair order state.
     *
     * @param id                      The unique identifier of the order.
     * @param state                   The current status of the order.
     * @param problemDescr            The customer's description of the problem.
     * @param customerPhone           The customer's contact number.
     * @param bikeSerialNo            The serial number of the bike.
     * @param date                    The creation date of the order.
     * @param estimatedCompletionDate The estimated completion date.
     * @param totalCost               The total cost of all repair tasks before
     *                                discount.
     * @param appliedDiscount         The amount of discount applied to the order.
     * @param diagnosticResults       A list of diagnostic findings.
     * @param repairTasks             A list of proposed or completed repair tasks.
     */
    public RepairOrderDTO(String id, String state, String problemDescr, String customerPhone,
            String bikeSerialNo, String date, String estimatedCompletionDate,
            double totalCost, double appliedDiscount, List<String> diagnosticResults,
            List<RepairTaskDTO> repairTasks) {
        this.id = id;
        this.state = state;
        this.problemDescr = problemDescr;
        this.customerPhone = customerPhone;
        this.bikeSerialNo = bikeSerialNo;
        this.date = date;
        this.estimatedCompletionDate = estimatedCompletionDate;
        this.totalCost = totalCost;
        this.appliedDiscount = appliedDiscount;
        this.diagnosticResults = new ArrayList<>(diagnosticResults);
        this.repairTasks = new ArrayList<>(repairTasks);
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getProblemDescr() {
        return problemDescr;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getBikeSerialNo() {
        return bikeSerialNo;
    }

    public String getDate() {
        return date;
    }

    public String getEstimatedCompletionDate() {
        return estimatedCompletionDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getAppliedDiscount() {
        return appliedDiscount;
    }

    public List<String> getDiagnosticResults() {
        return new ArrayList<>(diagnosticResults);
    }

    public List<RepairTaskDTO> getRepairTasks() {
        return new ArrayList<>(repairTasks);
    }

    /**
     * Returns a string representation of the repair order, formatted as a readable
     * receipt.
     *
     * @return A formatted string detailing the order information, tasks, and costs.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = LocalDateTime.parse(date).format(formatter);
        String formattedEstDate = LocalDateTime.parse(estimatedCompletionDate).format(formatter);

        StringBuilder builder = new StringBuilder();
        builder.append("---------------------------------------\n");
        builder.append("REPAIR ORDER: ").append(id).append("\n");
        builder.append("Date: ").append(formattedDate).append("\n");
        builder.append("Estimated completion: ").append(formattedEstDate).append("\n");
        builder.append("Status: ").append(state).append("\n");
        builder.append("Customer Phone: ").append(customerPhone).append("\n");
        builder.append("Bike Serial: ").append(bikeSerialNo).append("\n");
        builder.append("Problem: ").append(problemDescr).append("\n");

        builder.append("\nDiagnostic results: ");
        if (diagnosticResults.isEmpty()) {
            builder.append("(none)\n");
        } else {
            for (String result : diagnosticResults) {
                builder.append("\n  - ").append(result);
            }
            builder.append("\n");
        }

        builder.append("\nRepair tasks: ");
        if (repairTasks.isEmpty()) {
            builder.append("(none)\n");
        } else {
            for (RepairTaskDTO task : repairTasks) {
                builder.append("\n  - ").append(task.getDescription())
                        .append(" (").append(task.getCost()).append(" kr)");
            }
            builder.append("\n");
        }

        builder.append("\nSubtotal: ").append(totalCost).append(" kr\n");
        if (appliedDiscount > 0) {
            builder.append("Discount applied: -").append(appliedDiscount).append(" kr\n");
            builder.append("Total to pay: ").append(totalCost - appliedDiscount).append(" kr\n");
        } else {
            builder.append("Total to pay: ").append(totalCost).append(" kr\n");
        }
        builder.append("---------------------------------------\n");

        return builder.toString();
    }
}