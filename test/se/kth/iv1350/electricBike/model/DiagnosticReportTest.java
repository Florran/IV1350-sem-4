package se.kth.iv1350.electricBike.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DiagnosticReportTest {
    private DiagnosticReport report;

    @BeforeEach
    void setUp() {
        report = new DiagnosticReport();
    }

    @Test
    void testNewReportIsEmpty() {
        assertTrue(report.getResults().isEmpty(),
                "En nyskapad rapport ska ha en tom resultatlista.");
    }

    @Test
    void testAddResult() {
        String expectedResult = "Punktering bakdäck";
        report.addResult(expectedResult);

        List<String> results = report.getResults();

        assertEquals(1, results.size(), "Listan ska innehålla exakt ett resultat.");
        assertTrue(results.contains(expectedResult), "Listan ska innehålla det tillagda resultatet.");
    }

    @Test
    void testAddMultipleResults() {
        report.addResult("Punktering bakdäck");
        report.addResult("Slitna bromsar");

        List<String> results = report.getResults();

        assertEquals(2, results.size(), "Listan ska innehålla exakt två resultat.");
        assertTrue(results.contains("Punktering bakdäck") && results.contains("Slitna bromsar"),
                "Listan ska innehålla alla tillagda resultat.");
    }

    @Test
    void testEncapsulation() {
        report.addResult("Trasig kedja");

        List<String> retrievedList = report.getResults();
        retrievedList.clear();

        List<String> listAfterClear = report.getResults();
        assertEquals(1, listAfterClear.size(),
                "Inkapslingen misslyckades! Original-listan ändrades när kopian ändrades.");
    }
}
