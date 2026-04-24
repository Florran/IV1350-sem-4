package se.kth.iv1350.electricBike.startup;

import se.kth.iv1350.electricBike.controller.Controller;
import se.kth.iv1350.electricBike.integration.CustomerRegistry;
import se.kth.iv1350.electricBike.integration.RepairOrderRegistry;
import se.kth.iv1350.electricBike.view.View;

/**
 * Starts the electric bike repair application.
 */
public class Main {
    /**
     * Creates the application objects and starts the fake execution.
     * @param args Command line arguments, not used.
     */
    public static void main(String[] args) {
        CustomerRegistry customerRegistry = new CustomerRegistry();
        RepairOrderRegistry repairOrderRegistry = new RepairOrderRegistry();

        Controller contr = new Controller(customerRegistry, repairOrderRegistry);
        View view = new View(contr);

        view.fakeExecution();
    }
}
