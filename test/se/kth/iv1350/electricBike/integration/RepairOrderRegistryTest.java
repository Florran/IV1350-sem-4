package se.kth.iv1350.electricBike.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.electricBike.model.RepairOrder;

/**
 * Contains tests for the RepairOrderRegistry class.
 */
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
        RepairOrderDTO result = repairOrderReg.findRepairOrderById("anyId");
        assertNull(result);
    }

    @Test
    public void testFindExistingOrderByIdReturnsThatOrder() {
        RepairOrder stored = new RepairOrder("Bromsen ligger på", "0701112233", "SN001");
        RepairOrderDTO dto = stored.createDTO();
        repairOrderReg.createRepairOrder(dto);

        RepairOrderDTO result = repairOrderReg.findRepairOrderById(dto.getId());

        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
    }

    @Test
    public void testFindNonExistingIdInPopulatedRegistryReturnsNull() {
        RepairOrder order = new RepairOrder("Bromsen ligger på", "0701112233", "SN001");
        repairOrderReg.createRepairOrder(order.createDTO());

        RepairOrderDTO result = repairOrderReg.findRepairOrderById("does-not-exist");

        assertNull(result);
    }

    @Test
    public void testFindCorrectOrderAmongMany() {
        RepairOrder first = new RepairOrder("Punktering", "0700000001", "SN-A");
        RepairOrder target = new RepairOrder("Trasig kedja", "0700000002", "SN-B");
        RepairOrder third = new RepairOrder("Slitet batteri", "0700000003", "SN-C");

        repairOrderReg.createRepairOrder(first.createDTO());
        RepairOrderDTO targetDto = target.createDTO();
        repairOrderReg.createRepairOrder(targetDto);
        repairOrderReg.createRepairOrder(third.createDTO());

        RepairOrderDTO result = repairOrderReg.findRepairOrderById(targetDto.getId());

        assertNotNull(result);
        assertEquals(targetDto.getId(), result.getId());
        assertEquals("Trasig kedja", result.getProblemDescr());
    }

    @Test
    public void testFindByEmptyStringReturnsNull() {
        RepairOrder order = new RepairOrder("Bromsen ligger på", "0701112233", "SN001");
        repairOrderReg.createRepairOrder(order.createDTO());

        RepairOrderDTO result = repairOrderReg.findRepairOrderById("");

        assertNull(result);
    }

    @Test
    public void testFindByNullReturnsNull() {
        RepairOrder order = new RepairOrder("Bromsen ligger på", "0701112233", "SN001");
        repairOrderReg.createRepairOrder(order.createDTO());

        RepairOrderDTO result = repairOrderReg.findRepairOrderById(null);

        assertNull(result);
    }

    @Test
    public void testUpdateExistingOrderKeepsItRetrievableAndDoesNotDuplicate() {
        RepairOrder order = new RepairOrder("Bromsen ligger på", "0701112233", "SN001");
        RepairOrderDTO dto = order.createDTO();
        repairOrderReg.createRepairOrder(dto);

        repairOrderReg.updateRepairOrder(dto);

        assertNotNull(repairOrderReg.findRepairOrderById(dto.getId()));
        assertEquals(1, repairOrderReg.findAllRepairOrders().size());
    }

    @Test
    public void testUpdateNonExistingOrderDoesNotAddIt() {
        RepairOrder stored = new RepairOrder("Bromsen ligger på", "0701112233", "SN001");
        RepairOrder notStored = new RepairOrder("Punktering", "0700000002", "SN002");

        RepairOrderDTO storedDto = stored.createDTO();
        repairOrderReg.createRepairOrder(storedDto);

        repairOrderReg.updateRepairOrder(notStored.createDTO());

        assertNull(repairOrderReg.findRepairOrderById(notStored.getId()));
        assertEquals(1, repairOrderReg.findAllRepairOrders().size());
        assertEquals(storedDto.getId(), repairOrderReg.findAllRepairOrders().get(0).getId());
    }

    @Test
    public void testUpdateOnEmptyRegistryDoesNothing() {
        RepairOrder order = new RepairOrder("Punktering", "0700000002", "SN002");

        repairOrderReg.updateRepairOrder(order.createDTO());

        assertEquals(0, repairOrderReg.findAllRepairOrders().size());
    }

    @Test
    public void testFindAllInEmptyRegistryReturnsEmptyList() {
        List<RepairOrderDTO> result = this.repairOrderReg.findAllRepairOrders();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindAllReturnsAllStoredOrders() {
        RepairOrder firstOrder = new RepairOrder("Batteriet laddar inte", "0705556767", "0001");
        RepairOrder secondOrder = new RepairOrder("Bromsen ligger pa", "0705556768", "0002");

        this.repairOrderReg.createRepairOrder(firstOrder.createDTO());
        this.repairOrderReg.createRepairOrder(secondOrder.createDTO());

        List<RepairOrderDTO> result = this.repairOrderReg.findAllRepairOrders();

        assertEquals(2, result.size());
    }

    @Test
    public void testCreateRepairOrderStoresOrder() {
        RepairOrder newOrder = new RepairOrder("Daligt dack", "0705556767", "0001");

        this.repairOrderReg.createRepairOrder(newOrder.createDTO());

        List<RepairOrderDTO> result = this.repairOrderReg.findAllRepairOrders();
        assertEquals(1, result.size());
    }

    @Test
    public void testFindRepairOrderByNumberInEmptyRegistryReturnsNull() {
        assertNull(repairOrderReg.findRepairOrderByNumber("0701112233"));
    }

    @Test
    public void testFindRepairOrderByNumberReturnsMatchingOrder() {
        RepairOrder stored = new RepairOrder("Bromsen ligger på", "0701112233", "SN001");
        RepairOrderDTO dto = stored.createDTO();
        repairOrderReg.createRepairOrder(dto);

        RepairOrderDTO result = repairOrderReg.findRepairOrderByNumber("0701112233");

        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
    }

    @Test
    public void testFindRepairOrderByNumberUnknownPhoneReturnsNull() {
        RepairOrder order = new RepairOrder("Punktering", "0701112233", "SN001");
        repairOrderReg.createRepairOrder(order.createDTO());

        assertNull(repairOrderReg.findRepairOrderByNumber("0700000000"));
    }

    @Test
    public void testFindRepairOrderByNumberReturnsFirstMatchWhenMultipleExist() {
        RepairOrder first = new RepairOrder("First", "0701112233", "SN-A");
        RepairOrder second = new RepairOrder("Second", "0701112233", "SN-B");

        RepairOrderDTO firstDto = first.createDTO();
        repairOrderReg.createRepairOrder(firstDto);
        repairOrderReg.createRepairOrder(second.createDTO());

        RepairOrderDTO result = repairOrderReg.findRepairOrderByNumber("0701112233");

        assertEquals(firstDto.getId(), result.getId());
    }

    @Test
    public void testFindRepairOrderByNumberEmptyStringReturnsNull() {
        RepairOrder order = new RepairOrder("Punktering", "0701112233", "SN001");
        repairOrderReg.createRepairOrder(order.createDTO());

        assertNull(repairOrderReg.findRepairOrderByNumber(""));
    }
}