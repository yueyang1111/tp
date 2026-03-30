package seedu.duke.command;

import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.ui.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to delete all items within a
 * specified category.
 * Prompts the user for confirmation if the category is
 * not empty.
 */
public class DeleteCategoryCommand extends Command {
    private static final Logger logger = Logger.getLogger(DeleteCategoryCommand.class.getName());

    private final String categoryName;

    /**
     * Constructs a DeleteCategoryCommand with the given
     * category name.
     *
     * @param categoryName The name of the category to delete.
     */
    public DeleteCategoryCommand(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Executes the delete category command. If the category
     * is not empty, the user is prompted for confirmation
     * before clearing all items.
     *
     * @param inventory The inventory containing the category.
     * @param ui        The UI to display messages and read
     *                  user confirmation.
     */
    @Override
    public void execute(Inventory inventory, UI ui) {
        assert inventory != null : "DeleteCategoryCommand received null inventory.";
        assert ui != null : "DeleteCategoryCommand received null UI.";
        assert categoryName != null : "DeleteCategoryCommand received null category name.";
        Category category = inventory.findCategoryByName(categoryName);

        if (category == null) {
            logger.log(Level.WARNING, "Category not found for deletion: " + categoryName);
            ui.showCategoryNotFound(categoryName);
            return;
        }

        if (!category.isEmpty()) {
            ui.showDeleteCategoryConfirmation(categoryName, category.getItemCount());
            String response = ui.readCommand();

            if (response == null || !response.trim().equalsIgnoreCase("yes")) {
                logger.log(Level.INFO, "Category deletion cancelled for: " + categoryName);
                ui.showDeleteCategoryCancelled(categoryName);
                return;
            }

            category.getItems().clear();
            ui.showCategoryItemsCleared(categoryName);
        }
        logger.log(Level.INFO, "Deleted category '" + categoryName + "'.");
        ui.showCategoryDeleted(categoryName);
    }
}
