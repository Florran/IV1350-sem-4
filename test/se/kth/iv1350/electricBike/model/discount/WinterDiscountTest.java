package se.kth.iv1350.electricBike.model.discount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WinterDiscountTest {
    private DiscountStrategy winterDiscount;

    @BeforeEach
    public void setUp() {
        winterDiscount = new WinterDiscount();
    }

    @Test
    public void testApplyDiscountReducesCostByTenPercent() {
        double originalCost = 1000.0;
        double expectedCost = 900.0;

        double actualCost = winterDiscount.applyDiscount(originalCost);

        assertEquals(expectedCost, actualCost, 0.001);
    }

    @Test
    public void testApplyDiscountOnZeroCost() {
        double originalCost = 0.0;
        double expectedCost = 0.0;

        double actualCost = winterDiscount.applyDiscount(originalCost);

        assertEquals(expectedCost, actualCost, 0.001);
    }
}