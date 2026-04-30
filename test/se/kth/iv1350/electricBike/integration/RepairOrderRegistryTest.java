package se.kth.iv1350.electricBike.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.electricBike.model.RepairOrder;

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
    public void testFindByIdInEmptyRegistryReturnsNull() {
        RepairOrder result = repairOrderReg.findRepairOrderById("anyId");

        assertNull(result, "Lookup in empty registry should return null.");
    }

    @Test
    public void testFindExistingOrderByIdReturnsThatOrder() {
        RepairOrder stored = new RepairOrder("Bromsen ligger på", "0701112233", "SN001");
        repairOrderReg.createRepairOrder(stored);

        RepairOrder result = repairOrderReg.findRepairOrderById(stored.getId());

        assertNotNull(result, "Existing order should be found by its id.");
        assertEquals(stored.getId(), result.getId(), "Returned order should have the searched id.");
    }

    @Test
    public void testFindNonExistingIdInPopulatedRegistryReturnsNull() {
        repairOrderReg.createRepairOrder(new RepairOrder("Bromsen ligger på", "0701112233", "SN001"));

        RepairOrder result = repairOrderReg.findRepairOrderById("does-not-exist");

        assertNull(result, "Unknown id should return null even when registry has orders.");
    }

    @Test
    public void testFindCorrectOrderAmongMany() {
        RepairOrder first  = new RepairOrder("Punktering", "0700000001", "SN-A");
        RepairOrder target = new RepairOrder("Trasig kedja", "0700000002", "SN-B");
        RepairOrder third  = new RepairOrder("Slitet batteri", "0700000003", "SN-C");
        repairOrderReg.createRepairOrder(first);
        repairOrderReg.createRepairOrder(target);
        repairOrderReg.createRepairOrder(third);

        RepairOrder result = repairOrderReg.findRepairOrderById(target.getId());

        assertNotNull(result, "Matching order should be found among many.");
        assertEquals(target.getId(), result.getId(), "Returned order should be the one with the searched id.");
        assertEquals("Trasig kedja", result.getProblemDescr(),
                "Returned order should be the matching one, not another order in the registry.");
    }

    @Test
    public void testFindByEmptyStringReturnsNull() {
        repairOrderReg.createRepairOrder(new RepairOrder("Bromsen ligger på", "0701112233", "SN001"));

        RepairOrder result = repairOrderReg.findRepairOrderById("");

        assertNull(result, "Empty string id should return null.");
    }

    @Test
    public void testFindByNullReturnsNull() {
        repairOrderReg.createRepairOrder(new RepairOrder("Bromsen ligger på", "0701112233", "SN001"));

        RepairOrder result = repairOrderReg.findRepairOrderById(null);

        assertNull(result, "Null id should return null without throwing.");
    }

    @Test
    public void testUpdateExistingOrderKeepsItRetrievableAndDoesNotDuplicate() {
        RepairOrder order = new RepairOrder("Bromsen ligger på", "0701112233", "SN001");
        repairOrderReg.createRepairOrder(order);

        repairOrderReg.updateRepairOrder(order);

        assertNotNull(repairOrderReg.findRepairOrderById(order.getId()),
                "Order should still be retrievable after update with matching id.");
        assertEquals(1, repairOrderReg.findAllRepairOrders().size(),
                "Update with matching id should replace, not add a duplicate.");
    }

    @Test
    public void testUpdateNonExistingOrderDoesNotAddIt() {
        RepairOrder stored = new RepairOrder("Bromsen ligger på", "0701112233", "SN001");
        RepairOrder notStored = new RepairOrder("Punktering", "0700000002", "SN002");
        repairOrderReg.createRepairOrder(stored);

        repairOrderReg.updateRepairOrder(notStored);

        assertNull(repairOrderReg.findRepairOrderById(notStored.getId()),
                "Update with unknown id should not insert the order.");
        assertEquals(1, repairOrderReg.findAllRepairOrders().size(),
                "Registry should still contain only the originally stored order.");
        assertEquals(stored.getId(), repairOrderReg.findAllRepairOrders().get(0).getId(),
                "The originally stored order should remain in the registry.");
    }

    @Test
    public void testUpdateOnEmptyRegistryDoesNothing() {
        RepairOrder order = new RepairOrder("Punktering", "0700000002", "SN002");

        repairOrderReg.updateRepairOrder(order);

        assertEquals(0, repairOrderReg.findAllRepairOrders().size(),
                "Update on empty registry should not insert anything.");
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

    @Test
    public void testFindRepairOrderByNumberInEmptyRegistryReturnsNull() {
        assertNull(repairOrderReg.findRepairOrderByNumber("0701112233"),
                "Lookup in empty registry should return null.");
    }

    @Test
    public void testFindRepairOrderByNumberReturnsMatchingOrder() {
        RepairOrder stored = new RepairOrder("Bromsen ligger på", "0701112233", "SN001");
        repairOrderReg.createRepairOrder(stored);

        RepairOrder result = repairOrderReg.findRepairOrderByNumber("0701112233");

        assertNotNull(result, "Existing order should be found by phone number.");
        assertEquals(stored.getId(), result.getId(), "Returned order should be the stored one.");
    }

    @Test
    public void testFindRepairOrderByNumberUnknownPhoneReturnsNull() {
        repairOrderReg.createRepairOrder(new RepairOrder("Punktering", "0701112233", "SN001"));

        assertNull(repairOrderReg.findRepairOrderByNumber("0700000000"),
                "Unknown phone should return null even when registry has orders.");
    }

    @Test
    public void testFindRepairOrderByNumberReturnsFirstMatchWhenMultipleExist() {
        RepairOrder first = new RepairOrder("First", "0701112233", "SN-A");
        RepairOrder second = new RepairOrder("Second", "0701112233", "SN-B");
        repairOrderReg.createRepairOrder(first);
        repairOrderReg.createRepairOrder(second);

        RepairOrder result = repairOrderReg.findRepairOrderByNumber("0701112233");

        assertEquals(first.getId(), result.getId(),
                "Should return the first matching order, not later ones.");
    }

    @Test
    public void testFindRepairOrderByNumberEmptyStringReturnsNull() {
        repairOrderReg.createRepairOrder(new RepairOrder("Punktering", "0701112233", "SN001"));

        assertNull(repairOrderReg.findRepairOrderByNumber(""),
                "Empty string phone should return null.");
    }
}
