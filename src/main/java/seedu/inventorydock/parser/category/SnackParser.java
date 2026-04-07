package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses snack-specific fields from user input.
 */
public class SnackParser {
    private static final Logger logger = Logger.getLogger(SnackParser.class.getName());
    public final String brand;
    public final boolean isCrunchy;

    /**
     * Creates a {@code SnackParser} object with the parsed snack details.
     *
     * @param brand Brand of the snack.
     */
    public SnackParser(String brand, boolean isCrunchy) {
        this.brand = brand;
        this.isCrunchy = isCrunchy;
    }

    /**
     * Parses the snack-related fields from the given input string.
     *
     * @param input User input containing snack fields.
     * @return A {@code SnackParser} containing the parsed values.
     * @throws InventoryDockException If the required field is missing or invalid.
     */
    public static SnackParser parse(String input) throws InventoryDockException {
        assert input != null : "SnackParser received null inputs.";
        String brand = FieldParser.extractField(input, "brand/", "isCrunchy/");
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for snack.");
            throw new InventoryDockException("Missing brand for snack.");
        }

        String crunchyString = FieldParser.extractField(input, "isCrunchy/", null);
        if (crunchyString == null || crunchyString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing crunchiness for snack.");
            throw new InventoryDockException("Missing crunchiness for snack.");
        }

        if (!(crunchyString.equalsIgnoreCase("true") || crunchyString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Crunchiness must be true or false");
            throw new InventoryDockException("Crunchiness must be true or false");
        }
        boolean isCrunchy = Boolean.parseBoolean(crunchyString);

        return new SnackParser(brand, isCrunchy);
    }
}

