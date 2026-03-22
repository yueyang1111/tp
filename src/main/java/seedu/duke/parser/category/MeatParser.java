package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MeatParser {
    private static final Logger logger = Logger.getLogger(MeatParser.class.getName());

    public final String expiryDate;
    public final String meatType;
    public final String origin;
    public final boolean isFrozen;

    public MeatParser(String expiryDate, String meatType, String origin, boolean isFrozen) {
        this.expiryDate = expiryDate;
        this.meatType = meatType;
        this.origin = origin;
        this.isFrozen = isFrozen;
    }

    public static MeatParser parse(String input) throws DukeException {
        assert input != null : "MeatParser received null input.";
        logger.log(Level.INFO, "Processing Meat special fields.");

        String expiryDate = FieldParser.extractField(input, "expiryDate/", "meatType/");
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing expiry date for meat.");
            throw new DukeException("Missing expiry date for meat.");
        }
        DateParser.validateDate(expiryDate);

        String meatType = FieldParser.extractField(input, "meatType/", "origin/");
        if (meatType == null || meatType.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing meatType for meat.");
            throw new DukeException("Missing meatType for meat.");
        }

        String origin = FieldParser.extractField(input, "origin/", "isFrozen/");
        if (origin == null || origin.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing origin for meat.");
            throw new DukeException("Missing origin for meat.");
        }

        String frozenString = FieldParser.extractField(input, "isFrozen/", null);
        if (frozenString == null || frozenString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing frozen status for meat.");
            throw new DukeException("Missing frozen status for meat.");
        }

        if (!(frozenString.equalsIgnoreCase("true") || frozenString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "isFrozen must be true or false");
            throw new DukeException("isFrozen must be true or false");
        }
        boolean isFrozen = Boolean.parseBoolean(frozenString);

        logger.log(Level.INFO, "End of processing meat.");
        return new MeatParser(expiryDate, meatType, origin, isFrozen);
    }
}