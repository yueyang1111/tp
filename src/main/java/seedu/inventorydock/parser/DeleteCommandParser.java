package seedu.inventorydock.parser;


import seedu.inventorydock.command.Command;
import seedu.inventorydock.command.DeleteCategoryCommand;
import seedu.inventorydock.command.DeleteItemCommand;
import seedu.inventorydock.exception.DukeException;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses user input for delete commands.
 * Supports deleting an item by category and index, or
 * deleting an entire category.
 */
public class DeleteCommandParser {
    private static final Logger logger = Logger.getLogger(DeleteCommandParser.class.getName());

    /**
     * Parses the given input string and returns the
     * appropriate delete command.
     * Returns a {@link DeleteItemCommand} if both category
     * and index are provided, or a
     * {@link DeleteCategoryCommand} if only category is
     * provided.
     *
     * @param input The arguments following the "delete"
     *              command word.
     * @return The parsed Command.
     * @throws DukeException If the input is invalid.
     */
    public Command parse(String input) throws DukeException {
        assert input != null : "DeleteCommandParser received null input.";
        if (input.isEmpty()) {
            logger.log(Level.WARNING, "Delete command missing target.");
            throw new DukeException("Please specify what to delete. Use: delete category/CATEGORY "
                    + "index/INDEX or delete category/CATEGORY");
        }

        String[] tokens = input.trim().split("\\s+");
        String categoryName = null;
        String indexString = null;

        for (String token : tokens) {
            int sep = token.indexOf('/');
            if (sep <= 0 || sep == token.length() - 1) {
                logger.log(Level.WARNING, "Invalid delete token: " + token);
                throw new DukeException("Invalid token: '" + token + "'. Use: delete "
                        + "category/CATEGORY index/INDEX or delete category/CATEGORY");
            }
            String key = token.substring(0, sep).trim().toLowerCase();
            String value = token.substring(sep + 1).trim();

            switch (key) {
            case "category":
                categoryName = value;
                break;
            case "index":
                indexString = value;
                break;
            default:
                logger.log(Level.WARNING, "Unknown delete field: " + key);
                throw new DukeException("Unknown field: '" + key + "'. Use: delete "
                        + "category/CATEGORY index/INDEX or delete category/CATEGORY");
            }
        }

        if (categoryName == null || categoryName.isEmpty()) {
            logger.log(Level.WARNING, "Delete command missing category.");
            throw new DukeException("Missing category. Use: delete category/CATEGORY index/INDEX "
                    + "or delete category/CATEGORY");
        }

        if (indexString != null) {
            return parseDeleteItem(categoryName, indexString);
        }

        return new DeleteCategoryCommand(categoryName);
    }

    /**
     * Parses the index string and returns a
     * {@link DeleteItemCommand} for the given category.
     *
     * @param categoryName The name of the category.
     * @param indexString  The string representation of the
     *                     item index.
     * @return The parsed DeleteItemCommand.
     * @throws DukeException If the index is invalid.
     */
    private Command parseDeleteItem(String categoryName,
                                    String indexString) throws DukeException {
        int itemIndex;
        try {
            itemIndex = Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Non-integer index: " + indexString);
            throw new DukeException("Item index must be an integer.");
        }

        if (itemIndex <= 0) {
            logger.log(Level.WARNING, "Non-positive index: " + itemIndex);
            throw new DukeException("Item index must be a positive integer.");
        }

        return new DeleteItemCommand(categoryName, itemIndex);
    }
}
