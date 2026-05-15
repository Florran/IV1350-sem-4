package se.kth.iv1350.electricBike.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains tests for the LogHandler class.
 */
public class LogHandlerTest {

    private final Path logFile = Paths.get("repair-order-error-log.txt");
    private LogHandler logHandler;

    @AfterEach
    public void tearDown() throws IOException {
        if (logHandler != null) {
            logHandler.close();
            logHandler = null;
        }
        Files.deleteIfExists(logFile);
    }

    @Test
    public void testLogExceptionWritesMessageToFile() throws IOException {
        logHandler = new LogHandler();

        logHandler.logException(new IllegalStateException("databasen är nere"));

        String content = Files.readString(logFile);
        assertTrue(content.contains("databasen är nere"),
                "Loggfilen ska innehålla exceptionets meddelande.");
    }

    @Test
    public void testLogExceptionWritesExceptionClassToFile() throws IOException {
        logHandler = new LogHandler();

        logHandler.logException(new IllegalStateException("boom"));

        String content = Files.readString(logFile);
        assertTrue(content.contains("IllegalStateException"),
                "Loggfilen ska innehålla namnet på exceptionklassen.");
    }
}
