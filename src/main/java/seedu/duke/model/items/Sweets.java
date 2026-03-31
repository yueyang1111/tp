package seedu.duke.model.items;

import seedu.duke.model.Item;

/**
 * Represents a sweets item in the inventory.
 */
public class Sweets extends Item {
    private String brand;
    private String sweetnessLevel;
    private boolean isChewy;

    /**
     * Creates a sweets item with the given details.
     *
     * @param name Name of the sweets item.
     * @param quantity Quantity of the item.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param brand Brand of the sweets.
     * @param sweetnessLevel Sweetness level of the item.
     * @param isChewy Check item checy
     */
    public Sweets(String name, int quantity, String binLocation,
                  String expiryDate, String brand,
                  String sweetnessLevel, boolean isChewy) {
        super(name, quantity, binLocation, expiryDate);
        this.brand = brand;
        this.sweetnessLevel = sweetnessLevel;
        this.isChewy = isChewy;
    }

    /** @return Brand of the sweets item. */
    public String getBrand() {
        return brand;
    }

    /** @param brand New sweets brand. */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /** @return Sweetness level of the item. */
    public String getSweetnessLevel() {
        return sweetnessLevel;
    }

    /** @param sweetnessLevel New sweetness level. */
    public void setSweetnessLevel(String sweetnessLevel) {
        this.sweetnessLevel = sweetnessLevel;
    }

    /**
     * Returns whether this item is chewy.
     *
     * @return {@code true} if the item is chewy, {@code false} otherwise.
     */
    public boolean isChewy() {
        return isChewy;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " brand/" + brand
                + " sweetnessLevel/" + sweetnessLevel
                + " isChewy/" + isChewy;
    }

    @Override
    public String toString() {
        return "[Sweets] " + super.toString()
                + ", Brand: " + brand
                + ", Sweetness Level: " + sweetnessLevel
                + ", Chewy: " + isChewy;
    }
}
