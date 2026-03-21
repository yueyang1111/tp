package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ToiletriesParser {
    private static Logger logger = Logger.getLogger(ToiletriesParser.class.getName());

    public final String brand;
    public final boolean isLiquid;
    public final String expiryDate;

    public ToiletriesParser(String brand, boolean isLiquid, String expiryDate) {
        this.brand = brand;
        this.isLiquid = isLiquid;
        this.expiryDate = expiryDate;
    }

    public static ToiletriesParser parse(String input) throws DukeException {
        assert input != null : "ToiletriesParser received null inputs.";
        logger.log(Level.INFO, "Processing Toiletries special fields.");

        String brand = FieldParser.extractField(input, "brand/", "isLiquid/");
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for toiletries.");
            throw new DukeException("Missing brand for toiletries.");
        }

        String liquidString = FieldParser.extractField(input, "isLiquid/", "expiryDate/");
        if (liquidString == null || liquidString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing liquid field for toiletries.");
            throw new DukeException("Missing liquid field for toiletries.");
        }

        if (!(liquidString.equalsIgnoreCase("true") || liquidString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Liquid field must be true or false.");
            throw new DukeException("Liquid field must be true or false.");
        }
        boolean isLiquid = Boolean.parseBoolean(liquidString);

        String expiryDate = FieldParser.extractField(input, "expiryDate/", null);
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing expiry date for toiletries.");
            throw new DukeException("Missing expiry date for toiletries.");
        }
        DateParser.validateDate(expiryDate);

        logger.log(Level.INFO, "End of processing toiletries.");
        return new ToiletriesParser(brand, isLiquid, expiryDate);
    }
}
