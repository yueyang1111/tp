package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SnackParser {
    private static final Logger logger = Logger.getLogger(SnackParser.class.getName());
    public final String brand;
    public final boolean isCrunchy;

    public SnackParser(String brand, boolean isCrunchy) {
        this.brand = brand;
        this.isCrunchy = isCrunchy;
    }

    public static SnackParser parse(String input) throws DukeException {
        assert input != null : "SnackParser received null inputs.";
        String brand = FieldParser.extractField(input, "brand/", "isCrunchy/");
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for snack.");
            throw new DukeException("Missing brand for snack.");
        }

        String crunchyString = FieldParser.extractField(input, "isCrunchy/", null);
        if (crunchyString == null || crunchyString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing crunchiness for snack.");
            throw new DukeException("Missing crunchiness for snack.");
        }

        if (!(crunchyString.equalsIgnoreCase("true") || crunchyString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "Crunchiness must be true or false");
            throw new DukeException("Crunchiness must be true or false");
        }
        boolean isCrunchy = Boolean.parseBoolean(crunchyString);

        return new SnackParser(brand, isCrunchy);
    }
}

