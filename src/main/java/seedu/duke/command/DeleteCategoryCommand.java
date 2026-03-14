package seedu.duke.command;

import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.ui.UI;

public class DeleteCategoryCommand extends Command {
    private final String categoryName;

    public DeleteCategoryCommand(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public void execute(Inventory inventory, UI ui) {
        Category category = inventory.findCategoryByName(categoryName);

        if (category == null) {
            ui.showCategoryNotFound(categoryName);
            return;
        }

        if (!category.isEmpty()) {
            ui.showDeleteCategoryConfirmation(
                    categoryName, category.getItemCount());
            String response = ui.readCommand();

            if (response == null
                    || !response.trim().equalsIgnoreCase("yes")) {
                ui.showDeleteCategoryCancelled(categoryName);
                return;
            }

            category.getItems().clear();
            ui.showCategoryItemsCleared(categoryName);
        }

        inventory.getCategories().remove(category);
        ui.showCategoryDeleted(categoryName);
    }
}