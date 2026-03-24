package seedu.duke.model.items;

import seedu.duke.model.Item;

public class PetFood extends Item {
    private String petType;
    private String brand;

    public PetFood(String name, int quantity, String binLocation,
                   String expiryDate, String petType, String brand) {
        super(name, quantity, binLocation, expiryDate);
        this.petType = petType;
        this.brand = brand;
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

    @Override
    public String toString() {
        return "[PetFood] " + super.toString()
                + ", Pet Type: " + petType
                + ", Brand: " + brand;
    }
}
