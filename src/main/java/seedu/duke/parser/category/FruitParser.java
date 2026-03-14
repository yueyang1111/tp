package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;

public class FruitParser {
    public final String expiryDate;
    public final String size;
    public final boolean isRipe;

    public FruitParser(String expiryDate, String size, boolean isRipe) {
        this.expiryDate = expiryDate;
        this.size = size;
        this.isRipe = isRipe;
    }

    public static FruitParser parse(String input) throws DukeException {
        String expiryDate = FieldParser.extractField(input, "expiryDate/", "size/");
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            throw new DukeException("Missing expiry date for fruit.");
        }
        DateParser.validateDate(expiryDate);

        String size = FieldParser.extractField(input, "size/", "isRipe/");
        if (size == null || size.trim().isEmpty()) {
            throw new DukeException("Missing size for fruit.");
        }

        String ripeString = FieldParser.extractField(input, "isRipe/", null);
        if (ripeString == null || ripeString.trim().isEmpty()) {
            throw new DukeException("Missing ripeness for fruit.");
        }

        if (!(ripeString.equalsIgnoreCase("true") || ripeString.equalsIgnoreCase("false"))) {
            throw new DukeException("Ripeness must be true or false");
        }
        boolean isRipe = Boolean.parseBoolean(ripeString);

        return new FruitParser(expiryDate, size, isRipe);
    }
}
