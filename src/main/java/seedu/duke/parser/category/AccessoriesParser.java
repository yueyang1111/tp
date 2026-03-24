package seedu.duke.parser.category;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;

public class AccessoriesParser {
    private static final Logger logger = Logger.getLogger(AccessoriesParser.class.getName());

    public final String type;
    public final String material;

    public AccessoriesParser(String type, String material) {
        this.type = type;
        this.material = material;
    }

    public static AccessoriesParser parse(String input) throws DukeException {
        assert input != null : "AccessoriesParser received null input.";
        logger.log(Level.INFO, "Processing Accessories special fields.");

        String type = FieldParser.extractField(input, "type/", "material/");
        if (type == null || type.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing type for accessories.");
            throw new DukeException("Missing type for accessories.");
        }

        String material = FieldParser.extractField(input, "material/", null);
        if (material == null || material.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing material for accessories.");
            throw new DukeException("Missing material for accessories.");
        }

        logger.log(Level.INFO, "End of processing accessories.");
        return new AccessoriesParser(type, material);
    }
}
