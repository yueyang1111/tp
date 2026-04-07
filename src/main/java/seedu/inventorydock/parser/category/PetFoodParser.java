package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.parser.FieldParser;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses pet food-specific fields from user input.
 */
public class PetFoodParser {
    private static final Logger logger = Logger.getLogger(PetFoodParser.class.getName());

    public final String petType;
    public final String brand;
    public final boolean isDryFood;

    /**
     * Creates a {@code PetFoodParser} object with the parsed pet food details.
     *
     * @param petType Type of pet the food is for.
     * @param brand Brand of the pet food.
     */
    public PetFoodParser(String petType, String brand, boolean isDryFood) {
        this.petType = petType;
        this.brand = brand;
        this.isDryFood = isDryFood;
    }

    /**
     * Parses the pet food-related fields from the given input string.
     *
     * @param input User input containing pet food fields.
     * @return A {@code PetFoodParser} containing the parsed values.
     * @throws InventoryDockException If any required field is missing or invalid.
     */
    public static PetFoodParser parse(String input) throws InventoryDockException {
        assert input != null : "PetFoodParser received null input.";
        logger.log(Level.INFO, "Processing PetFood special fields.");

        String petType = FieldParser.extractField(input, "petType/", "brand/");
        if (petType == null || petType.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing petType for pet food.");
            throw new InventoryDockException("Missing petType for pet food.");
        }

        String brand = FieldParser.extractField(input, "brand/", "isDryFood/");
        if (brand == null || brand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing brand for pet food.");
            throw new InventoryDockException("Missing brand for pet food.");
        }

        String isDryFoodString = FieldParser.extractField(input, "isDryFood/", null);
        if (isDryFoodString == null || isDryFoodString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing isDryFood for pet food.");
            throw new InventoryDockException("Missing isDryFood for pet food.");
        }

        if (!(isDryFoodString.equalsIgnoreCase("true") || isDryFoodString.equalsIgnoreCase("false"))) {
            logger.log(Level.WARNING, "isDryFood must be true or false");
            throw new InventoryDockException("isDryFood must be true or false");
        }
        boolean isDryFood = Boolean.parseBoolean(isDryFoodString);

        logger.log(Level.INFO, "End of processing pet food.");
        return new PetFoodParser(petType, brand, isDryFood);
    }
}
