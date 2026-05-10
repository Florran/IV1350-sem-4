package se.kth.iv1350.electricBike.view;

import se.kth.iv1350.electricBike.integration.RepairOrderDTO;
import se.kth.iv1350.electricBike.model.RepairOrderObserver;

/**
 * An observer that prints updated repair orders to <code>System.out</code>,
 * so technicians and receptionists can see changes as they happen.
 */
public class RepairOrderView implements RepairOrderObserver {
    /**
     * Prints the specified repair order to <code>System.out</code>.
     *
     * @param order A snapshot of the repair order after the update.
     */
    public void repairOrderUpdated(RepairOrderDTO order) {
        System.out.println(order);
    }
}
