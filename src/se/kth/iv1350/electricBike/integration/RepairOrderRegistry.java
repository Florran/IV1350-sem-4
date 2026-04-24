package se.kth.iv1350.electricBike.integration;

import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.electricBike.model.RepairOrder;

/**
 * Represents the integration layer storage for repair orders.
 */
public class RepairOrderRegistry {
    private List<RepairOrder> repairOrders;

    /**
     * Creates a new repair order registry with an empty repair order list.
     */
    public RepairOrderRegistry() {
        this.repairOrders = new ArrayList<>();
    }

    /**
     * Stores a repair order in the registry.
     * @param order The repair order to store.
     */
    public void createRepairOrder(RepairOrder order) {
        repairOrders.add(order);
    }

    /**
     * Finds all repair orders stored in the registry.
     * @return A list containing all stored repair orders.
     */
    public List<RepairOrder> findAllRepairOrders() {
        return new ArrayList<>(repairOrders);
    }
}
