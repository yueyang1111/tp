package seedu.inventorydock.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.inventorydock.command.Command;
import seedu.inventorydock.exception.InventoryDockException;

/**
 * Parses top-level {@code add} commands and routes them to the category-specific
 * parser that constructs the corresponding add-item command.
 */
public class AddCommandParser {
    private static final Logger logger = Logger.getLogger(AddCommandParser.class.getName());


    /**
     * Validates the common add-command fields, extracts the category, and delegates
     * parsing to the matching category handler.
     *
     * @param input raw arguments following the {@code add} command word.
     * @return parsed command ready for execution.
     * @throws InventoryDockException if the input is empty, missing required fields, or uses an unknown category.
     */
    public Command parse(String input) throws InventoryDockException {
        assert input != null : "AddCommandParser received null input.";
        if (input.isEmpty()) {
            logger.log(Level.WARNING, "Add command input is empty.");
            throw new InventoryDockException("Input is empty.");
        }

        String trimmedInput = input.trim();
        validateRequiredFields(trimmedInput);

        String category = extractCategory(trimmedInput);
        logger.log(Level.INFO, "Processing add command for category: " + category);
        return parseByCategory(trimmedInput, category);
    }

    /**
     * Ensures the shared fields needed by all add-item workflows are present.
     *
     * @param input normalized add-command input.
     * @throws InventoryDockException if the item name or category is missing.
     */
    private void validateRequiredFields(String input) throws InventoryDockException {
        String category = extractFieldValue(input, "category/");
        if (category == null || category.isEmpty()) {
            logger.log(Level.WARNING, "Missing category in add command.");
            throw new InventoryDockException("Missing category.");
        }
    }

    /**
     * Extracts the category field and normalizes it for parser dispatch.
     *
     * @param input normalized add-command input.
     * @return lower-case category name.
     */
    private String extractCategory(String input) {
        return extractFieldValue(input, "category/").toLowerCase();
    }

    /**
     * Returns the value of the first token with the given prefix.
     *
     * @param input command input to inspect.
     * @param prefix field prefix such as {@code category/}.
     * @return trimmed field value, or {@code null} if the field is absent.
     */
    private String extractFieldValue(String input, String prefix) {
        String[] tokens = input.split(" ");
        for (String token : tokens) {
            if (token.startsWith(prefix)) {
                return token.substring(prefix.length()).trim();
            }
        }
        return null;
    }

    /**
     * Routes an add command to the parser for the requested category.
     *
     * @param input normalized add-command input.
     * @param category normalized category name.
     * @return parsed add-item command for the category.
     * @throws InventoryDockException if the category is unsupported.
     */
    private Command parseByCategory(String input, String category) throws InventoryDockException {
        AddItemCommandParser parser = new AddItemCommandParser();

        switch (category) {
        case "fruits":
            return parser.handleFruit(input);
        case "snacks":
            return parser.handleSnack(input);
        case "toiletries":
            return parser.handleToiletries(input);
        case "vegetables":
            return parser.handleVegetables(input);
        case "drinks":
            return parser.handleDrinks(input);
        case "icecream":
            return parser.handleIceCream(input);
        case "sweets":
            return parser.handleSweets(input);
        case "burger":
            return parser.handleBurger(input);
        case "setmeal":
            return parser.handleSetMeal(input);
        case "seafood":
            return parser.handleSeafood(input);
        case "meat":
            return parser.handleMeat(input);
        case "petfood":
            return parser.handlePetFood(input);
        case "accessories":
            return parser.handleAccessories(input);
        default:
            logger.log(Level.WARNING, "Unknown add command category: " + category);
            throw new InventoryDockException("Unknown category: " + category);
        }
    }
}

