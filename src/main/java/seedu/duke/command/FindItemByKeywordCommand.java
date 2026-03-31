package seedu.duke.command;

import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.ui.UI;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to find items across all categories
 * whose names contain the specified keyword.
 * The search is case-insensitive.
 */
public class FindItemByKeywordCommand extends Command {
    private static final Logger logger = Logger.getLogger(FindItemByKeywordCommand.class.getName());
    private final String keywordInput;

    /**
     * Constructs a FindItemByKeywordCommand with the given
     * keyword.
     *
     * @param keywordInput The keyword to search for in item
     *                     names.
     */
    public FindItemByKeywordCommand(String keywordInput) {
        this.keywordInput = keywordInput;
    }

    /**
     * Executes the find-by-keyword command by searching all
     * categories for items whose names contain the keyword.
     *
     * @param inventory The inventory to search through.
     * @param ui        The UI to display results to the user.
     * @throws DukeException If an error occurs during
     *                       execution.
     */
    @Override
    public void execute(Inventory inventory, UI ui) throws DukeException {
        assert inventory != null : "FindItemByKeywordCommand received null inventory.";
        assert ui != null : "FindItemByKeywordCommand received null UI.";
        assert keywordInput != null : "FindItemByKeywordCommand received null keyword input.";

        List<String> matches = new ArrayList<>();
        List<Category> categories = inventory.getCategories();

        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getName().toLowerCase().contains(keywordInput.toLowerCase())) {
                    matches.add(category.getName() + ": " + item);
                }
            }
        }

        if (matches.isEmpty()) {
            logger.log(Level.INFO, "No items found matching keyword: " + keywordInput);
            ui.showMessage("No items found matching keyword: " + keywordInput + ".");
            return;
        }

        logger.log(Level.INFO, "Found " + matches.size() + " item(s) matching keyword '" + keywordInput + "'.");

        ui.showDivider();
        ui.showMessage("Items matching keyword '" + keywordInput + "':");
        ui.showNumberedList(matches);
        ui.showDivider();
    }
}


