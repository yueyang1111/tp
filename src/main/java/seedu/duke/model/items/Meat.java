package seedu.duke.model.items;

import seedu.duke.model.Item;

/**
 * Represents a meat item in the inventory.
 */
public class Meat extends Item {
    private String meatType;
    private String origin;
    private boolean isFrozen;

    /**
     * Creates a meat item with the given details.
     *
     * @param name Name of the meat item.
     * @param quantity Quantity of the item.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param meatType Type of meat.
     * @param origin Origin of the meat.
     * @param isFrozen Check the frozen state of meat
     */
    public Meat(String name, int quantity, String binLocation,
                String expiryDate, String meatType,
                String origin, boolean isFrozen) {
        super(name, quantity, binLocation, expiryDate);
        this.meatType = meatType;
        this.origin = origin;
        this.isFrozen = isFrozen;
    }

    /** @return Origin of the meat. */
    public String getOrigin() {
        return origin;
    }

    /** @param origin New origin value. */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /** @return Type of meat. */
    public String getMeatType() {
        return meatType;
    }

    /** @param meatType New meat type. */
    public void setMeatType(String meatType) {
        this.meatType = meatType;
    }

    /**
     * Returns whether this item is frozen.
     *
     * @return {@code true} if the item is frozen, {@code false} otherwise.
     */
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * Converts this meat item into a storage-friendly string format.
     *
     * @param categoryName Name of the category this item belongs to.
     * @return Storage string representation.
     */
    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " meatType/" + meatType
                + " origin/" + origin
                + " isFrozen/" + isFrozen;
    }

    /**
     * Returns a string representation of this meat item.
     *
     * @return Formatted meat details.
     */
    @Override
    public String toString() {
        return "[Meat] " + super.toString()
                + ", Meat Type: " + meatType
                + ", Origin: " + origin
                + ", Frozen: " + isFrozen;
    }
}
