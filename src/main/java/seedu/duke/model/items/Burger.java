package seedu.duke.model.items;

import seedu.duke.model.Item;

/**
 * Represents a burger item in the inventory.
 */
public class Burger extends Item {
    private boolean isSpicy;
    private String pattyType;

    /**
     * Creates a burger item with the given details.
     *
     * @param name Name of the burger.
     * @param quantity Quantity of the burger.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param isSpicy Whether the burger is spicy.
     * @param pattyType Type of patty used.
     */
    public Burger(String name, int quantity, String binLocation,
                  String expiryDate, boolean isSpicy, String pattyType) {
        super(name, quantity, binLocation, expiryDate);
        this.isSpicy = isSpicy;
        this.pattyType = pattyType;
    }

    /** @return {@code true} if the burger is spicy. */
    public boolean isSpicy() {
        return isSpicy;
    }

    /** @param isSpicy New spicy status. */
    public void setSpicy(boolean isSpicy) {
        this.isSpicy = isSpicy;
    }

    /** @return Patty type of the burger. */
    public String getPattyType() {
        return pattyType;
    }

    /** @param pattyType New patty type. */
    public void setPattyType(String pattyType) {
        this.pattyType = pattyType;
    }

    /**
     * Converts this burger into a storage-friendly string format.
     *
     * @param categoryName Name of the category this item belongs to.
     * @return Storage string representation.
     */
    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " isSpicy/" + isSpicy
                + " pattyType/" + pattyType;
    }

    /**
     * Returns a string representation of this burger.
     *
     * @return Formatted burger details.
     */
    @Override
    public String toString() {
        return "[Burger] " + super.toString()
                + ", Spicy: " + isSpicy
                + ", Patty Type: " + pattyType;
    }
}
