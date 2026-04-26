package se.kth.iv1350.electricBike.integration;

/**
 * Represents the integration layer storage for customer information.
 */
public class CustomerRegistry {

    /**
     * Tries to find a customer based on a specified phone number, this would go in and ask a DB or equal but that is scoped out for the assignment. For now the values are hardcoded
     * @param phoneNumber The phone number used to search for the customer
     * @return Returns the found customer, or null if no matching customer exists
     */
    public CustomerDTO findCustomer(String phoneNumber) {
        if (phoneNumber.equals("0705556767")) {
            return new CustomerDTO(
                "Customer1",
                "Customer1@bike.repair",
                "0705556767",
                "Hot Wheels",
                "0001",
                "Lambo"
            );
        }
        return null;
    }
}
