package seedu.duke.command;

import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.ui.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to delete an item from a specified
 * category by its index.
 */
public class DeleteItemCommand extends Command {
    private static final Logger logger = Logger.getLogger(DeleteItemCommand.class.getName());

    private final String categoryName;
    private final int itemIndex;

    /**
     * Constructs a DeleteItemCommand with the given category
     * name and 1-based item index.
     *
     * @param categoryName The name of the category containing
     *                     the item.
     * @param itemIndex    The 1-based index of the item to
     *                     delete.
     */
    public DeleteItemCommand(String categoryName,
                             int itemIndex) {
        this.categoryName = categoryName;
        this.itemIndex = itemIndex;
    }

    /**
     * Executes the delete item command by looking up the
     * category and removing the item at the specified index.
     *
     * @param inventory The inventory to delete the item from.
     * @param ui        The UI to display messages to the user.
     */
    @Override
    public void execute(Inventory inventory, UI ui) {
        assert inventory != null : "DeleteItemCommand received null inventory.";
        assert ui != null : "DeleteItemCommand received null UI.";
        assert categoryName != null : "DeleteItemCommand received null category name.";

        Category category = inventory.findCategoryByName(categoryName);

        if (category == null) {
            logger.log(Level.WARNING, "Category not found for deletion: " + categoryName);
            ui.showCategoryNotFound(categoryName);
            return;
        }

        if (itemIndex < 1 || itemIndex > category.getItemCount()) {
            logger.log(Level.WARNING, "Invalid item index: " + itemIndex);
            ui.showError("Invalid item index: " + itemIndex + ". Category '" + categoryName
                    + "' has " + category.getItemCount()
                    + " item(s).");
            return;
        }

        Item item = category.getItem(itemIndex - 1);
        category.removeItem(itemIndex - 1);
        logger.log(Level.INFO, "Deleted item '" + item.getName() + "' from category '"
                + category.getName() + "'.");
        ui.showItemDeleted(item.getName(), category.getName());
    }
}
