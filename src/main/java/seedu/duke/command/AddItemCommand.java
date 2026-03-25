package seedu.duke.command;

import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.ui.UI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddItemCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddItemCommand.class.getName());

    private final String categoryName;
    private final Item item;

    public AddItemCommand(String categoryName, Item item) {
        assert categoryName != null : "AddItemCommand received null category name.";
        this.categoryName = categoryName;
        this.item = item;
    }

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
