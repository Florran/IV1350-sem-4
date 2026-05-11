package se.kth.iv1350.electricBike.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains tests for the FileLogger class.
 */
public class FileLoggerTest {

    private final Path testFile = Paths.get("test-filelogger.txt");

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(testFile);
    }

    @Test
    public void testLogWritesEntryToFile() throws IOException {
        FileLogger logger = new FileLogger(testFile.toString());

        logger.log("hello world");

        assertTrue(Files.exists(testFile));
        String content = Files.readString(testFile);
        assertTrue(content.contains("hello world"),
                "Log file should contain the logged entry, but was: " + content);
    }

    @Test
    public void testLogWritesEachEntryOnSeparateLine() throws IOException {
        FileLogger logger = new FileLogger(testFile.toString());

        logger.log("first entry");
        logger.log("second entry");

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(2, lines.size(), "Expected exactly two lines in the log");
        assertEquals("first entry", lines.get(0));
        assertEquals("second entry", lines.get(1));
    }

    @Test
    public void testCreatingLoggerOverwritesExistingFile() throws IOException {
        Files.writeString(testFile, "previous content\n");

        FileLogger logger = new FileLogger(testFile.toString());
        logger.log("fresh entry");

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size(), "Existing file should be overwritten");
        assertEquals("fresh entry", lines.get(0));
    }
}
