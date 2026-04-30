package se.kth.iv1350.electricBike.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RepairOrderDTOTest {

    @Test
    public void testToStringWithEmptyResultsAndTasksShowsNone() {
        RepairOrderDTO dto = new RepairOrderDTO(
                "abc12", "Newly created", "Battery dead",
                "0701112233", "SN001",
                "2026-04-30T10:00", "2026-05-07T10:00",
                List.of(), List.of());

        String result = dto.toString();

        assertTrue(result.contains("Diagnostic results: (none)"),
                "Empty diagnostic results should render as ' (none)'.");
        assertTrue(result.contains("Repair tasks: (none)"),
                "Empty repair tasks should render as ' (none)'.");
    }

    @Test
    public void testToStringListsDiagnosticResultsAsBullets() {
        RepairOrderDTO dto = new RepairOrderDTO(
                "abc12", "Newly created", "Battery dead",
                "0701112233", "SN001",
                "2026-04-30T10:00", "2026-05-07T10:00",
                List.of("Sensor broken", "Cell degraded"),
                List.of());

        String result = dto.toString();

        assertTrue(result.contains("\n  - Sensor broken"),
                "First diagnostic should appear as a bullet.");
        assertTrue(result.contains("\n  - Cell degraded"),
                "Second diagnostic should appear as a bullet.");
    }

    @Test
    public void testToStringIncludesAllScalarFields() {
        RepairOrderDTO dto = new RepairOrderDTO(
                "abc12", "Newly created", "Battery dead",
                "0701112233", "SN001",
                "2026-04-30T10:00", "2026-05-07T10:00",
                List.of(), List.of());

        String result = dto.toString();

        assertTrue(result.contains("abc12"), "id missing from toString");
        assertTrue(result.contains("Newly created"), "state missing from toString");
        assertTrue(result.contains("Battery dead"), "problem description missing from toString");
        assertTrue(result.contains("0701112233"), "customer phone missing from toString");
        assertTrue(result.contains("SN001"), "bike serial number missing from toString");
        assertTrue(result.contains("2026-04-30T10:00"), "creation date missing from toString");
        assertTrue(result.contains("2026-05-07T10:00"), "estimated completion date missing from toString");
    }

    @Test
    public void testConstructorCopiesDiagnosticResultsList() {
        List<String> input = new ArrayList<>();
        input.add("Sensor broken");
        RepairOrderDTO dto = new RepairOrderDTO(
                "abc12", "Newly created", "Battery dead",
                "0701112233", "SN001",
                "2026-04-30T10:00", "2026-05-07T10:00",
                input, List.of());

        input.add("Mutated after construction");

        assertEquals(1, dto.getDiagnosticResults().size(),
                "DTO should hold a defensive copy, not the caller's list.");
    }
}
