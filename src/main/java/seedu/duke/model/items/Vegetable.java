package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Vegetable extends Item {
    private String expiryDate;
    private boolean isLeafy;

    public Vegetable(String name, int quantity, String binLocation, String expiryDate, boolean isLeafy) {
        super(name, quantity, binLocation);
        this.expiryDate = expiryDate;
        this.isLeafy = isLeafy;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public boolean isLeafy() {
        return isLeafy;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setLeafy(boolean isLeafy) {
        this.isLeafy = isLeafy;
    }

    @Override
    public String toString() {
        return "[Vegetable] " + super.toString()
                + ", Expiry: " + expiryDate
                + ", Leafy: " + isLeafy;
    }
}
