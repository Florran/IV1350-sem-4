package se.kth.iv1350.electricBike.model.discount;

/**
 * Defines the interface for a discount strategy.
 */
public interface DiscountStrategy {
    /**
     * Calculates the cost after applying a discount.
     *
     * @param originalCost The original cost before discount.
     * @return The new cost after the discount is applied.
     */
    double applyDiscount(double originalCost);
}
