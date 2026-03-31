package seedu.duke.model.items;

import seedu.duke.model.Item;

/**
 * Represents a fruit item in the inventory.
 */
public class Fruit extends Item {
    private String size;
    private boolean isRipe;

    /**
     * Creates a fruit item with the given details.
     *
     * @param name Name of the fruit.
     * @param quantity Quantity of the fruit.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param size Size of the fruit.
     * @param isRipe Check whether the fruit is ripe.
     */
    public Fruit(String name, int quantity, String binLocation,
                 String expiryDate, String size, boolean isRipe) {
        super(name,quantity,binLocation,expiryDate);
        this.size = size;
        this.isRipe = isRipe;
    }

    /** @return Size of the fruit. */
    public String getSize() {
        return size;
    }

    /** @param size New fruit size. */
    public void setSize(String size) {
        this.size = size;
    }

    /** @return {@code true} if the fruit is ripe. */
    public boolean isRipe() {
        return isRipe;
    }

    /** @param isRipe New ripe status. */
    public void setRipe(boolean isRipe) {
        this.isRipe = isRipe;
    }

    /**
     * Converts this fruit into a storage-friendly string format.
     *
     * @param categoryName Name of the category this item belongs to.
     * @return Storage string representation.
     */
    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " size/" + size
                + " isRipe/" + isRipe;
    }

    /**
     * Returns a string representation of this fruit.
     *
     * @return Formatted fruit details.
     */
    @Override
    public String toString() {
        return "[Fruit] " + super.toString()
                + ", Size: " + size
                + ", Ripe: " + isRipe;
    }
}
