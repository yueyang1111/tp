package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Sweets extends Item {
    private String brand;
    private String sweetnessLevel;

    public Sweets(String name, int quantity, String binLocation,
                  String expiryDate, String brand,
                  String sweetnessLevel) {
        super(name, quantity, binLocation, expiryDate);
        this.brand = brand;
        this.sweetnessLevel = sweetnessLevel;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSweetnessLevel() {
        return sweetnessLevel;
    }

    public void setSweetnessLevel(String sweetnessLevel) {
        this.sweetnessLevel = sweetnessLevel;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " brand/" + brand
                + " sweetnessLevel/" + sweetnessLevel;
    }

    @Override
    public String toString() {
        return "[Sweets] " + super.toString()
                + ", Brand: " + brand
                + ", Sweetness Level: " + sweetnessLevel;
    }
}
