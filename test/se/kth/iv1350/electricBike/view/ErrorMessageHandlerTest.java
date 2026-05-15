package se.kth.iv1350.electricBike.view;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains tests for the ErrorMessageHandler class.
 */
public class ErrorMessageHandlerTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream captured;

    @BeforeEach
    public void setUp() {
        captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testShowErrorMsgPrintsUserMessage() {
        ErrorMessageHandler handler = new ErrorMessageHandler();

        handler.showErrorMsg("Något gick fel, försök igen.");

        assertTrue(captured.toString().contains("Något gick fel, försök igen."),
                "Användaren ska se meddelandet i konsolen.");
    }
}
