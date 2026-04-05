package seedu.inventorydock.parser.category;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.inventorydock.exception.DukeException;
import seedu.inventorydock.parser.FieldParser;

/**
 * Parses accessory-specific fields from user input.
 */
public class AccessoriesParser {
    private static final Logger logger = Logger.getLogger(AccessoriesParser.class.getName());

    public final String type;
    public final String material;
    public final boolean isFragile;

    /**
     * Creates an {@code AccessoriesParser} object with the parsed accessory details.
     *
     * @param type Type of the accessory.
     * @param material Material of the accessory.
     */
    public AccessoriesParser(String type, String material, boolean isFragile) {
        this.type = type;
        this.material = material;
        this.isFragile = isFragile;
    }

    /**
     * Parses the accessory-related fields from the given input string.
     *
     * @param input User input containing accessory fields.
     * @return An {@code AccessoriesParser} containing the parsed values.
     * @throws DukeException If any required field is missing or invalid.
     */
    public static AccessoriesParser parse(String input) throws DukeException {
        assert input != null : "AccessoriesParser received null input.";
        logger.log(Level.INFO, "Processing Accessories special fields.");

        String type = FieldParser.extractField(input, "type/", "material/");
        if (type == null || type.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing type for accessories.");
            throw new DukeException("Missing type for accessories.");
        }

        String material = FieldParser.extractField(input, "material/", "isFragile/");
        if (material == null || material.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing material for accessories.");
            throw new DukeException("Missing material for accessories.");
        }

        String isFragileString = FieldParser.extractField(input, "isFragile/", null);
        if (isFragileString == null || isFragileString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing isFragile for accessories.");
            throw new DukeException("Missing isFragile for accessories.");
        }

        if (!(isFragileString.equalsIgnoreCase("true") || isFragileString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "isFragile must be true or false");
            throw new DukeException("isFragile must be true or false");
        }
        boolean isFragile = Boolean.parseBoolean(isFragileString);

        logger.log(Level.INFO, "End of processing accessories.");
        return new AccessoriesParser(type, material, isFragile);
    }
}
