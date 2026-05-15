package se.kth.iv1350.electricBike.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Shows error messages to the user.
 */
public class ErrorMessageHandler {
    private static final DateTimeFormatter TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Shows the message to the user, prefixed with a timestamp.
     *
     * @param userMsg The text to show the user.
     */
    public void showErrorMsg(String userMsg) {
        System.out.println("[" + LocalDateTime.now().format(TIMESTAMP) + "] " + userMsg);
    }
}
