package seedu.duke.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the inventory as a collection of categories.
 * An <code>Inventory</code> object stores and looks up categories by name.
 */
public class Inventory {
    private static final Logger logger = Logger.getLogger(Inventory.class.getName());
    private List<Category> inventory;

    /**
     * Creates an empty inventory.
     */
    public Inventory() {
        inventory = new ArrayList<>();
    }

    public void addCategory(Category category) {
        inventory.add(category);
        logger.log(Level.INFO, "Added category to inventory: " + category.getName());
    }

    public List<Category> getCategories() {
        return inventory;
    }

    public Category getCategory(int index) {
        return inventory.get(index);
    }

    public int getCategoryCount() {
        return inventory.size();
    }

    /**
     * Returns the category whose name matches the specified name, ignoring case.
     *
     * @param categoryName Name of the category to find.
     * @return Matching category, or {@code null} if no category matches.
     */
    public Category findCategoryByName(String categoryName) {
        for (Category category : inventory) {
            if (category.getName().equalsIgnoreCase(categoryName)) {
                return category;
            }
        }
        return null;
    }

    public boolean hasCategory(String categoryName) {
        return findCategoryByName(categoryName) != null;
    }

    /**
     * Prints each category in the inventory with a one-based index.
     */
    public void printInventory() {
        logger.log(Level.INFO, "Printing inventory with " + inventory.size() + " categories.");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + "." + inventory.get(i));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Category category : inventory) {
            sb.append(category).append("\n");
        }
        return sb.toString();
    }
}
