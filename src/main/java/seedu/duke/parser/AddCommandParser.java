package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.UI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCommandParser {
    private static final Logger logger = Logger.getLogger(AddCommandParser.class.getName());

    private final UI ui;

    public AddCommandParser(UI ui) {
        assert ui != null : "AddCommandParser received null UI.";
        this.ui = ui;
    }

    public Command parse(String input) throws DukeException {
        assert input != null : "AddCommandParser received null input.";
        logger.log(Level.FINE, "Parsing add command input.");

        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            logger.log(Level.WARNING, "Add command input is empty.");
            throw new DukeException("Input is empty.");
        }

        String[] tokens = trimmedInput.split(" ");

        String itemName = null;
        String category = null;

        for (String token : tokens) {

            if (token.startsWith("item/")) {
                itemName = token.substring("item/".length()).trim();
            }

            if (token.startsWith("category/")) {
                category = token.substring("category/".length()).trim().toLowerCase();
            }

            if (itemName != null && category != null) {
                break;
            }
        }

        if (itemName == null || itemName.isEmpty()) {
            logger.log(Level.WARNING, "Missing item name in add command.");
            throw new DukeException("Missing item name.");
        }

        if (category == null || category.isEmpty()) {
            logger.log(Level.WARNING, "Missing category in add command.");
            throw new DukeException("Missing category.");
        }

        AddItemCommandParser parser = new AddItemCommandParser();
        logger.log(Level.INFO, "Processing add command for category: " + category);

        switch (category) {
        case "fruits":
            return parser.handleFruit(trimmedInput);
        case "snacks":
            return parser.handleSnack(trimmedInput);
        case "toiletries":
            return parser.handleToiletries(trimmedInput);
        case "vegetables":
            return parser.handleVegetables(trimmedInput);
        default:
            logger.log(Level.WARNING, "Unknown add command category: " + category);
            throw new DukeException("Unknown category: " + category);
        }
    }
}
