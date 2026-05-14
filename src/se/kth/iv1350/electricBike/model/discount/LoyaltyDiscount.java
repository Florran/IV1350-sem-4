package se.kth.iv1350.electricBike.model.discount;

/**
 * Applies a 15% loyalty discount to the repair order's total cost.
 */
public class LoyaltyDiscount implements DiscountStrategy {
    private static final double DISCOUNT_RATE = 0.15;

    @Override
    public double applyDiscount(double originalCost) {
        return originalCost * (1 - DISCOUNT_RATE);
    }
}