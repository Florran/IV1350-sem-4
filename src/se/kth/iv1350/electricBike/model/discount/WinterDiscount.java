package se.kth.iv1350.electricBike.model.discount;

/**
 * Applies a 10% winter seasonal discount to the repair order's total cost.
 */
public class WinterDiscount implements DiscountStrategy {
    private static final double DISCOUNT_RATE = 0.10;

    @Override
    public double applyDiscount(double originalCost) {
        return originalCost * (1 - DISCOUNT_RATE);
    }
}