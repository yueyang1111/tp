package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.DeleteCategoryCommand;
import seedu.duke.command.DeleteItemCommand;
import seedu.duke.ui.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses user input for delete commands.
 * Supports deleting an item by category and index, or
 * deleting an entire category.
 */
public class DeleteCommandParser {
    private static final Logger logger = Logger.getLogger(DeleteCommandParser.class.getName());

    private final UI ui;

    /**
     * Constructs a DeleteCommandParser with the given UI.
     *
     * @param ui The UI used to display error messages.
     */
    public DeleteCommandParser(UI ui) {
        this.ui = ui;
    }

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
     * @return The parsed Command, or null if input is invalid.
     */
    public Command parse(String input) {
        assert input != null : "DeleteCommandParser received null input.";
        if (input.isEmpty()) {
            logger.log(Level.WARNING, "Delete command missing target.");
            ui.showInvalidInput("Please specify what to delete. " + "Use: delete category/CATEGORY "
                    + "index/INDEX " + "or delete category/CATEGORY");
            return null;
        }

        String[] tokens = input.trim().split("\\s+");
        String categoryName = null;
        String indexString = null;

        for (String token : tokens) {
            int sep = token.indexOf('/');
            if (sep <= 0 || sep == token.length() - 1) {
                logger.log(Level.WARNING, "Invalid delete token: " + token);
                ui.showInvalidInput("Invalid token: '" + token + "'. Use: delete "
                        + "category/CATEGORY index/INDEX " + "or delete category/CATEGORY");
                return null;
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
                ui.showInvalidInput("Unknown field: '" + key + "'. Use: delete "
                        + "category/CATEGORY index/INDEX " + "or delete category/CATEGORY");
                return null;
            }
        }

        if (categoryName == null || categoryName.isEmpty()) {
            logger.log(Level.WARNING, "Delete command missing category.");
            ui.showInvalidInput("Missing category. " + "Use: delete category/CATEGORY " + "index/INDEX "
                    + "or delete category/CATEGORY");
            return null;
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
     * @return The parsed DeleteItemCommand, or null if the
     *         index is invalid.
     */
    private Command parseDeleteItem(String categoryName,
                                    String indexString) {
        int itemIndex;
        try {
            itemIndex = Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Non-integer index: " + indexString);
            ui.showInvalidInput("Item index must be an integer.");
            return null;
        }

        if (itemIndex <= 0) {
            logger.log(Level.WARNING, "Non-positive index: " + itemIndex);
            ui.showInvalidInput("Item index must be a positive integer.");
            return null;
        }

        return new DeleteItemCommand(categoryName, itemIndex);
    }
}
