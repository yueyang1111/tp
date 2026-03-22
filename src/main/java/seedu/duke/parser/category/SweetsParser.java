package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SweetsParser {
    private static final Logger logger = Logger.getLogger(SweetsParser.class.getName());

    public final String expiryDate;
    public final String brand;
    public final String ingradient;

    public SweetsParser(String expiryDate, String brand, String ingradient) {
        this.expiryDate = expiryDate;
        this.brand = brand;
        this.ingradient = ingradient;
    }

    public static SweetsParser parse(String input) throws DukeException {
        assert input != null : "SweetsParser received null input.";
        logger.log(Level.INFO, "Processing Sweets special fields.");

        String expiryDate = FieldParser.extractField(input, "expiryDate/", "brand/");
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing expiry date for sweets.");
            throw new DukeException("Missing expiry date for sweets.");
        }
        DateParser.validateDate(expiryDate);

        String brand = FieldParser.extractField(input, "brand/", "ingradient/");
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for sweets.");
            throw new DukeException("Missing brand for sweets.");
        }

        String ingradient = FieldParser.extractField(input, "ingradient/", null);
        if (ingradient == null || ingradient.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing ingradient for sweets.");
            throw new DukeException("Missing ingradient for sweets.");
        }

        logger.log(Level.INFO, "End of processing sweets.");
        return new SweetsParser(expiryDate, brand, ingradient);
    }
}