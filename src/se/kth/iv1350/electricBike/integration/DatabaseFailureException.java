package se.kth.iv1350.electricBike.integration;

/**
 * Thrown when the database that backs a registry cannot be reached or
 * answers in an unexpected way. The program has no real database, so
 * the situation is simulated by always throwing this exception when a
 * particular hardcoded item id is looked up in
 * {@link RepairOrderRegistry#findRepairOrderById(String)}.
 *
 * <p>This is an unchecked exception because the caller at the call site
 * has no meaningful way to recover from a missing database. The failure
 * has to bubble up to a higher layer that can log the problem and tell
 * the user that the operation could not be completed.</p>
 */
public class DatabaseFailureException extends RuntimeException {
    private final String itemId;

    /**
     * Creates a new instance that records which item id triggered the
     * simulated database failure. The id is included in the exception
     * message so that loggers and developers can see immediately which
     * lookup failed.
     *
     * @param itemId The identifier that was being looked up when the
     *               database call failed.
     */
    public DatabaseFailureException(String itemId) {
        super("Database call failed while looking up item with id "
                + itemId + ".");
        this.itemId = itemId;
    }

    /**
     * Returns the identifier that was looked up when the database call
     * failed, so that callers and loggers can include it in their output.
     *
     * @return The id that triggered the failure.
     */
    public String getItemId() {
        return itemId;
    }
}
