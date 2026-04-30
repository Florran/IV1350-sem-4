package se.kth.iv1350.electricBike.model;

import org.junit.jupiter.api.Test;
import se.kth.iv1350.electricBike.integration.RepairTaskDTO;

import static org.junit.jupiter.api.Assertions.*;

class RepairTaskTest {

    @Test
    void testTaskCreationAndGetDescription() {
        String expectedDescription = "Byt display";
        RepairTask task = new RepairTask(expectedDescription);

        assertEquals(expectedDescription, task.getDescription(),
                "Beskrivningen stämmer inte med vad som angavs i konstruktorn.");
    }

    @Test
    void testTaskIsInitiallyNotComplete() {
        RepairTask task = new RepairTask("Laga punktering");

        assertFalse(task.isComplete(),
                "En nyskapad uppgift ska inte vara markerad som färdig.");
    }

    @Test
    void testCreateDTOPreservesDescriptionAndStatus() {
        RepairTask task = new RepairTask("Byt batteri");
        RepairTaskDTO dto = task.createDTO();

        assertEquals("Byt batteri", dto.getDescription(),
                "DTO:n ska behålla den ursprungliga beskrivningen.");
        assertFalse(dto.isComplete(),
                "Den nya uppgiften ska inte vara markerad som färdig i DTO:n.");
    }
}
