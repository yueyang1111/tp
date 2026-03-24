package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Meat extends Item {
    private String meatType;
    private String origin;

    public Meat(String name, int quantity, String binLocation,
                String expiryDate, String meatType, String origin) {
        super(name, quantity, binLocation, expiryDate);
        this.meatType = meatType;
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getMeatType() {
        return meatType;
    }

    public void setMeatType(String meatType) {
        this.meatType = meatType;
    }

    @Override
    public String toString() {
        return "[Meat] " + super.toString()
                + ", Meat Type: " + meatType
                + ", Origin: " + origin;
    }
}
