package seedu.duke.model.items;

import seedu.duke.model.Item;

/**
 * Represents a toiletries item in the inventory.
 */
public class Toiletries extends Item {
    private String brand;
    private boolean isLiquid;

    /**
     * Creates a toiletries item with the given details.
     *
     * @param name Name of the toiletries item.
     * @param quantity Quantity of the item.
     * @param binLocation Storage bin location.
     * @param brand Brand of the item.
     * @param isLiquid Whether the item is liquid.
     * @param expiryDate Expiry date.
     */
    public Toiletries(String name, int quantity, String binLocation, String brand,
                      boolean isLiquid, String expiryDate) {
        super(name,quantity,binLocation,expiryDate);
        this.brand = brand;
        this.isLiquid = isLiquid;
    }

    /** @return Brand of the toiletries item. */
    public String getBrand() {
        return brand;
    }

    /** @return {@code true} if the item is liquid. */
    public boolean isLiquid() {
        return isLiquid;
    }

    /** @param brand New toiletries brand. */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /** @param isLiquid New liquid status. */
    public void setLiquid(boolean isLiquid) {
        this.isLiquid = isLiquid;
    }

    /**
     * Converts this toiletries item into a storage-friendly string format.
     *
     * @param categoryName Name of the category this item belongs to.
     * @return Storage string representation.
     */
    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " brand/" + brand
                + " isLiquid/" + isLiquid;
    }

    /**
     * Returns a string representation of this toiletries item.
     *
     * @return Formatted toiletries details.
     */
    @Override
    public String toString() {
        return "[Toiletries] " + super.toString()
                + ", Brand: " + brand
                + ", Liquid: " + isLiquid;
    }
}
