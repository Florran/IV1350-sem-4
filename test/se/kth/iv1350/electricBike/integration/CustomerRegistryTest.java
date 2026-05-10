package se.kth.iv1350.electricBike.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerRegistryTest {

    private CustomerRegistry customerReg;

    @BeforeEach
    public void setUp() {
        this.customerReg = new CustomerRegistry();
    }

    @AfterEach
    public void tearDown() {
        this.customerReg = null;
    }

    @Test
    public void testFindExistingCustomer() throws CustomerNotFoundException {
        String phone = "0705556767";
        CustomerDTO result = this.customerReg.findCustomer(phone);
        assertNotNull(result, "Existing customer was not found");
        assertEquals(phone, result.getPhoneNumber(), "Wrong phone number");
        assertEquals("Customer1", result.getName(), "Wrong name.");
    }

    @Test
    public void testFindUnknownCustomerThrowsException() {
        String phone = "0700000000";
        try {
            this.customerReg.findCustomer(phone);
            fail("Expected CustomerNotFoundException for unknown phone number.");
        } catch (CustomerNotFoundException exc) {
            assertTrue(exc.getMessage().contains(phone),
                    "Exception message should contain the searched phone number, was: " + exc.getMessage());
            assertEquals(phone, exc.getNumber(),
                    "Exception should carry the searched phone number.");
        }
    }

    @Test
    public void testFindCustomerEmptyPhoneNrThrowsException() {
        String phone = "";
        try {
            this.customerReg.findCustomer(phone);
            fail("Expected CustomerNotFoundException for empty phone number.");
        } catch (CustomerNotFoundException exc) {
            assertEquals(phone, exc.getNumber(),
                    "Exception should carry the searched phone number.");
        }
    }

    @Test
    public void testFailedLookupDoesNotAffectSubsequentLookup() {
        try {
            this.customerReg.findCustomer("0700000000");
            fail("Expected CustomerNotFoundException for unknown phone number.");
        } catch (CustomerNotFoundException exc) {
            // expected
        }
        try {
            CustomerDTO result = this.customerReg.findCustomer("0705556767");
            assertNotNull(result, "Registry should still find existing customer after a failed lookup.");
        } catch (CustomerNotFoundException exc) {
            fail("Existing customer lookup should not throw after a previous failed lookup.");
        }
    }
}
