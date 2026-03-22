package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PetFoodParser {
    private static final Logger logger = Logger.getLogger(PetFoodParser.class.getName());

    public final String expiryDate;
    public final String petType;
    public final String brand;

    public PetFoodParser(String expiryDate, String petType, String brand) {
        this.expiryDate = expiryDate;
        this.petType = petType;
        this.brand = brand;
    }

    public static PetFoodParser parse(String input) throws DukeException {
        assert input != null : "PetFoodParser received null input.";
        logger.log(Level.INFO, "Processing PetFood special fields.");

        String expiryDate = FieldParser.extractField(input, "expiryDate/", "petType/");
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing expiry date for pet food.");
            throw new DukeException("Missing expiry date for pet food.");
        }
        DateParser.validateDate(expiryDate);

        String petType = FieldParser.extractField(input, "petType/", "brand/");
        if (petType == null || petType.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing petType for pet food.");
            throw new DukeException("Missing petType for pet food.");
        }

        String brand = FieldParser.extractField(input, "brand/", null);
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for pet food.");
            throw new DukeException("Missing brand for pet food.");
        }

        logger.log(Level.INFO, "End of processing pet food.");
        return new PetFoodParser(expiryDate, petType, brand);
    }
}