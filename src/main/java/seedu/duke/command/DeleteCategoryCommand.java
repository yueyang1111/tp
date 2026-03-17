package seedu.duke.command;

import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.ui.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteCategoryCommand extends Command {
    private static Logger logger = Logger.getLogger(DeleteCategoryCommand.class.getName());

    private final String categoryName;

    public DeleteCategoryCommand(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public void execute(Inventory inventory, UI ui) {
        assert inventory != null : "DeleteCategoryCommand received null inventory.";
        assert ui != null : "DeleteCategoryCommand received null UI.";
        assert categoryName != null : "DeleteCategoryCommand received null category name.";
        logger.log(Level.INFO, "Attempting to delete category: " + categoryName);

        Category category = inventory.findCategoryByName(
                categoryName);

        if (category == null) {
            logger.log(Level.WARNING, "Category not found for deletion: " + categoryName);
            ui.showCategoryNotFound(categoryName);
            return;
        }

        if (!category.isEmpty()) {
            logger.log(Level.INFO, "Category " + categoryName + " is not empty, requesting confirmation.");
            ui.showDeleteCategoryConfirmation(
                    categoryName, category.getItemCount());
            String response = ui.readCommand();

            if (response == null
                    || !response.trim()
                    .equalsIgnoreCase("yes")) {
                logger.log(Level.INFO, "Category deletion cancelled for: " + categoryName);
                ui.showDeleteCategoryCancelled(categoryName);
                return;
            }

            category.getItems().clear();
            logger.log(Level.INFO, "Cleared items in category: " + categoryName);
            ui.showCategoryItemsCleared(categoryName);
        }

        inventory.getCategories().remove(category);
        logger.log(Level.INFO, "Deleted category: " + categoryName);
        ui.showCategoryDeleted(categoryName);
    }
}
