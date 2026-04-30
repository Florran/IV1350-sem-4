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
     *
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

        System.out.println("\nTekniker söker fram ordern via telefonnummer...");

        RepairOrderDTO foundOrder = contr.findRepairOrderByNumber(customerPhone);
        String generatedOrderId = foundOrder.getId();

        System.out.println("Systemet visar orderdetaljer från DTO:");
        System.out.println(" - Order-ID: " + foundOrder.getId());
        System.out.println(" - Status: " + foundOrder.getState());
        System.out.println(" - Beskrivning: " + foundOrder.getProblemDescr());

        System.out.println("\n--- Tekniker inspekterar cykeln ---");
        System.out.println("Tekniker anger fel och föreslår reparationer...");

        contr.addDiagnosticResult(generatedOrderId, "Kabelglapp vid motorns anslutning");
        contr.addDiagnosticResult(generatedOrderId, "Slitet batterifäste");

        contr.addRepairTask(generatedOrderId, "Byt ut och löd om motorkabel");
        contr.addRepairTask(generatedOrderId, "Montera nytt batterifäste");

        System.out.println("Diagnostik och reparationsuppgifter har sparats i ordern.");

        System.out.println("\n--- Presenterar för kund ---");
        System.out.println("Visar order för kund: " + foundOrder.getProblemDescr());
        System.out.println("Systemet beräknar priset (simulerat)...");

        System.out.println("\n--- Kundens svar ---");
        System.out.println("Kunden accepterar reparationen.");

        contr.acceptRepairOrder(generatedOrderId);

        RepairOrderDTO updatedOrder = contr.findRepairOrderById(generatedOrderId);
        System.out.println("Orderstatus är nu uppdaterad till: " + updatedOrder.getState());
    }
}