package seedu.duke.model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Category> inventory;

    public Inventory() {
        inventory = new ArrayList<>();
    }

    public void addCategory(Category category) {
        inventory.add(category);
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

    public void printInventory() {
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
