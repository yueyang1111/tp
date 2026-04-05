package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.DukeException;
import seedu.inventorydock.parser.FieldParser;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses set meal-specific fields from user input.
 */
public class SetMealParser {
    private static final Logger logger = Logger.getLogger(SetMealParser.class.getName());

    public final String mealType;
    public final String foodSize;
    public final boolean hasDrinks;

    /**
     * Creates a {@code SetMealParser} object with the parsed set meal details.
     *
     * @param mealType Type of the set meal.
     * @param foodSize Food size of the set meal.
     */
    public SetMealParser(String mealType, String foodSize, boolean hasDrinks) {
        this.mealType = mealType;
        this.foodSize = foodSize;
        this.hasDrinks = hasDrinks;
    }

    /**
     * Parses the set meal-related fields from the given input string.
     *
     * @param input User input containing set meal fields.
     * @return A {@code SetMealParser} containing the parsed values.
     * @throws DukeException If any required field is missing or invalid.
     */
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
