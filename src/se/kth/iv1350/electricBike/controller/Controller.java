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
     * @param phoneNumber The phone number used to search for the customer
     * @return Returns the found customer, or null if no matching customer exists
     */
    public CustomerDTO findCustomer(String phoneNumber) {
        CustomerDTO foundCustomer = customerReg.findCustomer(phoneNumber);
        return foundCustomer;
    }

    /**
     * Creates a new repair order and registers it in the system.
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

        for(RepairOrder order : orders) {
            dtos.add(order.createDTO());
        }
        return dtos;
    }
}
