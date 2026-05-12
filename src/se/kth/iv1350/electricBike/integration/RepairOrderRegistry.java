package se.kth.iv1350.electricBike.integration;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the integration layer storage for repair orders. Implemented
 * as a singleton because the program may only have one repair order
 * registry. If two instances existed, orders would disappear depending on
 * which instance a caller happened to hold, since the stored orders live
 * inside the instance.
 */
public class RepairOrderRegistry {
    private static final RepairOrderRegistry INSTANCE = new RepairOrderRegistry();

    /**
     * The hardcoded id that simulates a database failure. Looking up
     * this id always throws {@link DatabaseFailureException}, since
     * the program has no real database to fail against.
     */
    public static final String DB_FAILURE_TRIGGER_ID = "trigger-db-failure";

    private final List<RepairOrderDTO> repairOrders = new ArrayList<>();

    private RepairOrderRegistry() {
    }

    /**
     * Returns the only instance of this registry.
     *
     * @return The sole instance.
     */
    public static RepairOrderRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * Removes all repair orders from the registry. Mainly intended to
     * reset the singleton state between unit tests, but also usable from
     * production code that needs a clean registry.
     */
    public void clearAll() {
        repairOrders.clear();
    }

    /**
     * Stores a repair order DTO in the registry.
     *
     * @param order The repair order DTO to store.
     */
    public void createRepairOrder(RepairOrderDTO order) {
        repairOrders.add(order);
    }

    /**
     * Finds all repair orders stored in the registry.
     *
     * @return A list containing all stored repair order DTOs.
     */
    public List<RepairOrderDTO> findAllRepairOrders() {
        return new ArrayList<>(repairOrders);
    }

    /**
     * Finds a repair order by its unique id. Looking up the hardcoded
     * id {@link #DB_FAILURE_TRIGGER_ID} simulates a database failure
     * and throws {@link DatabaseFailureException}, since the program
     * has no real database to fail against.
     *
     * @param id The unique id to look up.
     * @return The matching DTO, or {@code null} if no order matches.
     * @throws DatabaseFailureException when the lookup id matches the
     *                                  hardcoded failure trigger.
     */
    public RepairOrderDTO findRepairOrderById(String id) {
        if (id == null) {
            return null;
        }
        if (DB_FAILURE_TRIGGER_ID.equals(id)) {
            throw new DatabaseFailureException(id);
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
     *
     * @param updatedOrder The repair order DTO to update.
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
     * Finds an active repair order belonging to a customer with the
     * given phone number.
     *
     * @param phoneNumber The customer's phone number.
     * @return The matching DTO, or {@code null} if no order matches.
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
