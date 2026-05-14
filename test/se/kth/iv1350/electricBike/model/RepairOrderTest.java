package se.kth.iv1350.electricBike.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.electricBike.integration.RepairOrderDTO;
import se.kth.iv1350.electricBike.model.discount.NoDiscount;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains tests for the RepairOrder class.
 */
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
        assertTrue(report.getResults().contains(expectedResult));
    }

    @Test
    void testAddRepairTask() {
        String expectedTaskDesc = "Replace gear cable";
        double expectedCost = 250.0;
        repairOrder.addRepairTask(expectedTaskDesc, expectedCost);

        boolean taskFound = false;
        for (RepairTask task : repairOrder.getRepairTasks()) {
            if (task.getDescription().equals(expectedTaskDesc) && task.getCost() == expectedCost) {
                taskFound = true;
                break;
            }
        }
        assertTrue(taskFound);
    }

    @Test
    void testAcceptRepairOrderChangesState() {
        repairOrder.acceptRepairOrder(new NoDiscount());
        assertEquals("Accepted", repairOrder.getState());
    }

    @Test
    public void testNewRepairOrderHasInitialState() {
        RepairOrder order = new RepairOrder("Batteriet laddar inte", "0705556767", "0001");
        assertEquals("Newly created", order.getState());
    }

    @Test
    public void testNewRepairOrderKeepsProblemDescription() {
        String problemDescr = "Motor stangs av i uppforsbacke";
        RepairOrder order = new RepairOrder(problemDescr, "0705556767", "0001");
        assertEquals(problemDescr, order.getProblemDescr());
    }

    @Test
    public void testNewRepairOrderGetsId() {
        RepairOrder order = new RepairOrder("Batteriet laddar inte", "0705556767", "0001");
        assertNotNull(order.getId());
        assertFalse(order.getId().trim().isEmpty());
    }

    @Test
    public void testCreateDTOContainsRepairOrderInformation() {
        String problemDescr = "Bromsen ligger pa";
        RepairOrder order = new RepairOrder(problemDescr, "0705556767", "0001");

        RepairOrderDTO dto = order.createDTO();

        assertEquals(order.getId(), dto.getId());
        assertEquals(order.getState(), dto.getState());
        assertEquals(order.getProblemDescr(), dto.getProblemDescr());
    }

    @Test
    void testGetRepairTasksReturnsCopy() {
        repairOrder.addRepairTask("Byt kedja", 350.0);
        repairOrder.getRepairTasks().clear();
        assertEquals(1, repairOrder.getRepairTasks().size());
    }

    @Test
    void testCreateDTOContainsDiagnosticResultsAndRepairTasks() {
        repairOrder.addDiagnosticResult("Slitet batteri");
        repairOrder.addRepairTask("Byt batteri", 1500.0);

        RepairOrderDTO dto = repairOrder.createDTO();

        assertTrue(dto.getDiagnosticResults().contains("Slitet batteri"));
        assertEquals(1, dto.getRepairTasks().size());
        assertEquals("Byt batteri", dto.getRepairTasks().get(0).getDescription());
    }
}