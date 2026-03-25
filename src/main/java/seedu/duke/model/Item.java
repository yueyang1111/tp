package seedu.duke.model;

public class Item {
    private String name;
    private int quantity;
    private String binLocation;
    private String expiryDate;

    public Item(String name, int quantity, String binLocation,
                String expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.binLocation = binLocation;
        this.expiryDate = expiryDate;
    }
    // getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBinLocation() {
        return binLocation;
    }

    public void setBinLocation(String binLocation) {
        this.binLocation = binLocation;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

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
