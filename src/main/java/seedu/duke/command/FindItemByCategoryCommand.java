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

public class FindItemByCategoryCommand extends Command {
    private final String categoryInput;
    private static final Logger logger = Logger.getLogger(FindItemByCategoryCommand.class.getName());

    public FindItemByCategoryCommand(String categoryInput) {
        this.categoryInput = categoryInput;
    }

    @Override
    public void execute(Inventory inventory, UI ui) throws DukeException{
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
        for (int i = 0; i < items.size(); i++) {
            ui.showMessage((i + 1) + ". " + items.get(i));
        }
        ui.showDivider();
    }
}
