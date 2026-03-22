package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeafoodParser {
    private static final Logger logger = Logger.getLogger(SeafoodParser.class.getName());

    public final String expiryDate;
    public final String seafoodType;
    public final String origin;
    public final boolean isFrozen;

    public SeafoodParser(String expiryDate, String seafoodType, String origin, boolean isFrozen) {
        this.expiryDate = expiryDate;
        this.seafoodType = seafoodType;
        this.origin = origin;
        this.isFrozen = isFrozen;
    }

    public static SeafoodParser parse(String input) throws DukeException {
        assert input != null : "SeafoodParser received null input.";
        logger.log(Level.INFO, "Processing Seafood special fields.");

        String expiryDate = FieldParser.extractField(input, "expiryDate/", "seafoodType/");
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing expiry date for seafood.");
            throw new DukeException("Missing expiry date for seafood.");
        }
        DateParser.validateDate(expiryDate);

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

        String frozenString = FieldParser.extractField(input, "isFrozen/", null);
        if (frozenString == null || frozenString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing frozen status for seafood.");
            throw new DukeException("Missing frozen status for seafood.");
        }

        if (!(frozenString.equalsIgnoreCase("true") || frozenString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "isFrozen must be true or false");
            throw new DukeException("isFrozen must be true or false");
        }
        boolean isFrozen = Boolean.parseBoolean(frozenString);

        logger.log(Level.INFO, "End of processing seafood.");
        return new SeafoodParser(expiryDate, seafoodType, origin, isFrozen);
    }
}