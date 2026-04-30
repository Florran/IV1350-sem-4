package se.kth.iv1350.electricBike.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CustomerDTOTest {

    @Test
    public void testToStringContainsAllFields() {
        CustomerDTO dto = new CustomerDTO(
                "Anna", "anna@example.com", "0701112233",
                "Trek", "SN001", "Domane");

        String expected =
                "Name:               Anna\n" +
                "Email:              anna@example.com\n" +
                "Phone number:       0701112233\n" +
                "Bike brand:         Trek\n" +
                "Bike serial number: SN001\n" +
                "Bike model:         Domane";

        assertEquals(expected, dto.toString(),
                "toString should produce the documented multi-line format with all fields.");
    }
}
