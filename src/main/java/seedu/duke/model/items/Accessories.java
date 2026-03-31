package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Accessories extends Item {
    private String type;
    private String material;
    private boolean isFragile;

    public Accessories(String name, int quantity, String binLocation,
                       String expiryDate, String type, String material, boolean isFragile) {
        super(name, quantity, binLocation, expiryDate);
        this.type = type;
        this.material = material;
        this.isFragile = isFragile;
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

    public boolean isFragile() {
        return isFragile;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " type/" + type
                + " material/" + material
                + " isFragile/" + isFragile;
    }

    @Override
    public String toString() {
        return "[Accessories] " + super.toString()
                + ", Type: " + type
                + ", Material: " + material
                + ", Fragile: " +isFragile;
    }
}
