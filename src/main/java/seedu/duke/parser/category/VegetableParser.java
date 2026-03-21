package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VegetableParser {
    private static Logger logger = Logger.getLogger(VegetableParser.class.getName());

    public final String expiryDate;
    public final boolean isLeafy;

    public VegetableParser(String expiryDate, boolean isLeafy) {
        this.expiryDate = expiryDate;
        this.isLeafy = isLeafy;
    }

    public static VegetableParser parse(String input) throws DukeException {
        assert input != null : "VegetableParser received null inputs.";
        logger.log(Level.FINE, "Processing vegetable special fields.");

        String expiryDate = FieldParser.extractField(input, "expiryDate/", "isLeafy/");
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing expiry date for vegetable.");
            throw new DukeException("Missing expiry date for vegetable.");
        }
        DateParser.validateDate(expiryDate);

        String leafyString = FieldParser.extractField(input, "isLeafy/", null);
        if (leafyString == null || leafyString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing leafy field for vegetable.");
            throw new DukeException("Missing leafy field for vegetable.");
        }

        if (!(leafyString.equalsIgnoreCase("true") || leafyString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Leafy field must be true or false.");
            throw new DukeException("Leafy field must be true or false.");
        }
        boolean isLeafy = Boolean.parseBoolean(leafyString);

        logger.log(Level.FINE, "Finished processing vegetable special fields.");
        return new VegetableParser(expiryDate, isLeafy);
    }
}
