package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses ice cream-specific fields from user input.
 */
public class IceCreamParser {
    private static final Logger logger = Logger.getLogger(IceCreamParser.class.getName());

    public final String flavour;
    public final boolean isDairyFree;

    /**
     * Creates an {@code IceCreamParser} object with the parsed ice cream details.
     *
     * @param flavour Flavour of the ice cream.
     * @param isDairyFree Whether the ice cream is dairy-free.
     */
    public IceCreamParser(String flavour, boolean isDairyFree) {
        this.flavour = flavour;
        this.isDairyFree = isDairyFree;
    }

    /**
     * Parses the ice cream-related fields from the given input string.
     *
     * @param input User input containing ice cream fields.
     * @return An {@code IceCreamParser} containing the parsed values.
     * @throws DukeException If any required field is missing or invalid.
     */
    public static IceCreamParser parse(String input) throws DukeException {
        assert input != null : "IceCreamParser received null input.";
        logger.log(Level.INFO, "Processing IceCream special fields.");

        String flavour = FieldParser.extractField(input, "flavour/", "isDairyFree/");
        if (flavour == null || flavour.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing flavour for ice cream.");
            throw new DukeException("Missing flavour for ice cream.");
        }

        String dairyFreeString = FieldParser.extractField(input, "isDairyFree/", null);
        if (dairyFreeString == null || dairyFreeString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing dairy-free status for ice cream.");
            throw new DukeException("Missing dairy-free status for ice cream.");
        }

        if (!(dairyFreeString.equalsIgnoreCase("true") || dairyFreeString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "isDairyFree must be true or false");
            throw new DukeException("isDairyFree must be true or false");
        }
        boolean isDairyFree = Boolean.parseBoolean(dairyFreeString);

        logger.log(Level.INFO, "End of processing ice cream.");
        return new IceCreamParser(flavour, isDairyFree);
    }
}
