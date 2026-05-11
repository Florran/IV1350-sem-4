package se.kth.iv1350.electricBike.model.discount;

/**
 * Represents a discount strategy that provides a loyalty discount.
 */
public class LoyaltyDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double originalCost) {
        return originalCost * 0.85;
    }
}
