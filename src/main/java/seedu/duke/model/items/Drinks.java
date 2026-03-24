package seedu.duke.model.items;

import seedu.duke.model.Item;

public class Drinks extends Item {
    private String brand;
    private String flavour;
    private int volume;


    public Drinks(String name, int quantity, String binLocation, String expiryDate,
                  String brand, String flavour) {
        super(name, quantity, binLocation, expiryDate);
        this.brand = brand;
        this.flavour = flavour;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }


    @Override
    public String toString() {
        return "[Drinks] " + super.toString()
                + ", Brand: " + brand
                + ", Flavour: " + flavour;
    }
}
