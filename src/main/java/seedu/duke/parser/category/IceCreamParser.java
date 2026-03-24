package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IceCreamParser {
    private static Logger logger = Logger.getLogger(IceCreamParser.class.getName());

    public final String flavour;
    public final boolean isDairyFree;

    public IceCreamParser(String flavour, boolean isDairyFree) {
        this.flavour = flavour;
        this.isDairyFree = isDairyFree;
    }

    public static IceCreamParser parse(String input) throws DukeException {
        assert input != null : "IceCreamParser received null input.";
        logger.log(Level.INFO, "Processing IceCream special fields.");

        String flavour = FieldParser.extractField(input, "flavour/", "isDairyFree/");
        if (flavour == null || flavour.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing flavour for ice cream.");
            throw new DukeException("Missing flavour for ice cream.");
        }

        String dairyFreeString = FieldParser.extractField(input, "isDairyFree/", null);
        if (dairyFreeString == null || dairyFreeString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing dairy-free status for ice cream.");
            throw new DukeException("Missing dairy-free status for ice cream.");
        }

        if (!(dairyFreeString.equalsIgnoreCase("true") || dairyFreeString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "isDairyFree must be true or false");
            throw new DukeException("isDairyFree must be true or false");
        }
        boolean isDairyFree = Boolean.parseBoolean(dairyFreeString);

        logger.log(Level.INFO, "End of processing ice cream.");
        return new IceCreamParser(flavour, isDairyFree);
    }
}
