package se.kth.iv1350.electricBike.model;

import java.time.LocalDateTime;
import java.util.UUID;
import se.kth.iv1350.electricBike.integration.RepairOrderDTO;

/**
 * Represents a repair order in the model layer.
 */
public class RepairOrder {
    private String id;
    private String problemDescr;
    private String customerPhone;
    private String bikeSerialNo;
    private LocalDateTime date;
    private String state;
    private String diagnosticReport;

    /**
     * Creates a new repair order for a customer and bike.
     * @param problemDescr The problem description registered for the repair order.
     * @param customerPhone The customer's phone number.
     * @param bikeSerialNo The serial number of the bike.
     */
    public RepairOrder(String problemDescr, String customerPhone, String bikeSerialNo) {
        this.id = UUID.randomUUID().toString().substring(0, 5);
        this.problemDescr = problemDescr;
        this.customerPhone = customerPhone;
        this.bikeSerialNo = bikeSerialNo;
        this.date = LocalDateTime.now();
        this.state = "Newly created";
    }

    /**
     * Gets the unique id of the repair order.
     * @return The repair order id.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the problem description registered for the repair order.
     * @return The problem description.
     */
    public String getProblemDescr() {
        return problemDescr;
    }

    /**
     * Gets the current state of the repair order.
     * @return The repair order state.
     */
    public String getState() {
        return state;
    }

    /**
     * Creates a DTO containing repair order information for other layers.
     * @return A DTO representing this repair order.
     */
    public RepairOrderDTO createDTO() {
        return new RepairOrderDTO(id, state, problemDescr);
    }
}
