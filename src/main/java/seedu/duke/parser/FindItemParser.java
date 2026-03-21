package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.FindItemByCategoryCommand;
import seedu.duke.command.FindItemByExpiryDateCommand;
import seedu.duke.ui.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FindItemParser {
    private static Logger logger = Logger.getLogger(FindItemParser.class.getName());

    private final UI ui;

    public FindItemParser(UI ui) {
        this.ui = ui;
    }

    public Command parse(String input) {
        assert input != null : "FindCommandParser received null input.";
        logger.log(Level.FINE, "Parsing find command.");

        if (input.isEmpty()) {
            logger.log(Level.WARNING, "Find command missing target.");
            ui.showInvalidInput("Please specify what to find. "
                    + "or find category/CATEGORY "
                    + "or find expiryDate/DATE");
            return null;
        }

        String[] parts = input.split("/", 2);

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            logger.log(Level.WARNING, "Find command missing name.");
            ui.showInvalidInput("Missing name. "
                    + "Use: find item/ITEM "
                    + "or find category/CATEGORY "
                    + "or find expiryDate/DATE");
            return null;
        }

        String type = parts[0].trim().toLowerCase();
        String name = parts[1].trim();

        switch (type) {
        case "keyword":
            logger.log(Level.FINE, "Parsed find item command for: " + name);
//            return new findItemByKeywordCommand(name);
        case "category":
            logger.log(Level.FINE, "Parsed find category command for: " + name);
            return new FindItemByCategoryCommand(name);
        case "expirydate":
            logger.log(Level.FINE, "Parsed find expiryDate command for: " + name);
            return new FindItemByExpiryDateCommand(name);
        default:
            logger.log(Level.WARNING, "Unknown find type: " + type);
            ui.showInvalidInput("Unknown find type: '" + type
                    + "Use: find item/ITEM "
                    + "or find category/CATEGORY"
                    + "or find expiryDate/DATE");
            return null;
        }
    }
}
