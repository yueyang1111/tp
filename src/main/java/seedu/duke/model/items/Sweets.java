package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Sweets extends Item {
    private String brand;
    private String sweetnessLevel;
    private boolean isChewy;

    public Sweets(String name, int quantity, String binLocation,
                  String expiryDate, String brand,
                  String sweetnessLevel, boolean isChewy) {
        super(name, quantity, binLocation, expiryDate);
        this.brand = brand;
        this.sweetnessLevel = sweetnessLevel;
        this.isChewy = isChewy;
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

    public boolean isChewy() {
        return isChewy;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " brand/" + brand
                + " sweetnessLevel/" + sweetnessLevel
                + " isChewy/" + isChewy;
    }

    @Override
    public String toString() {
        return "[Sweets] " + super.toString()
                + ", Brand: " + brand
                + ", Sweetness Level: " + sweetnessLevel
                + ", Chewy: " + isChewy;
    }
}
