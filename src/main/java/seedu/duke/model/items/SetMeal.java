package seedu.duke.model.items;

import seedu.duke.model.Item;

public class SetMeal extends Item {
    private String mealType;
    private String foodSize;

    public SetMeal(String name, int quantity, String binLocation,
                   String expiryDate, String mealType, String foodSize) {
        super(name, quantity, binLocation, expiryDate);
        this.mealType = mealType;
        this.foodSize = foodSize;
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

    @Override
    public String toString() {
        return "[SetMeal] " + super.toString()
                + ", Meal Type: " + mealType
                + ", Food Size: " + foodSize;
    }
}
