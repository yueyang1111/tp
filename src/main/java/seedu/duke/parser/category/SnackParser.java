package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;

public class SnackParser {
    public final String brand;
    public final String expiryDate;

    public SnackParser(String brand, String expiryDate) {
        this.brand = brand;
        this.expiryDate = expiryDate;
    }

    public static SnackParser parse(String input) throws DukeException {
        String brand = FieldParser.extractField(input, "brand/", "expiryDate/");
        if (brand == null || brand.trim().isEmpty()) {
            throw new DukeException("Missing brand for snack.");
        }

        String expiryDate = FieldParser.extractField(input, "expiryDate/", null);
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            throw new DukeException("Missing expiry date for snack.");
        }
        DateParser.validateDate(expiryDate);

        return new SnackParser(brand, expiryDate);
    }
}
