package seedu.duke.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents an item stored in the inventory.
 * An <code>Item</code> object stores a name, quantity, bin location, and expiry date.
 */
public class Item {
    private static final Logger logger = Logger.getLogger(Item.class.getName());
    private String name;
    private int quantity;
    private String binLocation;
    private String expiryDate;

    /**
     * Creates an item with the specified details.
     *
     * @param name Name of the item.
     * @param quantity Quantity of the item.
     * @param binLocation Bin location of the item.
     * @param expiryDate Expiry date of the item.
     */
    public Item(String name, int quantity, String binLocation,
                String expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.binLocation = binLocation;
        this.expiryDate = expiryDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        logger.log(Level.INFO, "Updating item name from '" + this.name + "' to '" + name + "'.");
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        logger.log(Level.INFO, "Updating quantity for item '" + name + "' from "
                + this.quantity + " to " + quantity + ".");
        this.quantity = quantity;
    }

    public String getBinLocation() {
        return binLocation;
    }

    public void setBinLocation(String binLocation) {
        logger.log(Level.INFO, "Updating bin location for item '" + name + "' from '"
                + this.binLocation + "' to '" + binLocation + "'.");
        this.binLocation = binLocation;
    }

    public void setExpiryDate(String expiryDate) {
        logger.log(Level.INFO, "Updating expiry date for item '" + name + "' from '"
                + this.expiryDate + "' to '" + expiryDate + "'.");
        this.expiryDate = expiryDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * Returns the storage format used when persisting the item.
     *
     * @param categoryName Name of the category containing the item.
     * @return Storage string containing the category and item fields.
     */
    public String toStorageString(String categoryName) {
        return "category/" + categoryName
                + " item/" + name
                + " bin/" + binLocation
                + " qty/" + quantity
                + " expiryDate/" + expiryDate;
    }

    @Override
    public String toString() {
        return String.format(
                "Name: %s, Quantity: %d, Bin: %s, Expiry: %s",
                name, quantity, binLocation, expiryDate
        );
    }
}
