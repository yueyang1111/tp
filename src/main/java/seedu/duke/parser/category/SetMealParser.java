package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SetMealParser {
    private static final Logger logger = Logger.getLogger(SetMealParser.class.getName());

    public final String expiryDate;
    public final String mealType;
    public final String foodSize;
    public final int minToUnfreeze;
    public final boolean isSpicy;

    public SetMealParser(String expiryDate, String mealType, String foodSize,
                         int minToUnfreeze, boolean isSpicy) {
        this.expiryDate = expiryDate;
        this.mealType = mealType;
        this.foodSize = foodSize;
        this.minToUnfreeze = minToUnfreeze;
        this.isSpicy = isSpicy;
    }

    public static SetMealParser parse(String input) throws DukeException {
        assert input != null : "SetMealParser received null input.";
        logger.log(Level.INFO, "Processing SetMeal special fields.");

        String expiryDate = FieldParser.extractField(input, "expiryDate/", "mealType/");
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing expiry date for set meal.");
            throw new DukeException("Missing expiry date for set meal.");
        }
        DateParser.validateDate(expiryDate);

        String mealType = FieldParser.extractField(input, "mealType/", "foodSize/");
        if (mealType == null || mealType.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing mealType for set meal.");
            throw new DukeException("Missing mealType for set meal.");
        }

        String foodSize = FieldParser.extractField(input, "foodSize/", "minToUnfreeze/");
        if (foodSize == null || foodSize.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing foodSize for set meal.");
            throw new DukeException("Missing foodSize for set meal.");
        }

        String minToUnfreezeString = FieldParser.extractField(input, "minToUnfreeze/", "isSpicy/");
        if (minToUnfreezeString == null || minToUnfreezeString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing minToUnfreeze for set meal.");
            throw new DukeException("Missing minToUnfreeze for set meal.");
        }

        int minToUnfreeze;
        try {
            minToUnfreeze = Integer.parseInt(minToUnfreezeString.trim());
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "minToUnfreeze must be an integer.");
            throw new DukeException("minToUnfreeze must be an integer.");
        }

        String spicyString = FieldParser.extractField(input, "isSpicy/", null);
        if (spicyString == null || spicyString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing isSpicy for set meal.");
            throw new DukeException("Missing isSpicy for set meal.");
        }

        if (!(spicyString.equalsIgnoreCase("true") || spicyString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "isSpicy must be true or false");
            throw new DukeException("isSpicy must be true or false");
        }
        boolean isSpicy = Boolean.parseBoolean(spicyString);

        logger.log(Level.INFO, "End of processing set meal.");
        return new SetMealParser(expiryDate, mealType, foodSize, minToUnfreeze, isSpicy);
    }
}