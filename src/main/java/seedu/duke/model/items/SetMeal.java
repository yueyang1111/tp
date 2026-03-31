package seedu.duke.model.items;

import seedu.duke.model.Item;

public class SetMeal extends Item {
    private String mealType;
    private String foodSize;
    private boolean hasDrinks;

    public SetMeal(String name, int quantity, String binLocation,
                   String expiryDate, String mealType,
                   String foodSize, boolean hasDrinks) {
        super(name, quantity, binLocation, expiryDate);
        this.mealType = mealType;
        this.foodSize = foodSize;
        this.hasDrinks = hasDrinks;
    }

    public String getFoodSize() {
        return foodSize;
    }

    public void setFoodSize(String foodSize) {
        this.foodSize = foodSize;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public boolean hasDrinks() {
        return hasDrinks;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " mealType/" + mealType
                + " foodSize/" + foodSize
                + " hasDrinks/" + hasDrinks;
    }

    @Override
    public String toString() {
        return "[SetMeal] " + super.toString()
                + ", Meal Type: " + mealType
                + ", Food Size: " + foodSize
                + ", Drinks Accompanied: " + hasDrinks;
    }
}
