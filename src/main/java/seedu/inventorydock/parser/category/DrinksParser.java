package seedu.inventorydock.parser.category;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.parser.FieldParser;

/**
 * Parses drink-specific fields from user input.
 */
public class DrinksParser {
    private static final Logger logger = Logger.getLogger(DrinksParser.class.getName());

    public final String brand;
    public final String flavour;
    public final boolean isCarbonated;

    /**
     * Creates a {@code DrinksParser} object with the parsed drink details.
     *
     * @param brand Brand of the drink.
     * @param flavour Flavour of the drink.
     */
    public DrinksParser(String brand, String flavour, boolean isCarbonated) {
        this.brand = brand;
        this.flavour = flavour;
        this.isCarbonated = isCarbonated;
    }

    /**
     * Parses the drink-related fields from the given input string.
     *
     * @param input User input containing drink fields.
     * @return A {@code DrinksParser} containing the parsed values.
     * @throws InventoryDockException If any required field is missing or invalid.
     */
    public static DrinksParser parse(String input) throws InventoryDockException {
        assert input != null : "DrinksParser received null input.";
        logger.log(Level.INFO, "Processing Drinks special fields.");

        String brand = FieldParser.extractField(input, "brand/", "flavour/");
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for drinks.");
            throw new InventoryDockException("Missing brand for drinks.");
        }

        String flavour = FieldParser.extractField(input, "flavour/", "isCarbonated/");
        if (flavour == null || flavour.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing flavour for drinks.");
            throw new InventoryDockException("Missing flavour for drinks.");
        }

        String isCarbonatedString = FieldParser.extractField(input, "isCarbonated/", null);
        if (isCarbonatedString == null || isCarbonatedString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing carbonation for drinks.");
            throw new InventoryDockException("Missing carbonation for drinks.");
        }

        if (!(isCarbonatedString.equalsIgnoreCase("true") || isCarbonatedString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Carbonation must be true or false");
            throw new InventoryDockException("Carbonation must be true or false");
        }
        boolean isCarbonated = Boolean.parseBoolean(isCarbonatedString);

        logger.log(Level.INFO, "End of processing drinks.");
        return new DrinksParser(brand, flavour, isCarbonated);
    }
}
