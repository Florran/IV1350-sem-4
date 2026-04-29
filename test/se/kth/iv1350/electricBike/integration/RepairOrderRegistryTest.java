package se.kth.iv1350.electricBike.integration;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.electricBike.model.RepairOrder;

import static org.junit.jupiter.api.Assertions.*;

public class RepairOrderRegistryTest {

    private RepairOrderRegistry repairOrderReg;

    @BeforeEach
    public void setUp() {
        this.repairOrderReg = new RepairOrderRegistry();
    }

    @AfterEach
    public void tearDown() {
        this.repairOrderReg = null;
    }

    @Test
    public void testFindAllInEmptyRegistryReturnsEmptyList() {
        List<RepairOrder> result = this.repairOrderReg.findAllRepairOrders();

        assertNotNull(result, "findAllRepairOrders should never return null");
        assertTrue(result.isEmpty(), "Empty registry should return an empty list");
    }

    @Test
    public void testFindAllReturnsAllStoredOrders() {
        RepairOrder firstOrder = new RepairOrder("Batteriet laddar inte", "0705556767", "0001");
        RepairOrder secondOrder = new RepairOrder("Bromsen ligger pa", "0705556768", "0002");
        this.repairOrderReg.createRepairOrder(firstOrder);
        this.repairOrderReg.createRepairOrder(secondOrder);

        List<RepairOrder> result = this.repairOrderReg.findAllRepairOrders();

        assertEquals(2, result.size(), "Registry should return all stored repair orders");
        assertTrue(result.contains(firstOrder), "Returned list should contain the first stored repair order");
        assertTrue(result.contains(secondOrder), "Returned list should contain the second stored repair order");
    }

    @Test
    public void testFindAllReturnsDefensiveCopy() {
        RepairOrder storedOrder = new RepairOrder("Motor stangs av", "0705556767", "0001");
        this.repairOrderReg.createRepairOrder(storedOrder);

        List<RepairOrder> firstResult = this.repairOrderReg.findAllRepairOrders();
        firstResult.clear();
        List<RepairOrder> secondResult = this.repairOrderReg.findAllRepairOrders();

        assertEquals(1, secondResult.size(), "Modifying the returned list should not affect the registry");
        assertTrue(secondResult.contains(storedOrder), "Stored repair order should remain in the registry");
    }

    @Test
    public void testCreateRepairOrderStoresOrder() {
        RepairOrder newOrder = new RepairOrder("Daligt dack", "0705556767", "0001");

        this.repairOrderReg.createRepairOrder(newOrder);

        List<RepairOrder> result = this.repairOrderReg.findAllRepairOrders();
        assertEquals(1, result.size(), "Created repair order should be stored in the registry");
        assertTrue(result.contains(newOrder), "Stored repair order should be the one that was created");
    }
}
