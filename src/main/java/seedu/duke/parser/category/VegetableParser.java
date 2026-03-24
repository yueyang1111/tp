package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VegetableParser {
    private static Logger logger = Logger.getLogger(VegetableParser.class.getName());

    public final boolean isLeafy;

    public VegetableParser(boolean isLeafy) {
        this.isLeafy = isLeafy;
    }

    public static VegetableParser parse(String input) throws DukeException {
        assert input != null : "VegetableParser received null inputs.";

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

        return new VegetableParser(isLeafy);
    }
}
