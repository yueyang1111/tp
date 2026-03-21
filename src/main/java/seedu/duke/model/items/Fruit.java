package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Fruit extends Item {
    private String size;
    private boolean isRipe;

    public Fruit(String name, int quantity, String binLocation,
                 String expiryDate, String size, boolean isRipe) {
        super(name,quantity,binLocation,expiryDate);
        this.size = size;
        this.isRipe = isRipe;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isRipe() {
        return isRipe;
    }

    public void setRipe(boolean isRipe) {
        this.isRipe = isRipe;
    }

    @Override
    public String toString() {
        return "[Fruit] " + super.toString()
                + ", Size: " + size
                + ", Ripe: " + isRipe;
    }
}
