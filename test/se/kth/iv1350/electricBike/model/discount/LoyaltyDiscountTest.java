package se.kth.iv1350.electricBike.model.discount;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains tests for the LoyaltyDiscount strategy.
 */
public class LoyaltyDiscountTest {

    @Test
    public void testApplyDiscountCalculatesCorrectPrice() {
        DiscountStrategy loyaltyDiscount = new LoyaltyDiscount();
        double originalCost = 1000.0;

        double expectedCostAfterDiscount = 850.0;
        double actualCostAfterDiscount = loyaltyDiscount.applyDiscount(originalCost);

        assertEquals(expectedCostAfterDiscount, actualCostAfterDiscount,
                "Lojalitetsrabatten räknade inte ut rätt slutpris.");
    }

    @Test
    public void testApplyDiscountOnZeroCostReturnsZero() {
        DiscountStrategy loyaltyDiscount = new LoyaltyDiscount();
        double originalCost = 0.0;

        double expectedCostAfterDiscount = 0.0;
        double actualCostAfterDiscount = loyaltyDiscount.applyDiscount(originalCost);

        assertEquals(expectedCostAfterDiscount, actualCostAfterDiscount,
                "Rabatt på noll kronor ska resultera i noll kronor.");
    }
}