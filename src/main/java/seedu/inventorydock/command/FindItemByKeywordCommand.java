package seedu.inventorydock.command;

import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.ui.UI;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to find items across all categories
 * whose names contain the specified keyword.
 * The search is case-insensitive and supports partial matches.
 * Results are grouped by category.
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
     * Results are grouped by category for easier reading.
     *
     * @param inventory The inventory to search through.
     * @param ui        The UI to display results to the user.
     */
    @Override
    public void execute(Inventory inventory, UI ui) {
        assert inventory != null : "FindItemByKeywordCommand received null inventory.";
        assert ui != null : "FindItemByKeywordCommand received null UI.";
        assert keywordInput != null : "FindItemByKeywordCommand received null keyword input.";

        if (keywordInput.contains(" ")) {
            ui.showError("Invalid input", "keyword/ accepts only a single keyword.");
            return;
        }

        Map<String, List<String>> matchesByCategory = new LinkedHashMap<>();
        int totalMatches = 0;
        List<Category> categories = inventory.getCategories();

        for (Category category : categories) {
            List<String> categoryMatches = new ArrayList<>();
            for (Item item : category.getItems()) {
                if (item.getName().toLowerCase().contains(keywordInput.toLowerCase())) {
                    categoryMatches.add(item.toString());
                }
            }
            if (!categoryMatches.isEmpty()) {
                matchesByCategory.put(category.getName(), categoryMatches);
                totalMatches += categoryMatches.size();
            }
        }

        if (totalMatches == 0) {
            String noItemsMessage = ui.formatNoItemsFoundMessage("matching keyword: " + keywordInput);
            logger.log(Level.INFO, noItemsMessage);
            ui.showMessage(noItemsMessage);
            ui.showDivider();
            return;
        }

        logger.log(Level.INFO, ui.formatFoundItemsMessage(totalMatches,
                "matching keyword '" + keywordInput + "'"));

        ui.showDivider();
        ui.showMessage(ui.formatFoundItemsMessage(totalMatches,
                "matching keyword '" + keywordInput + "'"));
        ui.showMessage("");

        for (Map.Entry<String, List<String>> entry : matchesByCategory.entrySet()) {
            String categoryName = entry.getKey();
            List<String> items = entry.getValue();
            ui.showMessage(categoryName + " (" + items.size() + "):");
            for (int i = 0; i < items.size(); i++) {
                ui.showMessage("  " + (i + 1) + ". " + items.get(i));
            }
        }

        ui.showDivider();
    }
}
