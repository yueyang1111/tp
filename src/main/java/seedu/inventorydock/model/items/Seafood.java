package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents a seafood item in the inventory.
 */
public class Seafood extends Item {
    private String seafoodType;
    private String origin;
    private boolean isFrozen;

    /**
     * Creates a seafood item with the given details.
     *
     * @param name Name of the seafood item.
     * @param quantity Quantity of the item.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param seafoodType Type of seafood.
     * @param origin Origin of the seafood.
     * @param isFrozen Check whether the food is frozen
     */
    public Seafood(String name, int quantity, String binLocation,
                   String expiryDate, String seafoodType,
                   String origin, boolean isFrozen) {
        super(name, quantity, binLocation, expiryDate);
        this.seafoodType = seafoodType;
        this.origin = origin;
        this.isFrozen = isFrozen;
    }

    /** @return Origin of the seafood. */
    public String getOrigin() {
        return origin;
    }

    /** @param origin New seafood origin. */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /** @return Type of seafood. */
    public String getSeafoodType() {
        return seafoodType;
    }

    /** @param seafoodType New seafood type. */
    public void setSeafoodType(String seafoodType) {
        this.seafoodType = seafoodType;
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
     * Converts this seafood item into a storage-friendly string format.
     *
     * @param categoryName Name of the category this item belongs to.
     * @return Storage string representation.
     */
    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " seafoodType/" + seafoodType
                + " origin/" + origin
                + " isFrozen/" + isFrozen;
    }

    /**
     * Returns a string representation of this seafood item.
     *
     * @return Formatted seafood details.
     */
    @Override
    public String toString() {
        return "[Seafood] " + super.toString()
                + ", Seafood Type: " + seafoodType
                + ", Origin: " + origin
                + ", Frozen: " + isFrozen;
    }
}
