package se.kth.iv1350.electricBike.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import se.kth.iv1350.electricBike.integration.RepairOrderDTO;
import se.kth.iv1350.electricBike.integration.RepairTaskDTO;

/**
 * Represents a repair order for a bike, containing customer details,
 * bike details, and the current status of the repair process.
 */
public class RepairOrder {
    private List<RepairOrderObserver> observers = new ArrayList<>();

    private String id;
    private String problemDescr;
    private String customerPhone;
    private String bikeSerialNo;
    private LocalDateTime date;
    private LocalDateTime estimatedCompletionDate;
    private String state;
    private DiagnosticReport diagnosticReport;
    private List<RepairTask> repairTasks;

    /**
     * Creates a new repair order with the provided details.
     *
     * @param problemDescr  A description of the problem provided by the customer.
     * @param customerPhone The customer's phone number.
     * @param bikeSerialNo  The serial number of the bike.
     */
    public RepairOrder(String problemDescr, String customerPhone, String bikeSerialNo) {
        this.id = UUID.randomUUID().toString().substring(0, 5);
        this.problemDescr = problemDescr;
        this.customerPhone = customerPhone;
        this.bikeSerialNo = bikeSerialNo;
        this.date = LocalDateTime.now();
        this.estimatedCompletionDate = this.date.plusWeeks(1);
        this.state = "Newly created";
        this.diagnosticReport = new DiagnosticReport();
        this.repairTasks = new ArrayList<>();
    }

    /**
     * Creates a repair order from a DTO.
     *
     * @param dto The DTO to create the order from.
     */
    public RepairOrder(RepairOrderDTO dto) {
        this.id = dto.getId();
        this.problemDescr = dto.getProblemDescr();
        this.customerPhone = dto.getCustomerPhone();
        this.bikeSerialNo = dto.getBikeSerialNo();
        this.date = LocalDateTime.parse(dto.getDate());
        this.estimatedCompletionDate = LocalDateTime.parse(dto.getEstimatedCompletionDate());
        this.state = dto.getState();
        this.diagnosticReport = new DiagnosticReport(dto.getDiagnosticResults());
        this.repairTasks = new ArrayList<>();
        for (RepairTaskDTO taskDto : dto.getRepairTasks()) {
            this.repairTasks.add(new RepairTask(taskDto.getDescription(), taskDto.getCost(), taskDto.isComplete()));
        }
    }

    /**
     * Adds an observer to be notified when the repair order is updated.
     * * @param obs The observer to add.
     */
    public void addObserver(RepairOrderObserver obs) {
        observers.add(obs);
    }

    /**
     * Notifies all registered observers that the order has been updated.
     */
    private void notifyObservers() {
        RepairOrderDTO dto = createDTO();
        for (RepairOrderObserver obs : observers) {
            obs.repairOrderUpdated(dto);
        }
    }

    /**
     * Calculates the total cost of all repair tasks in this order.
     *
     * @return The total cost.
     */
    public double getTotalCost() {
        double total = 0;
        for (RepairTask task : repairTasks) {
            total += task.getCost();
        }
        return total;
    }

    /**
     * Gets the unique identifier for this repair order.
     *
     * @return The repair order ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the customer's description of the problem.
     *
     * @return The problem description.
     */
    public String getProblemDescr() {
        return problemDescr;
    }

    /**
     * Gets the current state of the repair order.
     *
     * @return The state of the order.
     */
    public String getState() {
        return state;
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
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Gets the estimated completion date and time for this repair.
     *
     * @return The estimated completion date and time.
     */
    public LocalDateTime getEstimatedCompletionDate() {
        return estimatedCompletionDate;
    }

    /**
     * Creates a Data Transfer Object representing this repair order.
     *
     * @return A new instance of RepairOrderDTO containing order details.
     */
    public RepairOrderDTO createDTO() {
        List<RepairTaskDTO> taskDTOs = new ArrayList<>();

        for (RepairTask task : repairTasks) {
            taskDTOs.add(task.createDTO());
        }

        return new RepairOrderDTO(
                id,
                state,
                problemDescr,
                customerPhone,
                bikeSerialNo,
                date.toString(),
                estimatedCompletionDate.toString(),
                getTotalCost(),
                diagnosticReport.getResults(),
                taskDTOs);
    }

    /**
     * Adds a new finding to this repair order's diagnostic report.
     *
     * @param result A description of the diagnostic finding.
     */
    public void addDiagnosticResult(String result) {
        this.diagnosticReport.addResult(result);
        notifyObservers();
    }

    /**
     * Adds a proposed repair task to this repair order, including its cost.
     *
     * @param description A description of the work that needs to be done.
     * @param cost        The cost of this specific repair task.
     */
    public void addRepairTask(String description, double cost) {
        this.repairTasks.add(new RepairTask(description, cost));
        notifyObservers();
    }

    /**
     * Updates the state of this repair order to indicate that the customer has
     * accepted the proposed repairs.
     */
    public void acceptRepairOrder() {
        this.state = "Accepted";
        notifyObservers();
    }

    /**
     * Gets the diagnostic report for this repair order.
     *
     * @return The diagnostic report.
     */
    public DiagnosticReport getDiagnosticReport() {
        return this.diagnosticReport;
    }

    /**
     * Gets the proposed repair tasks for this repair order.
     *
     * @return A list of the proposed repair tasks.
     */
    public List<RepairTask> getRepairTasks() {
        return new ArrayList<>(this.repairTasks);
    }
}