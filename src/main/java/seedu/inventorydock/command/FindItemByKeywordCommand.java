package seedu.inventorydock.command;

import seedu.inventorydock.exception.DukeException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.ui.UI;

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
            String noItemsMessage = ui.formatNoItemsFoundMessage("matching keyword: " + keywordInput);
            logger.log(Level.INFO, noItemsMessage);
            ui.showMessage(noItemsMessage);
            return;
        }

        logger.log(Level.INFO, ui.formatFoundItemsMessage(matches.size(), "matching keyword '" + keywordInput + "'"));

        ui.showDivider();
        ui.showMessage(ui.formatFindResultsHeader("matching keyword '" + keywordInput + "':"));
        ui.showNumberedList(matches);
        ui.showDivider();
    }
}
