package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Snack extends Item {
    private String brand;
    private boolean isCrunchy;

    public Snack(String name, int quantity, String binLocation, String expiryDate,
                 String brand, boolean isCrunchy) {
        super(name,quantity,binLocation,expiryDate);
        this.brand = brand;
        this.isCrunchy = isCrunchy;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isCrunchy() {
        return isCrunchy;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " brand/" + brand
                + " isCrunchy/" + isCrunchy;
    }

    @Override
    public String toString() {
        return "[Snack] " + super.toString()
                + ", Brand: " + brand
                + ", Crunchy: " + isCrunchy;
    }
}
