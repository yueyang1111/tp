package seedu.duke.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private final List<Item> items;

    public Category(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item getItem(int index) {
        return items.get(index);
    }

    public int getItemCount() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void removeItem(int index) {
        items.remove(index);
    }

    public Item findItemByName(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category: ").append(name).append("\n");
        for (int i = 0; i < items.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(items.get(i)).append("\n");
        }
        return sb.toString();
    }
}
