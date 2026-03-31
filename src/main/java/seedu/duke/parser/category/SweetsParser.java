package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses sweets-specific fields from user input.
 */
public class SweetsParser {
    private static final Logger logger = Logger.getLogger(SweetsParser.class.getName());

    public final String brand;
    public final String sweetnessLevel;
    public final boolean isChewy;

    /**
     * Creates a {@code SweetsParser} object with the parsed sweets details.
     *
     * @param brand Brand of the sweets.
     * @param sweetnessLevel Sweetness level of the sweets.
     */
    public SweetsParser(String brand, String sweetnessLevel, boolean isChewy) {
        this.brand = brand;
        this.sweetnessLevel = sweetnessLevel;
        this.isChewy = isChewy;
    }

    /**
     * Parses the sweets-related fields from the given input string.
     *
     * @param input User input containing sweets fields.
     * @return A {@code SweetsParser} containing the parsed values.
     * @throws DukeException If any required field is missing or invalid.
     */
    public static SweetsParser parse(String input) throws DukeException {
        assert input != null : "SweetsParser received null input.";
        logger.log(Level.INFO, "Processing Sweets special fields.");

        String brand = FieldParser.extractField(input, "brand/", "sweetnessLevel/");
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for sweets.");
            throw new DukeException("Missing brand for sweets.");
        }

        String sweetnessLevel = FieldParser.extractField(input, "sweetnessLevel/", "isChewy/");
        if (sweetnessLevel == null || sweetnessLevel.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing sweetness level for sweets.");
            throw new DukeException("Missing sweetness level for sweets.");
        }

        String chewyString = FieldParser.extractField(input, "isChewy/", null);
        if (chewyString == null || chewyString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing chewiness for snack.");
            throw new DukeException("Missing chewiness for snack.");
        }

        if (!(chewyString.equalsIgnoreCase("true") || chewyString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Chewiness must be true or false");
            throw new DukeException("Chewiness must be true or false");
        }
        boolean isChewy = Boolean.parseBoolean(chewyString);

        logger.log(Level.INFO, "End of processing sweets.");
        return new SweetsParser(brand, sweetnessLevel, isChewy);
    }
}
