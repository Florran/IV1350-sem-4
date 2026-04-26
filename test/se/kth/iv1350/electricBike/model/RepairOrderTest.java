package se.kth.iv1350.electricBike.model;

import org.junit.jupiter.api.Test;
import se.kth.iv1350.electricBike.integration.RepairOrderDTO;

import static org.junit.jupiter.api.Assertions.*;

public class RepairOrderTest {

    @Test
    public void testNewRepairOrderHasInitialState() {
        RepairOrder repairOrder = new RepairOrder("Batteriet laddar inte", "0705556767", "0001");

        assertEquals("Newly created", repairOrder.getState(), "New repair order should have initial state");
    }

    @Test
    public void testNewRepairOrderKeepsProblemDescription() {
        String problemDescr = "Motor stangs av i uppforsbacke";
        RepairOrder repairOrder = new RepairOrder(problemDescr, "0705556767", "0001");

        assertEquals(problemDescr, repairOrder.getProblemDescr(), "Repair order should keep the problem description");
    }

    @Test
    public void testNewRepairOrderGetsId() {
        RepairOrder repairOrder = new RepairOrder("Batteriet laddar inte", "0705556767", "0001");

        assertNotNull(repairOrder.getId(), "Repair order should have an id");
        assertFalse(repairOrder.getId().trim().isEmpty(), "Repair order id should not be blank");
    }

    @Test
    public void testCreateDTOContainsRepairOrderInformation() {
        String problemDescr = "Bromsen ligger pa";
        RepairOrder repairOrder = new RepairOrder(problemDescr, "0705556767", "0001");

        RepairOrderDTO dto = repairOrder.createDTO();

        assertEquals(repairOrder.getId(), dto.getId(), "DTO should contain the repair order id");
        assertEquals(repairOrder.getState(), dto.getState(), "DTO should contain the repair order state");
        assertEquals(repairOrder.getProblemDescr(), dto.getProblemDescr(), "DTO should contain the problem description");
    }
}
