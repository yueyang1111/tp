package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Accessories extends Item {
    private String type;
    private String material;

    public Accessories(String name, int quantity, String binLocation,
                       String expiryDate, String type, String material) {
        super(name, quantity, binLocation, expiryDate);
        this.type = type;
        this.material = material;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "[Accessories] " + super.toString()
                + ", Type: " + type
                + ", Material: " + material;
    }
}
