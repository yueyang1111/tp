package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.DeleteCategoryCommand;
import seedu.duke.command.DeleteItemCommand;
import seedu.duke.ui.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteCommandParser {
    private static Logger logger = Logger.getLogger(DeleteCommandParser.class.getName());

    private final UI ui;

    public DeleteCommandParser(UI ui) {
        this.ui = ui;
    }

    public Command parse(String input) {
        assert input != null : "DeleteCommandParser received null input.";
        if (input.isEmpty()) {
            logger.log(Level.WARNING, "Delete command missing target.");
            ui.showInvalidInput("Please specify what to delete. "
                    + "Use: delete item/ITEM "
                    + "or delete category/CATEGORY");
            return null;
        }

        String[] parts = input.split("/", 2);

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            logger.log(Level.WARNING, "Delete command missing name.");
            ui.showInvalidInput("Missing name. "
                    + "Use: delete item/ITEM "
                    + "or delete category/CATEGORY");
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
            logger.log(Level.WARNING, "Unknown delete type: " + type);
            ui.showInvalidInput("Unknown delete type: '" + type
                    + "'. Use: delete item/ITEM "
                    + "or delete category/CATEGORY");
            return null;
        }
    }
}
