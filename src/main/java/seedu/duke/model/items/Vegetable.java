package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Vegetable extends Item {
    private boolean isLeafy;

    public Vegetable(String name, int quantity, String binLocation, String expiryDate, boolean isLeafy) {
        super(name,quantity,binLocation,expiryDate);
        this.isLeafy = isLeafy;
    }

    public boolean isLeafy() {
        return isLeafy;
    }

    public void setLeafy(boolean isLeafy) {
        this.isLeafy = isLeafy;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " isLeafy/" + isLeafy;
    }

    @Override
    public String toString() {
        return "[Vegetable] " + super.toString()
                + ", Leafy: " + isLeafy;
    }
}
