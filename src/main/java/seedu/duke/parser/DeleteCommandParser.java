package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.DeleteCategoryCommand;
import seedu.duke.command.DeleteItemCommand;
import seedu.duke.ui.UI;

public class DeleteCommandParser {

    private final UI ui;

    public DeleteCommandParser(UI ui) {
        this.ui = ui;
    }

    public Command parse(String input) {
        if (input.isEmpty()) {
            ui.showInvalidInput("Please specify what to delete. "
                    + "Use: delete item/ITEM or delete category/CATEGORY");
            return null;
        }

        String[] parts = input.split("/", 2);

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            ui.showInvalidInput(
                    "Missing name. Use: delete item/ITEM or delete category/CATEGORY");
            return null;
        }

        String type = parts[0].trim().toLowerCase();
        String name = parts[1].trim();

        switch (type) {
            case "item":
                return new DeleteItemCommand(name);
            case "category":
                return new DeleteCategoryCommand(name);
            default:
                ui.showInvalidInput("Unknown delete type: '" + type
                        + "'. Use: delete item/ITEM or delete category/CATEGORY");
                return null;
        }
    }
}