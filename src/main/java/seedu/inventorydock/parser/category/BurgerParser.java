package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.DukeException;
import seedu.inventorydock.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses burger-specific fields from user input.
 */
public class BurgerParser {
    private static final Logger logger = Logger.getLogger(BurgerParser.class.getName());

    public final boolean isSpicy;
    public final String pattyType;

    /**
     * Creates a {@code BurgerParser} object with the parsed burger details.
     *
     * @param isSpicy Whether the burger is spicy.
     * @param pattyType Type of patty used in the burger.
     */
    public BurgerParser(boolean isSpicy, String pattyType) {
        this.isSpicy = isSpicy;
        this.pattyType = pattyType;
    }

    /**
     * Parses the burger-related fields from the given input string.
     *
     * @param input User input containing burger fields.
     * @return A {@code BurgerParser} containing the parsed values.
     * @throws DukeException If any required field is missing or invalid.
     */
    public static BurgerParser parse(String input) throws DukeException {
        assert input != null : "BurgerParser received null input.";
        logger.log(Level.INFO, "Processing Burger special fields.");

        String spicyString = FieldParser.extractField(input, "isSpicy/", "pattyType/");
        if (spicyString == null || spicyString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing isSpicy for burger.");
            throw new DukeException("Missing isSpicy for burger.");
        }

        if (!(spicyString.equalsIgnoreCase("true") || spicyString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "isSpicy must be true or false");
            throw new DukeException("isSpicy must be true or false");
        }
        boolean isSpicy = Boolean.parseBoolean(spicyString);

        String pattyType = FieldParser.extractField(input, "pattyType/", null);
        if (pattyType == null || pattyType.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing pattyType for burger.");
            throw new DukeException("Missing pattyType for burger.");
        }

        logger.log(Level.INFO, "End of processing burger.");
        return new BurgerParser(isSpicy, pattyType);
    }
}
