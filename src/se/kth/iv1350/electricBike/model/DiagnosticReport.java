package se.kth.iv1350.electricBike.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the results from a technician's inspection of a bike.
 */
public class DiagnosticReport {
    private final List<String> results;

    /**
     * Creates a new, empty diagnostic report.
     */
    public DiagnosticReport() {
        this.results = new ArrayList<>();
    }

    /**
     * Creates a diagnostic report from an existing list of results.
     *
     * @param existingResults The results to populate the report with.
     */
    public DiagnosticReport(List<String> existingResults) {
        this.results = new ArrayList<>(existingResults);
    }

    /**
     * Adds a single finding to this report.
     *
     * @param result A description of the fault or finding.
     */
    void addResult(String result) {
        this.results.add(result);
    }

    /**
     * Retrieves all recorded diagnostic results.
     *
     * @return A list containing all current findings.
     */
    public List<String> getResults() {
        return new ArrayList<>(this.results);
    }
}