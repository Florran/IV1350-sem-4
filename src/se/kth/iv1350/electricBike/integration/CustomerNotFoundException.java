package se.kth.iv1350.electricBike.integration;

/**
 * Thrown when no customer is found
 */
public class CustomerNotFoundException extends Exception {
    private final String phoneNumber;

    /**
     * Creates a new instance with an error message explaining why the customer search returned no customers.
     * @param usedPhoneNumber The phone number used when a customer was searched for.
     */
    public CustomerNotFoundException(String usedPhoneNumber) {
        super("Unable to find customer associated with phone number " + usedPhoneNumber);
        this.phoneNumber = usedPhoneNumber;
    }

    public String getNumber() {
        return this.phoneNumber;
    }
}
