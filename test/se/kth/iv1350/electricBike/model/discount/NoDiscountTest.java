package se.kth.iv1350.electricBike.model.discount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NoDiscountTest {
    private DiscountStrategy noDiscount;

    @BeforeEach
    public void setUp() {
        noDiscount = new NoDiscount();
    }

    @Test
    public void testApplyDiscountLeavesCostUnchanged() {
        double originalCost = 1500.0;

        double actualCost = noDiscount.applyDiscount(originalCost);

        assertEquals(originalCost, actualCost, 0.001);
    }

    @Test
    public void testApplyDiscountOnZeroCost() {
        double originalCost = 0.0;

        double actualCost = noDiscount.applyDiscount(originalCost);

        assertEquals(originalCost, actualCost, 0.001);
    }
}