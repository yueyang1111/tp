package seedu.duke.model.items;

import seedu.duke.model.Item;

/**
 * Represents an ice cream item in the inventory.
 */
public class IceCream extends Item {
    private String flavour;
    private boolean isDairyFree;

    /**
     * Creates an ice cream item with the given details.
     *
     * @param name Name of the ice cream.
     * @param quantity Quantity of the ice cream.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param flavour Flavour of the ice cream.
     * @param isDairyFree Check whether the ice cream is dairy-free.
     */
    public IceCream(String name, int quantity, String binLocation,
                    String expiryDate, String flavour, boolean isDairyFree) {
        super(name, quantity, binLocation, expiryDate);
        this.flavour = flavour;
        this.isDairyFree = isDairyFree;
    }

    /** @return Flavour of the ice cream. */
    public String getFlavour() {
        return flavour;
    }

    /** @param flavour New ice cream flavour. */
    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    /** @return {@code true} if the ice cream is dairy-free. */
    public boolean isDairyFree() {
        return isDairyFree;
    }

    /** @param isDairyFree New dairy-free status. */
    public void setDairyFree(boolean isDairyFree) {
        this.isDairyFree = isDairyFree;
    }

    /**
     * Converts this ice cream into a storage-friendly string format.
     *
     * @param categoryName Name of the category this item belongs to.
     * @return Storage string representation.
     */
    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " flavour/" + flavour
                + " isDairyFree/" + isDairyFree;
    }

    /**
     * Returns a string representation of this ice cream.
     *
     * @return Formatted ice cream details.
     */
    @Override
    public String toString() {
        return "[IceCream] " + super.toString()
                + ", Flavour: " + flavour
                + ", DairyFree: " + isDairyFree;
    }
}
