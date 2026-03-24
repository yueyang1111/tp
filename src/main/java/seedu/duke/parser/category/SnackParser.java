package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SnackParser {
    private static Logger logger = Logger.getLogger(SnackParser.class.getName());
    public final String brand;

    public SnackParser(String brand) {
        this.brand = brand;
    }

    public static SnackParser parse(String input) throws DukeException {
        assert input != null : "SnackParser received null inputs.";
        String brand = FieldParser.extractField(input, "brand/", null);
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for snack.");
            throw new DukeException("Missing brand for snack.");
        }

        return new SnackParser(brand);
    }
}

