package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ToiletriesParser {
    private static Logger logger = Logger.getLogger(ToiletriesParser.class.getName());

    public final String brand;
    public final boolean isLiquid;

    public ToiletriesParser(String brand, boolean isLiquid) {
        this.brand = brand;
        this.isLiquid = isLiquid;
    }

    public static ToiletriesParser parse(String input) throws DukeException {
        assert input != null : "ToiletriesParser received null inputs.";
        String brand = FieldParser.extractField(input, "brand/", "isLiquid/");
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for toiletries.");
            throw new DukeException("Missing brand for toiletries.");
        }

        String liquidString = FieldParser.extractField(input, "isLiquid/", null);
        if (liquidString == null || liquidString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing liquid field for toiletries.");
            throw new DukeException("Missing liquid field for toiletries.");
        }

        if (!(liquidString.equalsIgnoreCase("true") || liquidString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Liquid field must be true or false.");
            throw new DukeException("Liquid field must be true or false.");
        }
        boolean isLiquid = Boolean.parseBoolean(liquidString);

        return new ToiletriesParser(brand, isLiquid);
    }
}

