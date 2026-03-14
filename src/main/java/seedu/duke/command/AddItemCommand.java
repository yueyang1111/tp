package seedu.duke.command;

import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.ui.UI;

public class AddItemCommand extends Command {
    private final String categoryName;
    private final Item item;

    public AddItemCommand(String categoryName, Item item) {
        this.categoryName = categoryName;
        this.item = item;
    }

    @Override
    public void execute(Inventory inventory, UI ui) throws DukeException {
        Category category = inventory.findCategoryByName(categoryName);

        if (category == null) {
            throw new DukeException("Category not found: " + categoryName);
        }

        if (item == null) {
            throw new DukeException("Item cannot be null.");
        }

        category.addItem(item);
        ui.showItemAdded(item.getName(), item.getQuantity(),
                category.getName(), item.getBinLocation());
    }
}
