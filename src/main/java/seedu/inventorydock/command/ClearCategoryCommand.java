package seedu.inventorydock.command;

import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.ui.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to clear all items within a
 * specified category.
 * Prompts the user for confirmation if the category is
 * not empty.
 */
public class ClearCategoryCommand extends Command {
    private static final Logger logger = Logger.getLogger(ClearCategoryCommand.class.getName());

    private final String categoryName;

    /**
     * Constructs a ClearCategoryCommand with the given
     * category name.
     *
     * @param categoryName The name of the category to clear.
     */
    public ClearCategoryCommand(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Executes the clear category command. If the category
     * is not empty, the user is prompted for confirmation
     * before clearing all items.
     *
     * @param inventory The inventory containing the category.
     * @param ui        The UI to display messages and read
     *                  user confirmation.
     */
    @Override
    public void execute(Inventory inventory, UI ui) {
        assert inventory != null : "ClearCategoryCommand received null inventory.";
        assert ui != null : "ClearCategoryCommand received null UI.";
        assert categoryName != null : "ClearCategoryCommand received null category name.";
        Category category = inventory.findCategoryByName(categoryName);

        if (category == null) {
            logger.log(Level.WARNING, "Category not found for clearing: " + categoryName);
            ui.showCategoryNotFound(categoryName);
            return;
        }

        if (category.isEmpty()) {
            logger.log(Level.INFO, "Category '" + categoryName + "' is already empty.");
            ui.showCategoryAlreadyEmpty(categoryName);
            return;
        }

        ui.showClearCategoryConfirmation(categoryName, category.getItemCount());
        String response = ui.readCommand();

        if (response == null || !response.trim().equalsIgnoreCase("yes")) {
            logger.log(Level.INFO, "Category clearing cancelled for: " + categoryName);
            ui.showClearCategoryCancelled(categoryName);
            return;
        }

        category.getItems().clear();
        ui.showCategoryItemsCleared(categoryName);
        logger.log(Level.INFO, "Cleared category '" + categoryName + "'.");
        ui.showCategoryCleared(categoryName);
    }
}
