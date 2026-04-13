package seedu.inventorydock.parser;


import seedu.inventorydock.command.Command;
import seedu.inventorydock.command.DeleteItemCommand;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.InvalidIndexException;
import seedu.inventorydock.exception.MissingArgumentException;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses user input for delete commands.
 * Supports deleting an item by category and index.
 * To clear all items in a category, use the "clear"
 * command instead.
 */
public class DeleteCommandParser {
    private static final Logger logger = Logger.getLogger(DeleteCommandParser.class.getName());

    /**
     * Parses the given input string and returns a
     * {@link DeleteItemCommand}.
     * Requires both category and index to be provided.
     *
     * @param input The arguments following the "delete"
     *              command word.
     * @return The parsed Command.
     * @throws InventoryDockException If the input is invalid.
     */
    public Command parse(String input) throws InventoryDockException {
        assert input != null : "DeleteCommandParser received null input.";
        if (input.isEmpty()) {
            logger.log(Level.WARNING, "Delete command missing target.");
            throw new MissingArgumentException("specify what to delete. Use: delete category/CATEGORY "
                    + "index/INDEX");
        }

        String[] tokens = input.trim().split("\\s+");
        String categoryName = null;
        String indexString = null;

        for (String token : tokens) {
            int sep = token.indexOf('/');
            if (sep <= 0 || sep == token.length() - 1) {
                logger.log(Level.WARNING, "Invalid delete token: " + token);
                throw new InvalidCommandException("delete token '" + token + "' is invalid. Use: delete "
                        + "category/CATEGORY index/INDEX");
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
                throw new InvalidCommandException("field '" + key + "' is not supported. Use: delete "
                        + "category/CATEGORY index/INDEX");
            }
        }

        if (categoryName == null || categoryName.isEmpty()) {
            logger.log(Level.WARNING, "Delete command missing category.");
            throw new MissingArgumentException("category is required. Use: delete category/CATEGORY index/INDEX");
        }

        if (indexString == null) {
            logger.log(Level.WARNING, "Delete command missing index.");
            throw new MissingArgumentException("index is required. Use: delete category/CATEGORY index/INDEX. "
                    + "To clear all items in a category, use: clear category/CATEGORY");
        }

        return parseDeleteItem(categoryName, indexString);
    }

    /**
     * Parses the index string and returns a
     * {@link DeleteItemCommand} for the given category.
     *
     * @param categoryName The name of the category.
     * @param indexString  The string representation of the
     *                     item index.
     * @return The parsed DeleteItemCommand.
     * @throws InventoryDockException If the index is invalid.
     */
    private Command parseDeleteItem(String categoryName,
                                    String indexString) throws InventoryDockException {
        int itemIndex;
        try {
            itemIndex = Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Non-integer index: " + indexString);
            throw new InvalidIndexException("Item index must be an integer.", e);
        }

        if (itemIndex <= 0) {
            logger.log(Level.WARNING, "Non-positive index: " + itemIndex);
            throw new InvalidIndexException("Item index must be a positive integer.");
        }

        return new DeleteItemCommand(categoryName, itemIndex);
    }
}
