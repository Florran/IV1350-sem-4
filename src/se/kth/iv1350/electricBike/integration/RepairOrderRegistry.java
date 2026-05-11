package se.kth.iv1350.electricBike.integration;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the integration layer storage for repair orders.
 */
public class RepairOrderRegistry {
    private List<RepairOrderDTO> repairOrders;

    /**
     * Creates a new repair order registry with an empty list.
     */
    public RepairOrderRegistry() {
        this.repairOrders = new ArrayList<>();
    }

    /**
     * Stores a repair order DTO in the registry.
     * * @param order The repair order DTO to store.
     */
    public void createRepairOrder(RepairOrderDTO order) {
        repairOrders.add(order);
    }

    /**
     * Finds all repair orders stored in the registry.
     * * @return A list containing all stored repair order DTOs.
     */
    public List<RepairOrderDTO> findAllRepairOrders() {
        return new ArrayList<>(repairOrders);
    }

    /**
     * Finds a repair order by its unique ID.
     * * @param id The unique ID of the order to find.
     * 
     * @return The matching RepairOrderDTO, or null if not found.
     */
    public RepairOrderDTO findRepairOrderById(String id) {
        if (id == null) {
            return null;
        }

        for (RepairOrderDTO order : repairOrders) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }

    /**
     * Updates a repair order in the registry, matching on its id.
     * * @param updatedOrder The repair order DTO to update.
     */
    public void updateRepairOrder(RepairOrderDTO updatedOrder) {
        for (int i = 0; i < repairOrders.size(); i++) {
            if (repairOrders.get(i).getId().equals(updatedOrder.getId())) {
                repairOrders.set(i, updatedOrder);
                return;
            }
        }
    }

    /**
     * Finds an active repair order belonging to a customer with the given phone
     * number.
     * * @param phoneNumber The customer's phone number.
     * 
     * @return The matching RepairOrderDTO, or null if not found.
     */
    public RepairOrderDTO findRepairOrderByNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }

        for (RepairOrderDTO order : repairOrders) {
            if (order.getCustomerPhone().equals(phoneNumber)) {
                return order;
            }
        }
        return null;
    }
}