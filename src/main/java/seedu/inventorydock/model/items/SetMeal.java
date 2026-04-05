package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents a set meal item in the inventory.
 */
public class SetMeal extends Item {
    private String mealType;
    private String foodSize;
    private boolean hasDrinks;

    /**
     * Creates a set meal item with the given details.
     *
     * @param name Name of the set meal.
     * @param quantity Quantity of the set meal.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param mealType Type of meal.
     * @param foodSize Size of the meal.
     * @param hasDrinks Check whether the set meal includes free drinks
     */
    public SetMeal(String name, int quantity, String binLocation,
                   String expiryDate, String mealType,
                   String foodSize, boolean hasDrinks) {
        super(name, quantity, binLocation, expiryDate);
        this.mealType = mealType;
        this.foodSize = foodSize;
        this.hasDrinks = hasDrinks;
    }

    /** @return Size of the food. */
    public String getFoodSize() {
        return foodSize;
    }

    /** @param foodSize New food size. */
    public void setFoodSize(String foodSize) {
        this.foodSize = foodSize;
    }

    /** @return Type of meal. */
    public String getMealType() {
        return mealType;
    }

    /** @param mealType New meal type. */
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    /**
     * Returns whether this set meal has free drink included.
     *
     * @return {@code true} if the item has drinks, {@code false} otherwise.
     */
    public boolean hasDrinks() {
        return hasDrinks;
    }

    /**
     * Converts this set meal into a storage-friendly string format.
     *
     * @param categoryName Name of the category this item belongs to.
     * @return Storage string representation.
     */
    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " mealType/" + mealType
                + " foodSize/" + foodSize
                + " hasDrinks/" + hasDrinks;
    }

    /**
     * Returns a string representation of this set meal.
     *
     * @return Formatted set meal details.
     */
    @Override
    public String toString() {
        return "[SetMeal] " + super.toString()
                + ", Meal Type: " + mealType
                + ", Food Size: " + foodSize
                + ", Drinks Accompanied: " + hasDrinks;
    }
}
