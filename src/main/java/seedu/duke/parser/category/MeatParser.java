package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MeatParser {
    private static final Logger logger = Logger.getLogger(MeatParser.class.getName());

    public final String meatType;
    public final String origin;
    public final boolean isFrozen;

    public MeatParser(String meatType, String origin, boolean isFrozen) {
        this.meatType = meatType;
        this.origin = origin;
        this.isFrozen = isFrozen;
    }

    public static MeatParser parse(String input) throws DukeException {
        assert input != null : "MeatParser received null input.";
        logger.log(Level.INFO, "Processing Meat special fields.");

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

        String isFrozenString = FieldParser.extractField(input, "isFrozen/", null);
        if (isFrozenString == null || isFrozenString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing isFrozen for meat.");
            throw new DukeException("Missing isFrozen for meat.");
        }

        if (!(isFrozenString.equalsIgnoreCase("true") || isFrozenString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "isFrozen must be true or false");
            throw new DukeException("isFrozen must be true or false");
        }
        boolean isFrozen = Boolean.parseBoolean(isFrozenString);

        logger.log(Level.INFO, "End of processing meat.");
        return new MeatParser(meatType, origin, isFrozen);
    }
}
