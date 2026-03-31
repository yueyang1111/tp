package seedu.duke.model.items;

import seedu.duke.model.Item;

public class PetFood extends Item {
    private String petType;
    private String brand;
    private boolean isDryFood;

    public PetFood(String name, int quantity, String binLocation,
                   String expiryDate, String petType,
                   String brand, boolean isDryFood) {
        super(name, quantity, binLocation, expiryDate);
        this.petType = petType;
        this.brand = brand;
        this.isDryFood = isDryFood;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isDryFood() {
        return isDryFood;
    }

    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " petType/" + petType
                + " brand/" + brand
                + " isDryFood/" + isDryFood;
    }

    @Override
    public String toString() {
        return "[PetFood] " + super.toString()
                + ", Pet Type: " + petType
                + ", Brand: " + brand
                + ", Dry Food: " + isDryFood;
    }
}
