package se.kth.iv1350.electricBike.model.discount;

/**
 * Represents a discount strategy where no discount is applied.
 */
public class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double originalCost) {
        return originalCost;
    }
}