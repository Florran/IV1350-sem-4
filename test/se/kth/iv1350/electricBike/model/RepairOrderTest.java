package se.kth.iv1350.electricBike.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RepairOrderTest {
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
}