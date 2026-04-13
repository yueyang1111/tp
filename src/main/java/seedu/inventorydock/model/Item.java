package seedu.inventorydock.model;


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
        
    }
}
