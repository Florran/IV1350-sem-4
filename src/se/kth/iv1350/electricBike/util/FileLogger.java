package se.kth.iv1350.electricBike.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Writes log entries to a text file. Every part of the program that needs
 * to log to a file shares this small API, so that the rest of the code
 * does not have to know anything about {@link PrintWriter} or
 * {@link FileWriter}.
 *
 * <p>The file is opened when the logger is created and stays open for
 * the lifetime of the logger instance. Each call to {@link #log(String)}
 * appends one line to the file and flushes it immediately, so log entries
 * are not lost if the program terminates unexpectedly.</p>
 */
public class FileLogger {
    private final PrintWriter out;

    /**
     * Opens the specified file for logging. If a file with the same name
     * already exists, it is overwritten so that each program run starts
     * with a clean log.
     *
     * @param fileName The name of the file that log entries are written
     *                 to.
     * @throws IOException if the file cannot be opened for writing, for
     *                     example because the directory is not writable.
     */
    public FileLogger(String fileName) throws IOException {
        this.out = new PrintWriter(new FileWriter(fileName), true);
    }

    /**
     * Appends one entry, followed by a newline, to the log file. The
     * output is flushed immediately.
     *
     * @param entry The text to write.
     */
    public void log(String entry) {
        out.println(entry);
    }
}
