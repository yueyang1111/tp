package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Burger extends Item {
    private boolean isSpicy;
    private String pattyType;

    public Burger(String name, int quantity, String binLocation,
                  String expiryDate, boolean isSpicy, String pattyType) {
        super(name, quantity, binLocation, expiryDate);
        this.isSpicy = isSpicy;
        this.pattyType = pattyType;
    }

    public boolean isSpicy() {
        return isSpicy;
    }

    public void setSpicy(boolean isSpicy) {
        this.isSpicy = isSpicy;
    }

    public String getPattyType() {
        return pattyType;
    }

    public void setPattyType(String pattyType) {
        this.pattyType = pattyType;
    }

    @Override
    public String toString() {
        return "[Burger] " + super.toString()
                + ", Spicy: " + isSpicy
                + ", Patty Type: " + pattyType;
    }
}
