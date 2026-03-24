package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BurgerParser {
    private static final Logger logger = Logger.getLogger(BurgerParser.class.getName());

    public final String expiryDate;
    public final boolean isSpicy;
    public final String pattyType;

    public BurgerParser(String expiryDate, boolean isSpicy, String pattyType) {
        this.expiryDate = expiryDate;
        this.isSpicy = isSpicy;
        this.pattyType = pattyType;
    }

    public static BurgerParser parse(String input) throws DukeException {
        assert input != null : "BurgerParser received null input.";
        logger.log(Level.INFO, "Processing Burger special fields.");

        String expiryDate = FieldParser.extractField(input, "expiryDate/", "isSpicy/");
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing expiry date for burger.");
            throw new DukeException("Missing expiry date for burger.");
        }
        DateParser.validateDate(expiryDate);

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
        return new BurgerParser(expiryDate, isSpicy, pattyType);
    }
}
