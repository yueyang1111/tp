package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;

public class VegetableParser {
    public final String expiryDate;
    public final boolean isLeafy;

    public VegetableParser(String expiryDate, boolean isLeafy) {
        this.expiryDate = expiryDate;
        this.isLeafy = isLeafy;
    }

    public static VegetableParser parse(String input) throws DukeException {
        assert input != null : "VegetableParser received null inputs.";

        String expiryDate = FieldParser.extractField(input, "expiryDate/", "isLeafy/");
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            throw new DukeException("Missing expiry date for vegetable.");
        }
        DateParser.validateDate(expiryDate);

        String leafyString = FieldParser.extractField(input, "isLeafy/", null);
        if (leafyString == null || leafyString.trim().isEmpty()) {
            throw new DukeException("Missing leafy field for vegetable.");
        }

        if (!(leafyString.equalsIgnoreCase("true") || leafyString.equalsIgnoreCase("false"))) {
            throw new DukeException("Leafy field must be true or false.");
        }
        boolean isLeafy = Boolean.parseBoolean(leafyString);

        return new VegetableParser(expiryDate, isLeafy);
    }
}
