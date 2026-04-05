package seedu.inventorydock.model.items;

import seedu.inventorydock.model.Item;

/**
 * Represents a pet food item in the inventory.
 */
public class PetFood extends Item {
    private String petType;
    private String brand;
    private boolean isDryFood;

    /**
     * Creates a pet food item with the given details.
     *
     * @param name Name of the pet food item.
     * @param quantity Quantity of the item.
     * @param binLocation Storage bin location.
     * @param expiryDate Expiry date.
     * @param petType Type of pet this food is for.
     * @param brand Brand of the pet food.
     * @param isDryFood Check whether food is considered dry food
     */
    public PetFood(String name, int quantity, String binLocation,
                   String expiryDate, String petType,
                   String brand, boolean isDryFood) {
        super(name, quantity, binLocation, expiryDate);
        this.petType = petType;
        this.brand = brand;
        this.isDryFood = isDryFood;
    }

    /** @return Type of pet this food is for. */
    public String getPetType() {
        return petType;
    }

    /** @param petType New pet type. */
    public void setPetType(String petType) {
        this.petType = petType;
    }

    /** @return Brand of the pet food. */
    public String getBrand() {
        return brand;
    }

    /** @param brand New pet food brand. */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Returns whether this item is dry food.
     *
     * @return {@code true} if the item is dry food, {@code false} otherwise.
     */
    public boolean isDryFood() {
        return isDryFood;
    }

    /**
     * Converts this pet food item into a storage-friendly string format.
     *
     * @param categoryName Name of the category this item belongs to.
     * @return Storage string representation.
     */
    @Override
    public String toStorageString(String categoryName) {
        return super.toStorageString(categoryName)
                + " petType/" + petType
                + " brand/" + brand
                + " isDryFood/" + isDryFood;
    }

    /**
     * Returns a string representation of this pet food item.
     *
     * @return Formatted pet food details.
     */
    @Override
    public String toString() {
        return "[PetFood] " + super.toString()
                + ", Pet Type: " + petType
                + ", Brand: " + brand
                + ", Dry Food: " + isDryFood;
    }
}
