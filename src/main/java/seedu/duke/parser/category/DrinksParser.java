package seedu.duke.parser.category;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;

public class DrinksParser {
    private static final Logger logger = Logger.getLogger(DrinksParser.class.getName());

    public final String brand;
    public final String flavour;
    public final boolean isCarbonated;

    public DrinksParser(String brand, String flavour, boolean isCarbonated) {
        this.brand = brand;
        this.flavour = flavour;
        this.isCarbonated = isCarbonated;
    }

    public static DrinksParser parse(String input) throws DukeException {
        assert input != null : "DrinksParser received null input.";
        logger.log(Level.INFO, "Processing Drinks special fields.");

        String brand = FieldParser.extractField(input, "brand/", "flavour/");
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for drinks.");
            throw new DukeException("Missing brand for drinks.");
        }

        String flavour = FieldParser.extractField(input, "flavour/", "isCarbonated/");
        if (flavour == null || flavour.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing flavour for drinks.");
            throw new DukeException("Missing flavour for drinks.");
        }

        String isCarbonatedString = FieldParser.extractField(input, "isCarbonated/", null);
        if (isCarbonatedString == null || isCarbonatedString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing carbonation for drinks.");
            throw new DukeException("Missing carbonation for drinks.");
        }

        if (!(isCarbonatedString.equalsIgnoreCase("true") || isCarbonatedString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Carbonation must be true or false");
            throw new DukeException("Carbonation must be true or false");
        }
        boolean isCarbonated = Boolean.parseBoolean(isCarbonatedString);

        logger.log(Level.INFO, "End of processing drinks.");
        return new DrinksParser(brand, flavour, isCarbonated);
    }
}
