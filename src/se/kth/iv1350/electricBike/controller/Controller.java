package se.kth.iv1350.electricBike.controller;

import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.electricBike.integration.*;
import se.kth.iv1350.electricBike.model.RepairOrder;
import se.kth.iv1350.electricBike.model.RepairOrderObserver;
import se.kth.iv1350.electricBike.model.discount.DiscountStrategy;

/**
 * Coordinates calls between the view, model, and integration layers.
 */
public class Controller {
    private CustomerRegistry customerReg;
    private RepairOrderRegistry repairOrderReg;
    private Printer printer;

    private List<RepairOrderObserver> repairOrderObservers = new ArrayList<>();

    /**
     * Creates a new instance. The repair order registry is obtained
     * through the singleton accessor
     * {@link RepairOrderRegistry#getInstance()}, since only one repair
     * order registry is allowed to exist.
     *
     * @param customerReg The customer registry.
     * @param printer     The printer.
     */
    public Controller(CustomerRegistry customerReg, Printer printer) {
        this.customerReg = customerReg;
        this.repairOrderReg = RepairOrderRegistry.getInstance();
        this.printer = printer;
    }

    /**
     * Adds an observer that will be notified when any repair order is updated.
     *
     * @param obs The observer to add.
     */
    public void addRepairOrderObserver(RepairOrderObserver obs) {
        this.repairOrderObservers.add(obs);
    }

    private void attachObservers(RepairOrder repairOrder) {
        for (RepairOrderObserver obs : this.repairOrderObservers) {
            repairOrder.addObserver(obs);
        }
    }

    /**
     * Searches for a customer by phone number.
     * * @param phoneNumber The phone number to search for.
     * 
     * @return The found customer, or null.
     * @throws CustomerNotFoundException if no customer matches the given phone
     *                                   number.
     */
    public CustomerDTO findCustomer(String phoneNumber) throws CustomerNotFoundException {
        return customerReg.findCustomer(phoneNumber);
    }

    /**
     * Creates a new repair order and saves it to the registry as a DTO.
     * * @param problemDescr Description of the problem.
     * 
     * @param customerPhone Customer's phone number.
     * @param bikeSerialNo  Bike's serial number.
     */
    public void createRepairOrder(String problemDescr, String customerPhone, String bikeSerialNo) {
        RepairOrder newOrder = new RepairOrder(problemDescr, customerPhone, bikeSerialNo);
        repairOrderReg.createRepairOrder(newOrder.createDTO());
    }

    /**
     * Returns all repair orders.
     * * @return A list of all repair order DTOs.
     */
    public List<RepairOrderDTO> findAllRepairOrders() {
        return repairOrderReg.findAllRepairOrders();
    }

    /**
     * Finds a repair order by its ID.
     * * @param orderId The order ID.
     * 
     * @return The found DTO, or null.
     */
    public RepairOrderDTO findRepairOrderById(String orderId) {
        return repairOrderReg.findRepairOrderById(orderId);
    }

    /**
     * Finds a repair order by customer phone number.
     * * @param phoneNumber The phone number.
     * 
     * @return The found DTO, or null.
     */
    public RepairOrderDTO findRepairOrderByNumber(String phoneNumber) {
        return repairOrderReg.findRepairOrderByNumber(phoneNumber);
    }

    /**
     * Adds a diagnostic result to an order.
     * * @param repairOrderId The order ID.
     * 
     * @param diagTaskResult The result to add.
     */
    public void addDiagnosticResult(String repairOrderId, String diagTaskResult) {
        RepairOrderDTO dto = repairOrderReg.findRepairOrderById(repairOrderId);
        RepairOrder repairOrder = new RepairOrder(dto);
        attachObservers(repairOrder);
        repairOrder.addDiagnosticResult(diagTaskResult);
        repairOrderReg.updateRepairOrder(repairOrder.createDTO());
    }

    /**
     * Adds a repair task with a specific cost to an order.
     * * @param repairOrderId The order ID.
     * 
     * @param repairTaskDesc The task description.
     * @param cost           The cost of the task.
     */
    public void addRepairTask(String repairOrderId, String repairTaskDesc, double cost) {
        RepairOrderDTO dto = repairOrderReg.findRepairOrderById(repairOrderId);
        RepairOrder repairOrder = new RepairOrder(dto);
        attachObservers(repairOrder);
        repairOrder.addRepairTask(repairTaskDesc, cost);
        repairOrderReg.updateRepairOrder(repairOrder.createDTO());
    }

    /**
     * Calculates the total cost of an order applying a discount strategy.
     * * @param repairOrderId The order ID.
     * 
     * @param discountStrategy The strategy to use.
     * @return The final cost.
     */
    public double calculateTotalCost(String repairOrderId, DiscountStrategy discountStrategy) {
        RepairOrderDTO dto = repairOrderReg.findRepairOrderById(repairOrderId);
        return discountStrategy.applyDiscount(dto.getTotalCost());
    }

    /**
     * Accepts the order, applies the final discount, and prints the receipt.
     * * @param repairOrderId The order ID.
     * 
     * @param discountStrategy The strategy to apply to the final order.
     */
    public void acceptRepairOrder(String repairOrderId, DiscountStrategy discountStrategy) {
        RepairOrderDTO dto = repairOrderReg.findRepairOrderById(repairOrderId);
        RepairOrder repairOrder = new RepairOrder(dto);
        attachObservers(repairOrder);
        repairOrder.acceptRepairOrder(discountStrategy);
        RepairOrderDTO repairOrderToPrint = repairOrder.createDTO();
        repairOrderReg.updateRepairOrder(repairOrderToPrint);
        printer.printRepairOrder(repairOrderToPrint);
    }
}