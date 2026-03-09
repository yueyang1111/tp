package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Snack extends Item {
    private String brand;
    private String expiryDate;

    public Snack(String name, int quantity, String binLocation, String brand, String expiryDate) {
        super(name, quantity, binLocation);
        this.brand = brand;
        this.expiryDate = expiryDate;
    }

    public String getBrand() {
        return brand;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "[Snack] " + super.toString()
                + ", Brand: " + brand
                + ", Expiry: " + expiryDate;
    }
}