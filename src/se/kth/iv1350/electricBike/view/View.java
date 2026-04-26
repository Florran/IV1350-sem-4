package se.kth.iv1350.electricBike.view;

import se.kth.iv1350.electricBike.controller.Controller;
import se.kth.iv1350.electricBike.integration.*;

/**
 * Represents the view layer used to run the application flow.
 */
public class View {

    private Controller contr;

    /**
     * The view constructor
     * @param contr Controller reference so view can make calls to controller
     */
    public View(Controller contr) {
        this.contr = contr;
    }

    /**
     * Runs a hardcoded execution of the basic repair order flow.
     */
    public void fakeExecution() {
        CustomerDTO foundCustomer = contr.findCustomer("0705556767");
        System.out.print(foundCustomer);

        String bikeSerial = foundCustomer.getBikeSerialNo();
        String customerPhone = foundCustomer.getPhoneNumber();
        String problemDescription = "Motor stängs av i uppförsbacke";

        System.out.println("\nReceptionist skriver in felet för cykel " + bikeSerial + ": " + problemDescription);

        contr.createRepairOrder(problemDescription, customerPhone, bikeSerial);

        System.out.println("Systemet har skapat reparationsorder");

        System.out.println("\nTekniker hämtar alla reparationsordrar...");
        java.util.List<RepairOrderDTO> repairOrders = contr.findAllRepairOrders();

        RepairOrderDTO foundOrder = repairOrders.get(0);

        System.out.println("Systemet visar orderdetaljer från DTO:");
        System.out.println(" - Order-ID: " + foundOrder.getId());
        System.out.println(" - Status: " + foundOrder.getState());
        System.out.println(" - Beskrivning: " + foundOrder.getProblemDescr());


    }
}
