package seedu.inventorydock.parser;

import seedu.inventorydock.command.Command;
import seedu.inventorydock.command.UpdateItemCommand;
import seedu.inventorydock.exception.DukeException;

import java.util.HashMap;
import java.util.Map;

/**
 * Parses user input for the update command into an executable command object.
 * An <code>UpdateCommandParser</code> object validates the category, item index,
 * and update fields before creating an update command.
 */
public class UpdateCommandParser {
    /**
     * Parses the specified update command arguments into an update command.
     * The input must contain <code>category/</code>, <code>index/</code>, and at least
     * one supported update field.
     *
     * @param input Update command arguments supplied by the user.
     * @return Command representing the requested item update.
     * @throws DukeException If the category, index, or update fields are invalid.
     */
    public Command parse(String input) throws DukeException {
        assert input != null : "UpdateCommandParser received null input.";

        if (input.isEmpty()) {
            throw new DukeException("Use: update category/CATEGORY index/INDEX "
                    + "[newItem/NAME] [bin/BIN] [qty/QTY] [expiryDate/DATE] ...");
        }

        String[] tokens = input.trim().split("\\s+");
        Map<String, String> fields = new HashMap<>();

        for (String token : tokens) {
            int separatorIndex = token.indexOf('/');
            if (separatorIndex <= 0 || separatorIndex == token.length() - 1) {
                throw new DukeException("Invalid update token: " + token);
            }

            String key = token.substring(0, separatorIndex);
            String value = token.substring(separatorIndex + 1);
            fields.put(key, value);
        }

        String categoryName = fields.remove("category");
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new DukeException("Missing category.");
        }

        String itemIndexString = fields.remove("index");
        if (itemIndexString == null || itemIndexString.trim().isEmpty()) {
            throw new DukeException("Missing item index.");
        }

        int itemIndex;
        try {
            itemIndex = Integer.parseInt(itemIndexString.trim());
        } catch (NumberFormatException e) {
            throw new DukeException("Item index must be an integer.");
        }

        if (itemIndex <= 0) {
            throw new DukeException("Item index must be a positive integer.");
        }

        if (fields.isEmpty()) {
            throw new DukeException("Provide at least one field to update.");
        }

        return new UpdateItemCommand(categoryName.trim(),
                itemIndex, fields);
    }
}
