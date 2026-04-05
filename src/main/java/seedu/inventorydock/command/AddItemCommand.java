package seedu.inventorydock.command;

import seedu.inventorydock.exception.DukeException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.ui.UI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adds a parsed item into the requested category and reports the result through the UI.
 */
public class AddItemCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddItemCommand.class.getName());

    private final String categoryName;
    private final Item item;

    /**
     * Creates a command that adds an item to an existing category.
     *
     * @param categoryName target category name.
     * @param item item to be inserted.
     */
    public AddItemCommand(String categoryName, Item item) {
        assert categoryName != null : "AddItemCommand received null category name.";
        this.categoryName = categoryName;
        this.item = item;
    }

    /**
     * Finds the target category in the inventory, adds the item, and shows a confirmation message.
     *
     * @param inventory inventory to mutate.
     * @param ui user interface used to display the result.
     * @throws DukeException if the category does not exist or the item is null.
     */
    @Override
    public void execute(Inventory inventory, UI ui) throws DukeException {
        assert inventory != null : "AddItemCommand received null inventory.";
        Category category = inventory.findCategoryByName(categoryName);

        if (category == null) {
            logger.log(Level.WARNING, "Category not found while adding item: " + categoryName);
            throw new DukeException("Category not found: " + categoryName);
        }

        if (item == null) {
            logger.log(Level.WARNING, "Null item supplied to AddItemCommand.");
            throw new DukeException("Item cannot be null.");
        }

        category.addItem(item);
        logger.log(Level.INFO, "Added item '" + item.getName()
                + "' to category '" + category.getName() + "'.");
        if (ui != null) {
            ui.showItemAdded(item.getName(), item.getQuantity(),
                    category.getName(), item.getBinLocation());
        }
    }
}
