package se.kth.iv1350.electricBike.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.electricBike.model.RepairTask;

/**
 * Contains tests for the RepairOrderDTO class.
 */
public class RepairOrderDTOTest {

        @Test
        public void testToStringWithEmptyResultsAndTasksShowsNone() {
                RepairOrderDTO dto = new RepairOrderDTO(
                                "abc12", "Newly created", "Battery dead",
                                "0701112233", "SN001",
                                "2026-04-30T10:00", "2026-05-07T10:00",
                                0.0, 0.0, new ArrayList<String>(), new ArrayList<RepairTaskDTO>());

                String result = dto.toString();

                assertTrue(result.contains("Diagnostic results: (none)"));
                assertTrue(result.contains("Repair tasks: (none)"));
        }

        @Test
        public void testToStringListsDiagnosticResultsAsBullets() {
                List<String> diagnostics = new ArrayList<>();
                diagnostics.add("Sensor broken");
                diagnostics.add("Cell degraded");

                RepairOrderDTO dto = new RepairOrderDTO(
                                "abc12", "Newly created", "Battery dead",
                                "0701112233", "SN001",
                                "2026-04-30T10:00", "2026-05-07T10:00",
                                0.0, 0.0, diagnostics, new ArrayList<RepairTaskDTO>());

                String result = dto.toString();

                assertTrue(result.contains("\n  - Sensor broken"));
                assertTrue(result.contains("\n  - Cell degraded"));
        }

        @Test
        public void testToStringIncludesAllScalarFields() {
                RepairOrderDTO dto = new RepairOrderDTO(
                                "abc12", "Newly created", "Battery dead",
                                "0701112233", "SN001",
                                "2026-04-30T10:00", "2026-05-07T10:00",
                                0.0, 0.0, new ArrayList<String>(), new ArrayList<RepairTaskDTO>());

                String result = dto.toString();

                assertTrue(result.contains("abc12"));
                assertTrue(result.contains("Newly created"));
                assertTrue(result.contains("Battery dead"));
                assertTrue(result.contains("0701112233"));
                assertTrue(result.contains("SN001"));
                assertTrue(result.contains("2026-04-30 10:00"));
                assertTrue(result.contains("2026-05-07 10:00"));
        }

        @Test
        public void testConstructorCopiesDiagnosticResultsList() {
                List<String> input = new ArrayList<>();
                input.add("Sensor broken");

                RepairOrderDTO dto = new RepairOrderDTO(
                                "abc12", "Newly created", "Battery dead",
                                "0701112233", "SN001",
                                "2026-04-30T10:00", "2026-05-07T10:00",
                                0.0, 0.0, input, new ArrayList<RepairTaskDTO>());

                input.add("Mutated after construction");

                assertEquals(1, dto.getDiagnosticResults().size());
        }

        @Test
        public void testConstructorCopiesRepairTasksList() {
                List<RepairTaskDTO> inputTasks = new ArrayList<>();
                RepairTask task1 = new RepairTask("Byt kedja", 350.0);
                inputTasks.add(task1.createDTO());

                RepairOrderDTO dto = new RepairOrderDTO(
                                "abc12", "Newly created", "Battery dead",
                                "0701112233", "SN001",
                                "2026-04-30T10:00", "2026-05-07T10:00",
                                0.0, 0.0, new ArrayList<String>(), inputTasks);

                RepairTask task2 = new RepairTask("Byt motor", 1500.0);
                inputTasks.add(task2.createDTO());

                assertEquals(1, dto.getRepairTasks().size());
        }
}