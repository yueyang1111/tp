package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SetMealParser {
    private static final Logger logger = Logger.getLogger(SetMealParser.class.getName());

    public final String mealType;
    public final String foodSize;

    public SetMealParser(String mealType, String foodSize) {
        this.mealType = mealType;
        this.foodSize = foodSize;
    }

    public static SetMealParser parse(String input) throws DukeException {
        assert input != null : "SetMealParser received null input.";
        logger.log(Level.INFO, "Processing SetMeal special fields.");

        String mealType = FieldParser.extractField(input, "mealType/", "foodSize/");
        if (mealType == null || mealType.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing mealType for set meal.");
            throw new DukeException("Missing mealType for set meal.");
        }

        String foodSize = FieldParser.extractField(input, "foodSize/", null);
        if (foodSize == null || foodSize.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing foodSize for set meal.");
            throw new DukeException("Missing foodSize for set meal.");
        }

        logger.log(Level.INFO, "End of processing set meal.");
        return new SetMealParser(mealType, foodSize);
    }
}
