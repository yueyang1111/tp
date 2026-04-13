package seedu.inventorydock.parser;

import seedu.inventorydock.command.ClearCategoryCommand;
import seedu.inventorydock.command.Command;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.MissingArgumentException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses user input for the clear command.
 * Supports clearing all items within a category.
 */
public class ClearCommandParser {
    private static final Logger logger = Logger.getLogger(ClearCommandParser.class.getName());

    /**
     * Parses the given input string and returns a
     * {@link ClearCategoryCommand}.
     *
     * @param input The arguments following the "clear"
     *              command word.
     * @return The parsed Command.
     * @throws InventoryDockException If the input is invalid.
     */
    public Command parse(String input) throws InventoryDockException {
        assert input != null : "ClearCommandParser received null input.";
        if (input.isEmpty()) {
            logger.log(Level.WARNING, "Clear command missing target.");
            throw new MissingArgumentException("specify what to clear. Use: clear category/CATEGORY");
        }

        String[] tokens = input.trim().split("\\s+");
        String categoryName = null;

        for (String token : tokens) {
            int sep = token.indexOf('/');
            if (sep <= 0 || sep == token.length() - 1) {
                logger.log(Level.WARNING, "Invalid clear token: " + token);
                throw new InvalidCommandException("clear token '" + token + "' is invalid. Use: clear "
                        + "category/CATEGORY");
            }
            String key = token.substring(0, sep).trim().toLowerCase();
            String value = token.substring(sep + 1).trim();

            switch (key) {
            case "category":
                categoryName = value;
                break;
            default:
                logger.log(Level.WARNING, "Unknown clear field: " + key);
                throw new InvalidCommandException("field '" + key + "' is not supported. Use: clear "
                        + "category/CATEGORY");
            }
        }

        if (categoryName == null || categoryName.isEmpty()) {
            logger.log(Level.WARNING, "Clear command missing category.");
            throw new MissingArgumentException("category is required. Use: clear category/CATEGORY");
        }

        return new ClearCategoryCommand(categoryName);
    }
}
