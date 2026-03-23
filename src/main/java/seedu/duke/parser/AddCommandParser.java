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
        if (input.isEmpty()) {
            logger.log(Level.WARNING, "Add command input is empty.");
            throw new DukeException("Input is empty.");
        }

        String trimmedInput = input.trim();
        validateRequiredFields(trimmedInput);

        String category = extractCategory(trimmedInput);
        logger.log(Level.INFO, "Processing add command for category: " + category);
        return parseByCategory(trimmedInput, category);
    }

    private void validateRequiredFields(String input) throws DukeException {
        String itemName = extractFieldValue(input, "item/");
        if (itemName == null || itemName.isEmpty()) {
            logger.log(Level.WARNING, "Missing item name in add command.");
            throw new DukeException("Missing item name.");
        }

        String category = extractFieldValue(input, "category/");
        if (category == null || category.isEmpty()) {
            logger.log(Level.WARNING, "Missing category in add command.");
            throw new DukeException("Missing category.");
        }
    }

    private String extractCategory(String input) {
        return extractFieldValue(input, "category/").toLowerCase();
    }

    private String extractFieldValue(String input, String prefix) {
        String[] tokens = input.split(" ");
        for (String token : tokens) {
            if (token.startsWith(prefix)) {
                return token.substring(prefix.length()).trim();
            }
        }
        return null;
    }

    private Command parseByCategory(String input, String category) throws DukeException {
        AddItemCommandParser parser = new AddItemCommandParser();

        switch (category) {
        case "fruits":
            return parser.handleFruit(input);
        case "snacks":
            return parser.handleSnack(input);
        case "toiletries":
            return parser.handleToiletries(input);
        case "vegetables":
            return parser.handleVegetables(trimmedInput);
        case "drinks":
            return parser.handleDrinks(trimmedInput);
        case "icecream":
            return parser.handleIcecream(trimmedInput);
        case "sweets":
            return parser.handleSweets(trimmedInput);
        case "burger":
            return parser.handleBurger(trimmedInput);
        case "setmeal":
            return parser.handleSetMeal(trimmedInput);
        case "seafood":
            return parser.handleSeafood(trimmedInput);
        case "meat":
            return parser.handleMeat(trimmedInput);
        case "petfood":
            return parser.handlePetFood(trimmedInput);
        case "accessories":
            return parser.handleAccessories(trimmedInput);
        default:
            logger.log(Level.WARNING, "Unknown add command category: " + category);
            throw new DukeException("Unknown category: " + category);
        }
    }
}
