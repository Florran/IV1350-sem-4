package se.kth.iv1350.electricBike.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Writes caught exceptions to a log file so developers can investigate them.
 */
public class LogHandler {
    private static final String LOG_FILE_NAME = "repair-order-error-log.txt";
    private static final DateTimeFormatter TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final FileLogger errorLog;

    /**
     * Opens the error log.
     *
     * @throws IOException if the error log cannot be opened.
     */
    public LogHandler() throws IOException {
        this.errorLog = new FileLogger(LOG_FILE_NAME);
    }

    /**
     * Writes the exception, its message, and its stack trace to the log.
     *
     * @param exc The exception to record.
     */
    public void logException(Exception exc) {
        StringWriter formatted = new StringWriter();
        formatted.write(LocalDateTime.now().format(TIMESTAMP)
                + " - " + exc.getClass().getSimpleName()
                + ": " + exc.getMessage() + System.lineSeparator());
        exc.printStackTrace(new PrintWriter(formatted));
        errorLog.log(formatted.toString());
    }

    /**
     * Closes the underlying log file. Mainly needed so tests on Windows
     * can delete the file afterwards.
     */
    public void close() {
        errorLog.close();
    }
}
