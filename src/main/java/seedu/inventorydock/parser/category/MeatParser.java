package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.parser.FieldParser;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses meat-specific fields from user input.
 */
public class MeatParser {
    private static final Logger logger = Logger.getLogger(MeatParser.class.getName());

    public final String meatType;
    public final String origin;
    public final boolean isFrozen;

    /**
     * Creates a {@code MeatParser} object with the parsed meat details.
     *
     * @param meatType Type of meat.
     * @param origin Origin of the meat.
     */
    public MeatParser(String meatType, String origin, boolean isFrozen) {
        this.meatType = meatType;
        this.origin = origin;
        this.isFrozen = isFrozen;
    }

    public static MeatParser parse(String input) throws InventoryDockException {
        assert input != null : "MeatParser received null input.";
        logger.log(Level.INFO, "Processing Meat special fields.");

        String meatType = FieldParser.extractField(input, "meatType/", "origin/");
        if (meatType == null || meatType.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing meatType for meat.");
            throw new InventoryDockException("Missing meatType for meat.");
        }

        String origin = FieldParser.extractField(input, "origin/", "isFrozen/");
        if (origin == null || origin.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing origin for meat.");
            throw new InventoryDockException("Missing origin for meat.");
        }

        String isFrozenString = FieldParser.extractField(input, "isFrozen/", null);
        if (isFrozenString == null || isFrozenString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing isFrozen for meat.");
            throw new InventoryDockException("Missing isFrozen for meat.");
        }

        if (!(isFrozenString.equalsIgnoreCase("true") || isFrozenString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "isFrozen must be true or false");
            throw new InventoryDockException("isFrozen must be true or false");
        }
        boolean isFrozen = Boolean.parseBoolean(isFrozenString);

        logger.log(Level.INFO, "End of processing meat.");
        return new MeatParser(meatType, origin, isFrozen);
    }
}
