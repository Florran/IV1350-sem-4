package se.kth.iv1350.electricBike.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.electricBike.integration.RepairTaskDTO;

/**
 * Contains tests for the RepairTask class.
 */
public class RepairTaskTest {

    @Test
    public void testGetDescription() {
        RepairTask task = new RepairTask("Byt däck", 250.0);
        assertEquals("Byt däck", task.getDescription());
    }

    @Test
    public void testGetCost() {
        RepairTask task = new RepairTask("Byt däck", 250.0);
        assertEquals(250.0, task.getCost(), 0.001);
    }

    @Test
    public void testIsCompleteInitiallyFalse() {
        RepairTask task = new RepairTask("Byt däck", 250.0);
        assertFalse(task.isComplete());
    }

    @Test
    public void testCreateDTO() {
        RepairTask task = new RepairTask("Byt bromsar", 500.0, true);
        RepairTaskDTO dto = task.createDTO();

        assertNotNull(dto);
    }
}