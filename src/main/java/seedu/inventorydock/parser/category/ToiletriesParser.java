package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses toiletries-specific fields from user input.
 */
public class ToiletriesParser {
    private static final Logger logger = Logger.getLogger(ToiletriesParser.class.getName());

    public final String brand;
    public final boolean isLiquid;

    /**
     * Creates a {@code ToiletriesParser} object with the parsed toiletry details.
     *
     * @param brand Brand of the toiletry item.
     * @param isLiquid Whether the toiletry item is liquid.
     */
    public ToiletriesParser(String brand, boolean isLiquid) {
        this.brand = brand;
        this.isLiquid = isLiquid;
    }

    /**
     * Parses the toiletries-related fields from the given input string.
     *
     * @param input User input containing toiletries fields.
     * @return A {@code ToiletriesParser} containing the parsed values.
     * @throws InventoryDockException If any required field is missing or invalid.
     */
    public static ToiletriesParser parse(String input) throws InventoryDockException {
        assert input != null : "ToiletriesParser received null inputs.";
        String brand = FieldParser.extractField(input, "brand/", "isLiquid/");
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for toiletries.");
            throw new InventoryDockException("Missing brand for toiletries.");
        }

        String liquidString = FieldParser.extractField(input, "isLiquid/", null);
        if (liquidString == null || liquidString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing liquid field for toiletries.");
            throw new InventoryDockException("Missing liquid field for toiletries.");
        }

        if (!(liquidString.equalsIgnoreCase("true") || liquidString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Liquid field must be true or false.");
            throw new InventoryDockException("Liquid field must be true or false.");
        }
        boolean isLiquid = Boolean.parseBoolean(liquidString);

        return new ToiletriesParser(brand, isLiquid);
    }
}

