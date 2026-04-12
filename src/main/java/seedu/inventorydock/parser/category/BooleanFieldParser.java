package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.parser.FieldParser;

/**
 * Parses a category-specific boolean field from user input.
 */
public class BooleanFieldParser {
    /**
     * Parses a boolean field with the given prefix from the input.
     *
     * @param input Full add-command input.
     * @param fieldPrefix Field prefix to parse, such as {@code isRipe/}.
     * @return Parsed boolean value.
     * @throws InventoryDockException If the field is missing or not true/false.
     */
    public static boolean parse(String input, String fieldPrefix) throws InventoryDockException {
        assert input != null : "BooleanFieldParser received null input.";
        assert fieldPrefix != null : "BooleanFieldParser received null field prefix.";

        String booleanString = FieldParser.extractField(input, fieldPrefix, null);
        if (booleanString == null || booleanString.trim().isEmpty()) {
            throw new MissingArgumentException("Missing value for " + fieldPrefix);
        }

        if (!(booleanString.equalsIgnoreCase("true") || booleanString.equalsIgnoreCase("false"))) {
            throw new InvalidCommandException(fieldPrefix + " must be true or false.");
        }

        return Boolean.parseBoolean(booleanString);
    }
}
