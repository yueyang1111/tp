package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FruitParser {
    private static Logger logger = Logger.getLogger(FruitParser.class.getName());

    public final String expiryDate;
    public final String size;
    public final boolean isRipe;

    public FruitParser(String expiryDate, String size, boolean isRipe) {
        this.expiryDate = expiryDate;
        this.size = size;
        this.isRipe = isRipe;
    }

    public static FruitParser parse(String input) throws DukeException {
        assert input != null : "FruitParser received null input.";
        logger.log(Level.INFO, "Processing Fruit special fields.");

        String expiryDate = FieldParser.extractField(input, "expiryDate/", "size/");
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing expiry date for fruit.");
            throw new DukeException("Missing expiry date for fruit.");
        }
        DateParser.validateDate(expiryDate);

        String size = FieldParser.extractField(input, "size/", "isRipe/");
        if (size == null || size.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing size for fruit.");
            throw new DukeException("Missing size for fruit.");
        }

        String ripeString = FieldParser.extractField(input, "isRipe/", null);
        if (ripeString == null || ripeString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing ripeness for fruit.");
            throw new DukeException("Missing ripeness for fruit.");
        }

        if (!(ripeString.equalsIgnoreCase("true") || ripeString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Ripeness must be true or false");
            throw new DukeException("Ripeness must be true or false");
        }
        boolean isRipe = Boolean.parseBoolean(ripeString);

        logger.log(Level.INFO, "End of processing fruit.");
        return new FruitParser(expiryDate, size, isRipe);
    }
}
