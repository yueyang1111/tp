package seedu.duke.command;

import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.model.items.Fruit;
import seedu.duke.model.items.Snack;
import seedu.duke.model.items.Toiletries;
import seedu.duke.model.items.Vegetable;
import seedu.duke.ui.UI;

public class AddItemCommand extends Command {
    private final String itemName;
    private final String categoryName;
    private final String bin;
    private final int quantity;
    private final String brand;
    private final String expiryDate;
    private final String size;
    private final boolean isRipe;
    private final boolean isLeafy;
    private final boolean isLiquid;

    public AddItemCommand(String itemName, String categoryName, String bin,
                          int quantity, String brand, String expiryDate,
                          String size, boolean isRipe, boolean isLeafy,
                          boolean isLiquid) {
        this.itemName = itemName;
        this.categoryName = categoryName;
        this.bin = bin;
        this.quantity = quantity;
        this.brand = brand;
        this.expiryDate = expiryDate;
        this.size = size;
        this.isRipe = isRipe;
        this.isLeafy = isLeafy;
        this.isLiquid = isLiquid;
    }

    @Override
    public void execute(Inventory inventory, UI ui) {
        Category category = inventory.findCategoryByName(categoryName);

        if (category == null) {
            ui.showCategoryNotFound(categoryName);
            return;
        }

        Item item = createItemByCategory(categoryName);

        if (item == null) {
            ui.showUnsupportedCategory(categoryName);
            return;
        }

        category.addItem(item);
        ui.showItemAdded(itemName, quantity, category.getName(), bin);
    }

    private Item createItemByCategory(String categoryName) {
        switch (categoryName.toLowerCase()) {
            case "fruits":
                return new Fruit(itemName, quantity, bin, expiryDate, size, isRipe);
            case "vegetables":
                return new Vegetable(itemName, quantity, bin, expiryDate, isLeafy);
            case "toiletries":
                return new Toiletries(itemName, quantity, bin, brand, isLiquid);
            case "snacks":
                return new Snack(itemName, quantity, bin, brand, expiryDate);
            default:
                return null;
        }
    }
}