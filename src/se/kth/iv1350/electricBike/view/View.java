package se.kth.iv1350.electricBike.view;

import java.io.IOException;
import java.util.List;
import se.kth.iv1350.electricBike.controller.Controller;
import se.kth.iv1350.electricBike.integration.CustomerDTO;
import se.kth.iv1350.electricBike.integration.CustomerNotFoundException;
import se.kth.iv1350.electricBike.integration.RepairOrderDTO;
import se.kth.iv1350.electricBike.model.discount.DiscountStrategy;
import se.kth.iv1350.electricBike.model.discount.LoyaltyDiscount;

/**
 * Represents the view layer used to run the application flow.
 */
public class View {

    private Controller contr;

    /**
     * The view constructor.
     *
     * @param contr Controller reference so view can make calls to controller.
     */
    public View(Controller contr) throws IOException {
        this.contr = contr;
        contr.addRepairOrderObserver(new RepairOrderView());
        contr.addRepairOrderObserver(new RepairOrderLogger());
    }

    /**
     * Runs a hardcoded execution of the basic repair order flow.
     */
    public void fakeExecution() {
        System.out.println("--- Sample run: Repair Electric Bike, basic flow ---\n");

        String customerPhone = "0705556767";
        String problemDescription = "Motor stängs av i uppförsbacke";

        System.out.println("--- Kunden lämnar in cykeln ---");
        System.out.println("Receptionist letar upp kund med telefonnummer " + customerPhone + "...");

        CustomerDTO foundCustomer;
        try {
            foundCustomer = contr.findCustomer(customerPhone);
        } catch (CustomerNotFoundException exc) {
            System.out.println("Ingen kund hittades med telefonnummer " + exc.getNumber()
                    + ". Kontrollera numret och försök igen!");
            return;
        }

        System.out.println("Kund hittades:");
        System.out.println(foundCustomer);

        String bikeSerial = foundCustomer.getBikeSerialNo();
        System.out.println("\nReceptionist skriver in felet för cykel " + bikeSerial + ": " + problemDescription);
        contr.createRepairOrder(problemDescription, customerPhone, bikeSerial);
        System.out.println("Systemet har skapat reparationsorder.");

        System.out.println("\n--- Tekniker börjar arbeta ---");
        System.out.println("Tekniker hämtar alla pågående reparationsordrar...");
        List<RepairOrderDTO> allOrders = contr.findAllRepairOrders();
        System.out.println("Antal ordrar i systemet: " + allOrders.size());

        for (RepairOrderDTO order : allOrders) {
            System.out.println(order);
        }

        RepairOrderDTO orderToWorkOn = allOrders.get(0);
        String generatedOrderId = orderToWorkOn.getId();

        System.out.println("\nTekniker väljer order " + generatedOrderId + " och inspekterar cykeln...");
        contr.addDiagnosticResult(generatedOrderId, "Kabelglapp vid motorns anslutning");
        contr.addDiagnosticResult(generatedOrderId, "Slitet batterifäste");
        System.out.println("Diagnostiska resultat har sparats i ordern.");

        System.out.println("\nTekniker föreslår reparationer...");
        contr.addRepairTask(generatedOrderId, "Byt ut och löd om motorkabel", 450.0);
        contr.addRepairTask(generatedOrderId, "Montera nytt batterifäste", 200.0);
        System.out.println("Reparationsuppgifter har sparats i ordern.");

        System.out.println("\n--- Kunden kommer tillbaka för att hämta cykeln ---");
        System.out.println("Receptionist söker fram ordern via telefonnummer " + customerPhone + "...");
        RepairOrderDTO foundOrder = contr.findRepairOrderByNumber(customerPhone);

        if (foundOrder != null) {
            System.out.println("Hittad order:");
            System.out.println(foundOrder);

            System.out.println("\nApplicerar rabatt (LoyaltyDiscount)...");
            DiscountStrategy loyaltyDiscount = new LoyaltyDiscount();
            double finalPrice = contr.calculateTotalCost(generatedOrderId, loyaltyDiscount);
            System.out.println("Totalkostnad efter rabatt: " + finalPrice + " kr");

            System.out.println("\nKunden accepterar reparationen.");
            System.out.println("\n--- Skrivare skriver ut kvitto ---");
            contr.acceptRepairOrder(generatedOrderId);
        }

        System.out.println("\n--- Demonstration av felhantering (sökning på okänt ID) ---");
        String fakeId = "finns-inte-id";
        RepairOrderDTO missingOrder = contr.findRepairOrderById(fakeId);
        if (missingOrder == null) {
            System.out.println("[ANVÄNDARMEDDELANDE]: Operationen misslyckades då ordern med ID " + fakeId
                    + " inte kunde hittas.");
        }
    }
}