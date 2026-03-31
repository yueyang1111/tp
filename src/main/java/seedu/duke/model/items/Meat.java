package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Meat extends Item {
    private String meatType;
    private String origin;
    private boolean isFrozen;

    public Meat(String name, int quantity, String binLocation,
                String expiryDate, String meatType,
                String origin, boolean isFrozen) {
        super(name, quantity, binLocation, expiryDate);
        this.meatType = meatType;
        this.origin = origin;
        this.isFrozen = isFrozen;
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

    public boolean isFrozen() {
        return isFrozen;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " meatType/" + meatType
                + " origin/" + origin
                + " isFrozen/" + isFrozen;
    }

    @Override
    public String toString() {
        return "[Meat] " + super.toString()
                + ", Meat Type: " + meatType
                + ", Origin: " + origin
                + ", Frozen: " + isFrozen;
    }
}
