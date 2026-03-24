package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Snack extends Item {
    private String brand;

    public Snack(String name, int quantity, String binLocation, String brand, String expiryDate) {
        super(name,quantity,binLocation,expiryDate);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " brand/" + brand;
    }

    @Override
    public String toString() {
        return "[Snack] " + super.toString()
                + ", Brand: " + brand;
    }
}
