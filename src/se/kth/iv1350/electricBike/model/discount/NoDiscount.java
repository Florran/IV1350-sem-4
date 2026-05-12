package se.kth.iv1350.electricBike.model.discount;

/**
 * Represents the absence of a discount (Null Object pattern).
 * Returns the original cost unmodified.
 */
public class NoDiscount implements DiscountStrategy {

    @Override
    public double applyDiscount(double originalCost) {
        return originalCost;
    }
}