package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SetMealParser {
    private static final Logger logger = Logger.getLogger(SetMealParser.class.getName());

    public final String mealType;
    public final String foodSize;
    public final boolean hasDrinks;

    public SetMealParser(String mealType, String foodSize, boolean hasDrinks) {
        this.mealType = mealType;
        this.foodSize = foodSize;
        this.hasDrinks = hasDrinks;
    }

    public static SetMealParser parse(String input) throws DukeException {
        assert input != null : "SetMealParser received null input.";
        logger.log(Level.INFO, "Processing SetMeal special fields.");

        String mealType = FieldParser.extractField(input, "mealType/", "foodSize/");
        if (mealType == null || mealType.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing mealType for set meal.");
            throw new DukeException("Missing mealType for set meal.");
        }

        String foodSize = FieldParser.extractField(input, "foodSize/", "hasDrinks/");
        if (foodSize == null || foodSize.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing foodSize for set meal.");
            throw new DukeException("Missing foodSize for set meal.");
        }

        String hasDrinksString = FieldParser.extractField(input, "hasDrinks/", null);
        if (hasDrinksString == null || hasDrinksString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing hasDrinks for set meal.");
            throw new DukeException("Missing hasDrinks for set meal.");
        }

        if (!(hasDrinksString.equalsIgnoreCase("true") || hasDrinksString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "hasDrinks must be true or false");
            throw new DukeException("hasDrinks must be true or false");
        }
        boolean hasDrinks = Boolean.parseBoolean(hasDrinksString);

        logger.log(Level.INFO, "End of processing set meal.");
        return new SetMealParser(mealType, foodSize, hasDrinks);
    }
}
