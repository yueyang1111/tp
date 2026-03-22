package seedu.duke.parser.category;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;

public class DrinksParser {
    private static final Logger logger = Logger.getLogger(DrinksParser.class.getName());

    public final String expiryDate;
    public final String brand;
    public final String flavour;
    public final int volume;
    public final boolean isCold;
    public final boolean isCanned;

    public DrinksParser(String expiryDate, String brand, String flavour,
                        int volume, boolean isCold, boolean isCanned) {
        this.expiryDate = expiryDate;
        this.brand = brand;
        this.flavour = flavour;
        this.volume = volume;
        this.isCold = isCold;
        this.isCanned = isCanned;
    }

    public static DrinksParser parse(String input) throws DukeException {
        assert input != null : "DrinksParser received null input.";
        logger.log(Level.INFO, "Processing Drinks special fields.");

        String expiryDate = FieldParser.extractField(input, "expiryDate/", "brand/");
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing expiry date for drinks.");
            throw new DukeException("Missing expiry date for drinks.");
        }
        DateParser.validateDate(expiryDate);

        String brand = FieldParser.extractField(input, "brand/", "flavour/");
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for drinks.");
            throw new DukeException("Missing brand for drinks.");
        }

        String flavour = FieldParser.extractField(input, "flavour/", "volume/");
        if (flavour == null || flavour.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing flavour for drinks.");
            throw new DukeException("Missing flavour for drinks.");
        }

        String volumeString = FieldParser.extractField(input, "volume/", "isCold/");
        if (volumeString == null || volumeString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing volume for drinks.");
            throw new DukeException("Missing volume for drinks.");
        }

        int volume;
        try {
            volume = Integer.parseInt(volumeString.trim());
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Volume must be an integer.");
            throw new DukeException("Volume must be an integer.");
        }

        String coldString = FieldParser.extractField(input, "isCold/", "isCanned/");
        if (coldString == null || coldString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing coldness for drinks.");
            throw new DukeException("Missing coldness for drinks.");
        }

        if (!(coldString.equalsIgnoreCase("true") || coldString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "isCold must be true or false");
            throw new DukeException("isCold must be true or false");
        }
        boolean isCold = Boolean.parseBoolean(coldString);

        String cannedString = FieldParser.extractField(input, "isCanned/", null);
        if (cannedString == null || cannedString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing canned status for drinks.");
            throw new DukeException("Missing canned status for drinks.");
        }

        if (!(cannedString.equalsIgnoreCase("true") || cannedString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "isCanned must be true or false");
            throw new DukeException("isCanned must be true or false");
        }
        boolean isCanned = Boolean.parseBoolean(cannedString);

        logger.log(Level.INFO, "End of processing drinks.");
        return new DrinksParser(expiryDate, brand, flavour, volume, isCold, isCanned);
    }
}