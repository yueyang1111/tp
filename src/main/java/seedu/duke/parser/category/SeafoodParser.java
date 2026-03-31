package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses seafood-specific fields from user input.
 */
public class SeafoodParser {
    private static final Logger logger = Logger.getLogger(SeafoodParser.class.getName());

    public final String seafoodType;
    public final String origin;
    public final boolean isFrozen;

    /**
     * Creates a {@code SeafoodParser} object with the parsed seafood details.
     *
     * @param seafoodType Type of seafood.
     * @param origin Origin of the seafood.
     */
    public SeafoodParser(String seafoodType, String origin, boolean isFrozen) {
        this.seafoodType = seafoodType;
        this.origin = origin;
        this.isFrozen = isFrozen;
    }

    /**
     * Parses the seafood-related fields from the given input string.
     *
     * @param input User input containing seafood fields.
     * @return A {@code SeafoodParser} containing the parsed values.
     * @throws DukeException If any required field is missing or invalid.
     */
    public static SeafoodParser parse(String input) throws DukeException {
        assert input != null : "SeafoodParser received null input.";
        logger.log(Level.INFO, "Processing Seafood special fields.");

        String seafoodType = FieldParser.extractField(input, "seafoodType/", "origin/");
        if (seafoodType == null || seafoodType.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing seafoodType for seafood.");
            throw new DukeException("Missing seafoodType for seafood.");
        }

        String origin = FieldParser.extractField(input, "origin/", "isFrozen/");
        if (origin == null || origin.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing origin for seafood.");
            throw new DukeException("Missing origin for seafood.");
        }

        String isFrozenString = FieldParser.extractField(input, "isFrozen/", null);
        if (isFrozenString == null || isFrozenString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing isFrozen for seafood.");
            throw new DukeException("Missing isFrozen for seafood.");
        }

        if (!(isFrozenString.equalsIgnoreCase("true") || isFrozenString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "isFrozen must be true or false");
            throw new DukeException("isFrozen must be true or false");
        }
        boolean isFrozen = Boolean.parseBoolean(isFrozenString);

        logger.log(Level.INFO, "End of processing seafood.");
        return new SeafoodParser(seafoodType, origin, isFrozen);
    }
}
