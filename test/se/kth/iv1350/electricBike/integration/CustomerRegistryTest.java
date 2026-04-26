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
    public void testFindExistingCustomer() {
        String phone = "0705556767";
        CustomerDTO result = this.customerReg.findCustomer(phone);
        assertNotNull(result, "Existing customer was not found");
        assertEquals(phone, result.getPhoneNumber(), "Wrong phone number");
        assertEquals("Customer1", result.getName(), "Wrong name.");
    }

    @Test
    public void testFindUnknownCustomerReturnsNull() {
        String phone = "0700000000";
        CustomerDTO result = this.customerReg.findCustomer(phone);

        assertNull(result, "invalid phone number should return null");

    }

    @Test
    public void testFindCustomerEmptyPhoneNrReturnsNull() {
        String phone = "";
        CustomerDTO result = this.customerReg.findCustomer(phone);

        assertNull(result, "no phone number should return null");
    }
}
