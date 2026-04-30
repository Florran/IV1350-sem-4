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

    /**
     * Finds a repair order by its unique ID.
     * @param id The unique ID of the order to find.
     * @return The matching RepairOrder, or null if no match exists.
     */
    public RepairOrder findRepairOrderById(String id) {
        for (RepairOrder order : repairOrders) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }

    /**
     * Updates a repair order in the registry, matching on its id.
     * @param repairOrder The repair order to update.
     */
    public void updateRepairOrder(RepairOrder repairOrder) {
        for (int i = 0; i < repairOrders.size(); i++) {
            if (repairOrders.get(i).getId().equals(repairOrder.getId())) {
                repairOrders.set(i, repairOrder);
                return;
            }
        }
    }

    /**
     * Finds an active repair order belonging to a customer with the given phone number.
     * @param phoneNumber The customer's phone number.
     * @return The matching RepairOrder, or null if none is found.
     */
    public RepairOrder findRepairOrderByNumber(String phoneNumber) {
        for (RepairOrder order : repairOrders) {
            if (order.getCustomerPhone().equals(phoneNumber)) {
                return order;
            }
        }
        return null;
    }
}