package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents a drink item in the inventory.
 */
public class Drinks extends Item {
    private String brand;
    private String flavour;
    private boolean isCarbonated;

    /**
     * Creates a drink item with the given details.
     *
     * @param name Name of the drink.
     * @param quantity Quantity of the drink.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param brand Brand of the drink.
     * @param flavour Flavour of the drink.
     * @param isCarbonated Check if the drink is carbonated
     */
    public Drinks(String name, int quantity, String binLocation, String expiryDate,
                  String brand, String flavour, boolean isCarbonated) {
        super(name, quantity, binLocation, expiryDate);
        this.brand = brand;
        this.flavour = flavour;
        this.isCarbonated = isCarbonated;
    }

    /** @return Brand of the drink. */
    public String getBrand() {
        return brand;
    }

    /** @param brand New drink brand. */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /** @return Flavour of the drink. */
    public String getFlavour() {
        return flavour;
    }

    /** @param flavour New drink flavour. */
    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    /**
     * Returns whether this item is carbonated.
     *
     * @return {@code true} if the item is carbonated, {@code false} otherwise.
     */
    public boolean isCarbonated() {
        return isCarbonated;
    }

    /**
     * Converts this drink into a storage-friendly string format.
     *
     * @param categoryName Name of the category this item belongs to.
     * @return Storage string representation.
     */
    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " brand/" + brand
                + " flavour/" + flavour
                + " isCarbonated/" + isCarbonated;
    }

    /**
     * Returns a string representation of this drink.
     *
     * @return Formatted drink details.
     */
    @Override
    public String toString() {
        return "[Drinks] " + super.toString()
                + ", Brand: " + brand
                + ", Flavour: " + flavour
                + ", Carbonated: " + isCarbonated;
    }
}
