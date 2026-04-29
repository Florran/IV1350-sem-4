package se.kth.iv1350.electricBike.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.electricBike.model.RepairOrder;

public class RepairOrderRegistryTest {

    private RepairOrderRegistry registry;

    @BeforeEach
    public void setUp() {
        this.registry = new RepairOrderRegistry();
    }

    @AfterEach
    public void tearDown() {
        this.registry = null;
    }

    @Test
    public void testFindByIdInEmptyRegistryReturnsNull() {
        RepairOrder result = registry.findRepairOrderById("anyId");

        assertNull(result, "Lookup in empty registry should return null.");
    }

    @Test
    public void testFindExistingOrderByIdReturnsThatOrder() {
        RepairOrder stored = new RepairOrder("Bromsen ligger på", "0701112233", "SN001");
        registry.createRepairOrder(stored);

        RepairOrder result = registry.findRepairOrderById(stored.getId());

        assertNotNull(result, "Existing order should be found by its id.");
        assertEquals(stored.getId(), result.getId(), "Returned order should have the searched id.");
    }

    @Test
    public void testFindNonExistingIdInPopulatedRegistryReturnsNull() {
        registry.createRepairOrder(new RepairOrder("Bromsen ligger på", "0701112233", "SN001"));

        RepairOrder result = registry.findRepairOrderById("does-not-exist");

        assertNull(result, "Unknown id should return null even when registry has orders.");
    }

    @Test
    public void testFindCorrectOrderAmongMany() {
        RepairOrder first  = new RepairOrder("Punktering", "0700000001", "SN-A");
        RepairOrder target = new RepairOrder("Trasig kedja", "0700000002", "SN-B");
        RepairOrder third  = new RepairOrder("Slitet batteri", "0700000003", "SN-C");
        registry.createRepairOrder(first);
        registry.createRepairOrder(target);
        registry.createRepairOrder(third);

        RepairOrder result = registry.findRepairOrderById(target.getId());

        assertNotNull(result, "Matching order should be found among many.");
        assertEquals(target.getId(), result.getId(), "Returned order should be the one with the searched id.");
        assertEquals("Trasig kedja", result.getProblemDescr(),
                "Returned order should be the matching one, not another order in the registry.");
    }

    @Test
    public void testFindByEmptyStringReturnsNull() {
        registry.createRepairOrder(new RepairOrder("Bromsen ligger på", "0701112233", "SN001"));

        RepairOrder result = registry.findRepairOrderById("");

        assertNull(result, "Empty string id should return null.");
    }

    @Test
    public void testFindByNullReturnsNull() {
        registry.createRepairOrder(new RepairOrder("Bromsen ligger på", "0701112233", "SN001"));

        RepairOrder result = registry.findRepairOrderById(null);

        assertNull(result, "Null id should return null without throwing.");
    }
}
