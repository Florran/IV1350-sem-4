package se.kth.iv1350.electricBike.model;

import se.kth.iv1350.electricBike.integration.RepairOrderDTO;

/**
 * A listener interface for getting notified when a repair order is updated.
 * A class that wants such notifications implements this interface and
 * registers an instance of itself with a {@link RepairOrder}. When the
 * repair order changes, the observer's {@link #repairOrderUpdated} method
 * is called.
 */
public interface RepairOrderObserver {
    /**
     * Called when a repair order has been updated.
     *
     * @param order A snapshot of the repair order after the update.
     */
    void repairOrderUpdated(RepairOrderDTO order);
}
