package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses vegetable-specific fields from user input.
 */
public class VegetableParser {
    private static final Logger logger = Logger.getLogger(VegetableParser.class.getName());

    public final boolean isLeafy;

    /**
     * Creates a {@code VegetableParser} object with the parsed vegetable details.
     *
     * @param isLeafy Whether the vegetable is leafy.
     */
    public VegetableParser(boolean isLeafy) {
        this.isLeafy = isLeafy;
    }

    /**
     * Parses the vegetable-related fields from the given input string.
     *
     * @param input User input containing vegetable fields.
     * @return A {@code VegetableParser} containing the parsed values.
     * @throws InventoryDockException If the required field is missing or invalid.
     */
    public static VegetableParser parse(String input) throws InventoryDockException {
        assert input != null : "VegetableParser received null inputs.";

        String leafyString = FieldParser.extractField(input, "isLeafy/", null);
        if (leafyString == null || leafyString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing leafy field for vegetable.");
            throw new InventoryDockException("Missing leafy field for vegetable.");
        }

        if (!(leafyString.equalsIgnoreCase("true") || leafyString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Leafy field must be true or false.");
            throw new InventoryDockException("Leafy field must be true or false.");
        }
        boolean isLeafy = Boolean.parseBoolean(leafyString);

        return new VegetableParser(isLeafy);
    }
}
