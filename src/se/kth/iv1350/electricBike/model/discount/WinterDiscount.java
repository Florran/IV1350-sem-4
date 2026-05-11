package se.kth.iv1350.electricBike.model.discount;

/**
 * Represents a discount strategy that provides a winter discount.
 */
public class WinterDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double originalCost) {
        return originalCost * 0.90;
    }
}

