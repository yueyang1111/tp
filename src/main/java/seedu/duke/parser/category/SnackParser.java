package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SnackParser {
    private static Logger logger = Logger.getLogger(SnackParser.class.getName());
    public final String brand;
    public final String expiryDate;

    public SnackParser(String brand, String expiryDate) {
        this.brand = brand;
        this.expiryDate = expiryDate;
    }

    public static SnackParser parse(String input) throws DukeException {
        assert input != null : "SnackParser received null inputs.";
        String brand = FieldParser.extractField(input, "brand/", "expiryDate/");
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for snack.");
            throw new DukeException("Missing brand for snack.");
        }

        String expiryDate = FieldParser.extractField(input, "expiryDate/", null);
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing expiry date for snack.");
            throw new DukeException("Missing expiry date for snack.");
        }
        DateParser.validateDate(expiryDate);

        return new SnackParser(brand, expiryDate);
    }
}

