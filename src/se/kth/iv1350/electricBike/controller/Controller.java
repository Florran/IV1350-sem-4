package se.kth.iv1350.electricBike.controller;

import java.lang.String;
import se.kth.iv1350.electricBike.integration.*;
import se.kth.iv1350.electricBike.model.RepairOrder;
import java.util.List;
import java.util.ArrayList;

public class Controller {

    private CustomerRegistry customerReg;
    private RepairOrderRegistry repairOrderReg;

    /**
     * The controller constructor
     * 
     * @param customerReg    CustomerRegistry reference so controller can make calls
     *                       to CustomerRegistry
     * @param repairOrderReg RepairOrderRegistry reference to save repair orders
     */
    public Controller(CustomerRegistry customerReg, RepairOrderRegistry repairOrderReg) {
        this.customerReg = customerReg;
        this.repairOrderReg = repairOrderReg;
    }

    /**
     * Tries to find a customer based on a specified phone number
     * 
     * @param phoneNumber The phone number used to search for the customer
     * @return Returns the found customer
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
        repairOrderReg.saveRepairOrder(newOrder);
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
     * Finds the history of orders for a specific customer.
     * 
     * @param phoneNumber The phone number to search for
     * @return A list of RepairOrderDTOs belonging to the customer
     */
    public List<RepairOrderDTO> findRepairOrderHistory(String phoneNumber) {
        List<RepairOrder> orders = repairOrderReg.findRepairOrdersByPhone(phoneNumber);
        List<RepairOrderDTO> dtos = new ArrayList<>();

        for (RepairOrder order : orders) {
            dtos.add(order.createDTO());
        }
        return dtos;
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

    /**
     * Marks a specific repair order as rejected by the customer.
     *
     * @param repairOrderId The unique identifier of the repair order.
     */
    public void rejectRepairOrder(String repairOrderId) {
        RepairOrder repairOrder = repairOrderReg.findRepairOrderById(repairOrderId);
        repairOrder.rejectRepairOrder();
    }
}