package se.kth.iv1350.electricBike.view;

import se.kth.iv1350.electricBike.integration.RepairOrderDTO;
import se.kth.iv1350.electricBike.model.RepairOrderObserver;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * An observer that writes updated repair orders to a log file, so technicians
 * and receptionists have a persistent record of changes.
 */
public class RepairOrderLogger implements RepairOrderObserver {
    private PrintWriter logStream;
    private static final String LOG_FILE_NAME = "repair-order-log.txt";

    /**
     * Creates a new instance and opens the log file for writing. An existing
     * log file with the same name will be overwritten.
     *
     * @throws IOException if the log file cannot be opened.
     */
    public RepairOrderLogger() throws IOException {
        this.logStream = new PrintWriter(new FileWriter(LOG_FILE_NAME), true);
    }

    /**
     * Writes the specified repair order to the log file.
     *
     * @param order A snapshot of the repair order after the update.
     */
    @Override
    public void repairOrderUpdated(RepairOrderDTO order) {
        logStream.println(order);
    }
}
