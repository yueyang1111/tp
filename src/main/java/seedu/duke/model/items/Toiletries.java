package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Toiletries extends Item {
    private String brand;
    private boolean isLiquid;

    public Toiletries(String name, int quantity, String binLocation, String brand, boolean isLiquid) {
        super(name, quantity, binLocation);
        this.brand = brand;
        this.isLiquid = isLiquid;
    }

    public String getBrand() {
        return brand;
    }

    public boolean isLiquid() {
        return isLiquid;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setLiquid(boolean isLiquid) {
        this.isLiquid = isLiquid;
    }

    @Override
    public String toString() {
        return "[Toiletries] " + super.toString()
                + ", Brand: " + brand
                + ", Liquid: " + isLiquid;
    }
}
