package seedu.inventorydock.command;

import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.ui.UI;

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
     * Parses and validates a category input supplied to the category find command.
     *
     * @param categoryInput raw category input.
     * @return normalized category input.
     * @throws MissingArgumentException if the category is empty.
     * @throws InvalidCommandException if the category is numeric.
     */
    public static String parseCategoryInput(String categoryInput)
            throws MissingArgumentException, InvalidCommandException {
        String trimmedCategory = categoryInput.trim();
        if (trimmedCategory.isEmpty()) {
            logger.log(Level.WARNING, "Empty category input received.");
            throw new MissingArgumentException("Category cannot be empty.");
        }
        try {
            Integer.parseInt(trimmedCategory);
            logger.log(Level.WARNING, "Numeric category input received: " + trimmedCategory);
            throw new InvalidCommandException("Category must be a string.");
        } catch (NumberFormatException e) {
            assert !trimmedCategory.isEmpty() : "FindItemByCategoryCommand parsed empty category input.";
            return trimmedCategory.toLowerCase();
        }
    }

    /**
     * Locates the requested category and displays either its items or an appropriate message.
     *
     * @param inventory inventory to search.
     * @param ui user interface used to display search results.
     */
    @Override
    public void execute(Inventory inventory, UI ui) {
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
            String noItemsMessage = ui.formatNoItemsFoundMessage("in category: " + matched.getName());
            logger.log(Level.INFO, noItemsMessage);
            ui.showMessage(noItemsMessage);
            return;
        }

        logger.log(Level.INFO, ui.formatFoundItemsMessage(items.size(), "in category '" + matched.getName() + "'"));

        ui.showDivider();
        ui.showMessage(ui.formatFindResultsHeader("in category: " + matched.getName()));
        ui.showNumberedList(items);
        ui.showDivider();
    }
}
