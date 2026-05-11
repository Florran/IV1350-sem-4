package se.kth.iv1350.electricBike.view;

import java.io.IOException;
import se.kth.iv1350.electricBike.integration.RepairOrderDTO;
import se.kth.iv1350.electricBike.model.RepairOrderObserver;
import se.kth.iv1350.electricBike.util.FileLogger;

/**
 * An observer that writes updated repair orders to a log file, so
 * technicians and receptionists have a persistent record of changes.
 * The actual file handling is delegated to a {@link FileLogger}, which
 * keeps this class focused on the observer contract.
 */
public class RepairOrderLogger implements RepairOrderObserver {
    private static final String LOG_FILE_NAME = "repair-order-log.txt";
    private final FileLogger fileLogger;

    /**
     * Creates a new instance and opens the log file for writing. An
     * existing log file with the same name is overwritten.
     *
     * @throws IOException if the log file cannot be opened.
     */
    public RepairOrderLogger() throws IOException {
        this.fileLogger = new FileLogger(LOG_FILE_NAME);
    }

    /**
     * Writes the specified repair order to the log file.
     *
     * @param order A snapshot of the repair order after the update.
     */
    @Override
    public void repairOrderUpdated(RepairOrderDTO order) {
        fileLogger.log(order.toString());
    }
}
