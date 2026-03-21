package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Toiletries extends Item {
    private String brand;
    private boolean isLiquid;
    private String expiryDate;

    public Toiletries(String name, int quantity, String binLocation, String brand,
                      boolean isLiquid, String expiryDate) {
        super(name, quantity, binLocation);
        this.brand = brand;
        this.isLiquid = isLiquid;
        this.expiryDate = expiryDate;
    }

    public String getBrand() {
        return brand;
    }

    public String getExpiryDate() {
        return expiryDate;
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

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "[Toiletries] " + super.toString()
                + ", Brand: " + brand
                + ", Liquid: " + isLiquid
                + ", Expiry : " + expiryDate;
    }
}
