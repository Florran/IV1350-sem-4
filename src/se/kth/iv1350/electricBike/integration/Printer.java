package se.kth.iv1350.electricBike.integration;

/**
 * Simulates a printer that prints repair order receipts for the customer.
 */
public class Printer {

    /**
     * Prints all data of a repair order to standard output.
     * @param repairOrderToPrint The repair order to print.
     */
    public void printRepairOrder(RepairOrderDTO repairOrderToPrint) {
        System.out.println("\n--- Repair order receipt ---");
        System.out.println("Order id:              " + repairOrderToPrint.getId());
        System.out.println("State:                 " + repairOrderToPrint.getState());
        System.out.println("Customer phone:        " + repairOrderToPrint.getCustomerPhone());
        System.out.println("Bike serial number:    " + repairOrderToPrint.getBikeSerialNo());
        System.out.println("Problem description:   " + repairOrderToPrint.getProblemDescr());
        System.out.println("Created:               " + repairOrderToPrint.getDate());
        System.out.println("Estimated completion:  " + repairOrderToPrint.getEstimatedCompletionDate());

        System.out.println("Diagnostic results:");
        for (String result : repairOrderToPrint.getDiagnosticResults()) {
            System.out.println("  - " + result);
        }

        System.out.println("Repair tasks:");
        for (RepairTaskDTO task : repairOrderToPrint.getRepairTasks()) {
            String status = task.isComplete() ? "done" : "pending";
            System.out.println("  - " + task.getDescription() + " (" + status + ")");
        }
        System.out.println("--- End of receipt ---\n");
    }
}
