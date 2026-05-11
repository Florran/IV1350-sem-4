package se.kth.iv1350.electricBike.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import se.kth.iv1350.electricBike.integration.RepairOrderDTO;
import se.kth.iv1350.electricBike.integration.RepairTaskDTO;
import se.kth.iv1350.electricBike.model.discount.DiscountStrategy;

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
    private double appliedDiscount;
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
        this.appliedDiscount = 0.0;
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
        this.appliedDiscount = dto.getAppliedDiscount();
        this.diagnosticReport = new DiagnosticReport(dto.getDiagnosticResults());
        this.repairTasks = new ArrayList<>();
        for (RepairTaskDTO taskDto : dto.getRepairTasks()) {
            this.repairTasks.add(new RepairTask(taskDto.getDescription(), taskDto.getCost(), taskDto.isComplete()));
        }
    }

    public void addObserver(RepairOrderObserver obs) {
        observers.add(obs);
    }

    private void notifyObservers() {
        RepairOrderDTO dto = createDTO();
        for (RepairOrderObserver obs : observers) {
            obs.repairOrderUpdated(dto);
        }
    }

    public double getTotalCost() {
        double total = 0;
        for (RepairTask task : repairTasks) {
            total += task.getCost();
        }
        return total;
    }

    public String getId() {
        return id;
    }

    public String getProblemDescr() {
        return problemDescr;
    }

    public String getState() {
        return state;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getBikeSerialNo() {
        return bikeSerialNo;
    }

    public LocalDateTime getDate() {
        return date;
    }

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
                appliedDiscount,
                diagnosticReport.getResults(),
                taskDTOs);
    }

    public void addDiagnosticResult(String result) {
        this.diagnosticReport.addResult(result);
        notifyObservers();
    }

    public void addRepairTask(String description, double cost) {
        this.repairTasks.add(new RepairTask(description, cost));
        notifyObservers();
    }

    /**
     * Updates the state of this repair order and locks in the applied discount.
     * * @param discountStrategy The strategy used to calculate the final discount.
     */
    public void acceptRepairOrder(DiscountStrategy discountStrategy) {
        this.state = "Accepted";
        double baseCost = getTotalCost();
        this.appliedDiscount = baseCost - discountStrategy.applyDiscount(baseCost);
        notifyObservers();
    }

    public DiagnosticReport getDiagnosticReport() {
        return this.diagnosticReport;
    }

    public List<RepairTask> getRepairTasks() {
        return new ArrayList<>(this.repairTasks);
    }
}