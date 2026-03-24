package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Seafood extends Item {
    private String seafoodType;
    private String origin;

    public Seafood(String name, int quantity, String binLocation,
                   String expiryDate, String seafoodType, String origin) {
        super(name, quantity, binLocation, expiryDate);
        this.seafoodType = seafoodType;
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSeafoodType() {
        return seafoodType;
    }

    public void setSeafoodType(String seafoodType) {
        this.seafoodType = seafoodType;
    }

    @Override
    public String toString() {
        return "[Seafood] " + super.toString()
                + ", Seafood Type: " + seafoodType
                + ", Origin: " + origin;
    }
}
