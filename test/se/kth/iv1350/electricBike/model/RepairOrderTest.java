package se.kth.iv1350.electricBike.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.electricBike.integration.RepairOrderDTO;

import static org.junit.jupiter.api.Assertions.*;

public class RepairOrderTest {
    private RepairOrder repairOrder;

    @BeforeEach
    void setUp() {
        repairOrder = new RepairOrder("Gears not shifting", "0701234567", "SN123456");
    }

    @Test
    void testAddDiagnosticResult() {
        String expectedResult = "Worn out gear cable";
        repairOrder.addDiagnosticResult(expectedResult);

        DiagnosticReport report = repairOrder.getDiagnosticReport();
        assertTrue(report.getResults().contains(expectedResult),
                "The diagnostic result should be added to the report.");
    }

    @Test
    void testAddRepairTask() {
        String expectedTaskDesc = "Replace gear cable";
        repairOrder.addRepairTask(expectedTaskDesc);

        boolean taskFound = false;
        for (RepairTask task : repairOrder.getRepairTasks()) {
            if (task.getDescription().equals(expectedTaskDesc)) {
                taskFound = true;
                break;
            }
        }
        assertTrue(taskFound, "The repair task should be added to the order.");
    }

    @Test
    void testAcceptRepairOrderChangesState() {
        repairOrder.acceptRepairOrder();
        assertEquals("Accepted", repairOrder.getState(),
                "The state should change to 'Accepted'.");
    }

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
        assertEquals(repairOrder.getProblemDescr(), dto.getProblemDescr(),
                "DTO should contain the problem description");
    }
}