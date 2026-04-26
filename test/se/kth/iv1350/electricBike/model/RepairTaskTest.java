package se.kth.iv1350.electricBike.model;

import org.junit.jupiter.api.Test;
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
}