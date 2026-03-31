package seedu.duke.command;

import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.ui.UI;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Finds and displays all items belonging to a specific category.
 */
public class FindItemByCategoryCommand extends Command {
    private static final Logger logger = Logger.getLogger(FindItemByCategoryCommand.class.getName());
    private final String categoryInput;

    /**
     * Creates a command that searches for items in one category.
     *
     * @param categoryInput category name supplied by the user.
     */
    public FindItemByCategoryCommand(String categoryInput) {
        this.categoryInput = categoryInput;
    }

    /**
     * Locates the requested category and displays either its items or an appropriate message.
     *
     * @param inventory inventory to search.
     * @param ui user interface used to display search results.
     * @throws DukeException included for command interface compatibility.
     */
    @Override
    public void execute(Inventory inventory, UI ui) throws DukeException {
        assert inventory != null : "FindItemByCategoryCommand received null inventory.";
        assert ui != null : "FindItemByCategoryCommand received null UI.";
        assert categoryInput != null : "FindItemByCategoryCommand received null category input.";
        Category matched = inventory.findCategoryByName(categoryInput);

        if (matched == null) {
            logger.log(Level.WARNING, "Category not found: " + categoryInput);
            ui.showCategoryNotFound(categoryInput);
            return;
        }

        List<Item> items = matched.getItems();

        if (items.isEmpty()) {
            logger.log(Level.INFO, "No items found in category: " + categoryInput);
            ui.showMessage("No items found in category: " + matched.getName() + ".");
            return;
        }

        logger.log(Level.INFO, "Found " + items.size()
                + " item(s) in category '" + matched.getName() + "'.");

        ui.showDivider();
        ui.showMessage("Items in category: " + matched.getName());
        ui.showNumberedList(items);
        ui.showDivider();
    }
}
