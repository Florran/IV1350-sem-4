package se.kth.iv1350.electricBike.startup;

import se.kth.iv1350.electricBike.controller.Controller;
import se.kth.iv1350.electricBike.integration.CustomerRegistry;
import se.kth.iv1350.electricBike.integration.Printer;
import se.kth.iv1350.electricBike.view.View;

/**
 * Starts the electric bike repair application.
 */
public class Main {
    /**
     * Creates the application objects and starts the fake execution.
     * The repair order registry is not instantiated here, since it is
     * a singleton owned by the integration layer.
     *
     * @param args Command line arguments, not used.
     */
    public static void main(String[] args) {
        CustomerRegistry customerRegistry = new CustomerRegistry();
        Printer printer = new Printer();

        Controller contr = new Controller(customerRegistry, printer);
        View view = new View(contr);

        view.fakeExecution();
    }
}
