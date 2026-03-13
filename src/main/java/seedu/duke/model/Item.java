package seedu.duke.model;

public class Item {
    private String name;
    private int quantity;
    private String binLocation;

    public Item(String name, int quantity, String binLocation) {
        this.name = name;
        this.quantity = quantity;
        this.binLocation = binLocation;
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

    @Override
    public String toString() {
        return "Name: " + name + ", Quantity: " + quantity + ", Bin: " + binLocation;
    }
}
