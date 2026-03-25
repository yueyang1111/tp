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

public class FindItemByKeywordCommand extends Command {
    private static final Logger logger = Logger.getLogger(FindItemByKeywordCommand.class.getName());
    private final String keywordInput;

    public FindItemByKeywordCommand(String keywordInput) {
        this.keywordInput = keywordInput;
    }

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

        logger.log(Level.INFO, "Found " + matches.size()
                + " item(s) matching keyword '" + keywordInput + "'.");

        ui.showDivider();
        ui.showMessage("Items matching keyword '" + keywordInput + "':");
        for (int i = 0; i < matches.size(); i++) {
            ui.showMessage((i + 1) + ". " + matches.get(i));
        }
        ui.showDivider();
    }
}
