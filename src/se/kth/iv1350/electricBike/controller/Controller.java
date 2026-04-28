package se.kth.iv1350.electricBike.controller;

import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.electricBike.integration.*;
import se.kth.iv1350.electricBike.model.RepairOrder;

/**
 * Coordinates calls between the view, model, and integration layers.
 */
public class Controller {

    private CustomerRegistry customerReg;
    private RepairOrderRegistry repairOrderReg;

    /**
     * The controller constructor
     * @param customerReg CustomerRegistry reference so controller can make calls to CustomerRegistry
     * @param repairOrderReg RepairOrderRegistry reference used to store repair orders
     */
    public Controller(CustomerRegistry customerReg, RepairOrderRegistry repairOrderReg) {
        this.customerReg = customerReg;
        this.repairOrderReg = repairOrderReg;
    }

    /**
     * Tries to find a customer based on a specified phone number
     * 
     * @param phoneNumber The phone number used to search for the customer
     * @return Returns the found customer, or null if no matching customer exists
     */
    public CustomerDTO findCustomer(String phoneNumber) {
        CustomerDTO foundCustomer = customerReg.findCustomer(phoneNumber);
        return foundCustomer;
    }

    /**
     * Creates a new repair order and saves it to the system.
     * 
     * @param problemDescr  The description of the problem
     * @param customerPhone The customer's phone number
     * @param bikeSerialNo  The serial number of the bike
     */
    public void createRepairOrder(String problemDescr, String customerPhone, String bikeSerialNo) {
        RepairOrder newOrder = new RepairOrder(problemDescr, customerPhone, bikeSerialNo);
        repairOrderReg.createRepairOrder(newOrder);
    }

    /**
     * Finds all repair orders in the system.
     * @return A list of DTOs representing all repair orders.
     */
    public List<RepairOrderDTO> findAllRepairOrders() {
        List<RepairOrder> orders = repairOrderReg.findAllRepairOrders();
        List<RepairOrderDTO> dtos = new ArrayList<>();

        for (RepairOrder order : orders) {
            dtos.add(order.createDTO());
        }
        return dtos;
    }

    /**
     * Tries to find a repair order based on its unique ID.
     *
     * @param orderId The unique ID of the order
     * @return The found RepairOrderDTO, or null if not found
     */
    public RepairOrderDTO findRepairOrderById(String orderId) {
        RepairOrder order = repairOrderReg.findRepairOrderById(orderId);
        if (order != null) {
            return order.createDTO();
        }
        return null;
    }

    /**
     * Tries to find an active repair order for a specific customer.
     *
     * @param phoneNumber The phone number to search for
     * @return The found RepairOrderDTO, or null if no order was found
     */
    public RepairOrderDTO findRepairOrder(String phoneNumber) {
        RepairOrder order = repairOrderReg.findRepairOrderByPhone(phoneNumber);

        if (order != null) {
            return order.createDTO();
        }
        return null;
    }

    /**
     * Adds a new finding from the technician's inspection to a specific repair
     * order.
     *
     * @param repairOrderId  The unique identifier of the repair order.
     * @param diagTaskResult The description of the diagnostic finding.
     */
    public void addDiagnosticResult(String repairOrderId, String diagTaskResult) {
        RepairOrder repairOrder = repairOrderReg.findRepairOrderById(repairOrderId);
        repairOrder.addDiagnosticResult(diagTaskResult);
    }

    /**
     * Adds a proposed repair task to a specific repair order.
     *
     * @param repairOrderId  The unique identifier of the repair order.
     * @param repairTaskDesc The description of the repair task to be added.
     */
    public void addRepairTask(String repairOrderId, String repairTaskDesc) {
        RepairOrder repairOrder = repairOrderReg.findRepairOrderById(repairOrderId);
        repairOrder.addRepairTask(repairTaskDesc);
    }

    /**
     * Marks a specific repair order as accepted by the customer.
     *
     * @param repairOrderId The unique identifier of the repair order.
     */
    public void acceptRepairOrder(String repairOrderId) {
        RepairOrder repairOrder = repairOrderReg.findRepairOrderById(repairOrderId);
        repairOrder.acceptRepairOrder();
    }
}