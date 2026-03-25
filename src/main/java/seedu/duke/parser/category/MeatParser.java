package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MeatParser {
    private static final Logger logger = Logger.getLogger(MeatParser.class.getName());

    public final String meatType;
    public final String origin;

    public MeatParser(String meatType, String origin) {
        this.meatType = meatType;
        this.origin = origin;
    }

    public static MeatParser parse(String input) throws DukeException {
        assert input != null : "MeatParser received null input.";
        logger.log(Level.INFO, "Processing Meat special fields.");

        String meatType = FieldParser.extractField(input, "meatType/", "origin/");
        if (meatType == null || meatType.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing meatType for meat.");
            throw new DukeException("Missing meatType for meat.");
        }

        String origin = FieldParser.extractField(input, "origin/", null);
        if (origin == null || origin.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing origin for meat.");
            throw new DukeException("Missing origin for meat.");
        }

        logger.log(Level.INFO, "End of processing meat.");
        return new MeatParser(meatType, origin);
    }
}
