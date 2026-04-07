package seedu.inventorydock.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.inventorydock.command.AddItemCommand;
import seedu.inventorydock.command.Command;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.model.items.Accessories;
import seedu.inventorydock.model.items.Burger;
import seedu.inventorydock.model.items.Drinks;
import seedu.inventorydock.model.items.Fruit;
import seedu.inventorydock.model.items.IceCream;
import seedu.inventorydock.model.items.Meat;
import seedu.inventorydock.model.items.PetFood;
import seedu.inventorydock.model.items.Seafood;
import seedu.inventorydock.model.items.SetMeal;
import seedu.inventorydock.model.items.Snack;
import seedu.inventorydock.model.items.Sweets;
import seedu.inventorydock.model.items.Toiletries;
import seedu.inventorydock.model.items.Vegetable;
import seedu.inventorydock.parser.category.AccessoriesParser;
import seedu.inventorydock.parser.category.BurgerParser;
import seedu.inventorydock.parser.category.CommonFieldParser;
import seedu.inventorydock.parser.category.DrinksParser;
import seedu.inventorydock.parser.category.FruitParser;
import seedu.inventorydock.parser.category.IceCreamParser;
import seedu.inventorydock.parser.category.InputValidator;
import seedu.inventorydock.parser.category.MeatParser;
import seedu.inventorydock.parser.category.PetFoodParser;
import seedu.inventorydock.parser.category.SeafoodParser;
import seedu.inventorydock.parser.category.SetMealParser;
import seedu.inventorydock.parser.category.SnackParser;
import seedu.inventorydock.parser.category.SweetsParser;
import seedu.inventorydock.parser.category.ToiletriesParser;
import seedu.inventorydock.parser.category.VegetableParser;

/**
 * Converts validated add-item arguments into category-specific {@link Item}
 * instances wrapped in {@link AddItemCommand} objects.
 */
public class AddItemCommandParser {
    private static final Logger logger = Logger.getLogger(AddItemCommandParser.class.getName());

    /**
     * Parses a fruit add-item command.
     *
     * @param input raw add-item arguments for the fruit workflow.
     * @return add-item command containing a {@link Fruit}.
     * @throws InventoryDockException if any required field is missing or malformed.
     */
    public Command handleFruit(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null fruit input.";
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "size/", "isRipe/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "size/");
        FruitParser fruitFields = FruitParser.parse(input);
        Item item = new Fruit(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, fruitFields.size, fruitFields.isRipe);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    /**
     * Parses a snack add-item command.
     *
     * @param input raw add-item arguments for the snack workflow.
     * @return add-item command containing a {@link Snack}.
     * @throws InventoryDockException if any required field is missing or malformed.
     */
    public Command handleSnack(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null snack input.";
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "brand/", "isCrunchy/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        SnackParser snackFields = SnackParser.parse(input);
        Item item = new Snack(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, snackFields.brand, snackFields.isCrunchy);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    /**
     * Parses a toiletries add-item command.
     *
     * @param input raw add-item arguments for the toiletries workflow.
     * @return add-item command containing a {@link Toiletries}.
     * @throws InventoryDockException if any required field is missing or malformed.
     */
    public Command handleToiletries(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null toiletries input.";
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "brand/", "isLiquid/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        ToiletriesParser toiletriesFields = ToiletriesParser.parse(input);
        Item item = new Toiletries(commonFields.itemName, commonFields.quantity, commonFields.bin,
                toiletriesFields.brand, toiletriesFields.isLiquid, commonFields.expiryDate);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    /**
     * Parses a vegetable add-item command.
     *
     * @param input raw add-item arguments for the vegetable workflow.
     * @return add-item command containing a {@link Vegetable}.
     * @throws InventoryDockException if any required field is missing or malformed.
     */
    public Command handleVegetables(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null vegetable input.";
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "isLeafy/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "isLeafy/");
        VegetableParser vegetableFields = VegetableParser.parse(input);
        Item item = new Vegetable(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, vegetableFields.isLeafy);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    /**
     * Parses a drinks add-item command.
     *
     * @param input raw add-item arguments for the drinks workflow.
     * @return add-item command containing a {@link Drinks}.
     * @throws InventoryDockException if any required field is missing or malformed.
     */
    public Command handleDrinks(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null drinks input.";
        logger.log(Level.INFO, "Parsing add-item command for drinks.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "brand/", "flavour/", "isCarbonated/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        DrinksParser drinksFields = DrinksParser.parse(input);
        Item item = new Drinks(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, drinksFields.brand, drinksFields.flavour, drinksFields.isCarbonated);

        logger.log(Level.INFO, "Created drinks item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    /**
     * Parses an ice cream add-item command.
     *
     * @param input raw add-item arguments for the ice cream workflow.
     * @return add-item command containing an {@link IceCream}.
     * @throws InventoryDockException if any required field is missing or malformed.
     */
    public Command handleIceCream(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null ice cream input.";
        logger.log(Level.INFO, "Parsing add-item command for ice cream.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "flavour/", "isDairyFree/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "flavour/");
        IceCreamParser iceCreamFields = IceCreamParser.parse(input);
        Item item = new IceCream(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, iceCreamFields.flavour, iceCreamFields.isDairyFree);

        logger.log(Level.INFO, "Created ice cream item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    /**
     * Parses a sweets add-item command.
     *
     * @param input raw add-item arguments for the sweets workflow.
     * @return add-item command containing a {@link Sweets}.
     * @throws InventoryDockException if any required field is missing or malformed.
     */
    public Command handleSweets(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null sweets input.";
        logger.log(Level.INFO, "Parsing add-item command for sweets.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "brand/", "sweetnessLevel/", "isChewy/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        SweetsParser sweetsFields = SweetsParser.parse(input);
        Item item = new Sweets(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, sweetsFields.brand, sweetsFields.sweetnessLevel, sweetsFields.isChewy);

        logger.log(Level.INFO, "Created sweets item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    /**
     * Parses a set meal add-item command.
     *
     * @param input raw add-item arguments for the set meal workflow.
     * @return add-item command containing a {@link SetMeal}.
     * @throws InventoryDockException if any required field is missing or malformed.
     */
    public Command handleSetMeal(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null set meal input.";
        logger.log(Level.INFO, "Parsing add-item command for set meal.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "mealType/", "foodSize/", "hasDrinks/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "mealType/");
        SetMealParser setMealFields = SetMealParser.parse(input);
        Item item = new SetMeal(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, setMealFields.mealType, setMealFields.foodSize, setMealFields.hasDrinks);

        logger.log(Level.INFO, "Created set meal item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    /**
     * Parses a seafood add-item command.
     *
     * @param input raw add-item arguments for the seafood workflow.
     * @return add-item command containing a {@link Seafood}.
     * @throws InventoryDockException if any required field is missing or malformed.
     */
    public Command handleSeafood(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null seafood input.";
        logger.log(Level.INFO, "Parsing add-item command for seafood.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "seafoodType/", "origin/", "isFrozen/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "seafoodType/");
        SeafoodParser seafoodFields = SeafoodParser.parse(input);
        Item item = new Seafood(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, seafoodFields.seafoodType, seafoodFields.origin, seafoodFields.isFrozen);

        logger.log(Level.INFO, "Created seafood item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    /**
     * Parses a meat add-item command.
     *
     * @param input raw add-item arguments for the meat workflow.
     * @return add-item command containing a {@link Meat}.
     * @throws InventoryDockException if any required field is missing or malformed.
     */
    public Command handleMeat(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null meat input.";
        logger.log(Level.INFO, "Parsing add-item command for meat.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "meatType/", "origin/", "isFrozen/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "meatType/");
        MeatParser meatFields = MeatParser.parse(input);
        Item item = new Meat(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, meatFields.meatType, meatFields.origin, meatFields.isFrozen);

        logger.log(Level.INFO, "Created meat item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    /**
     * Parses a pet food add-item command.
     *
     * @param input raw add-item arguments for the pet food workflow.
     * @return add-item command containing a {@link PetFood}.
     * @throws InventoryDockException if any required field is missing or malformed.
     */
    public Command handlePetFood(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null pet food input.";
        logger.log(Level.INFO, "Parsing add-item command for pet food.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "petType/", "brand/", "isDryFood/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "petType/");
        PetFoodParser petFoodFields = PetFoodParser.parse(input);
        Item item = new PetFood(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, petFoodFields.petType, petFoodFields.brand, petFoodFields.isDryFood);

        logger.log(Level.INFO, "Created pet food item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    /**
     * Parses an accessories add-item command.
     *
     * @param input raw add-item arguments for the accessories workflow.
     * @return add-item command containing an {@link Accessories}.
     * @throws InventoryDockException if any required field is missing or malformed.
     */
    public Command handleAccessories(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null accessories input.";
        logger.log(Level.INFO, "Parsing add-item command for accessories.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "type/", "material/", "isFragile/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "type/");
        AccessoriesParser accessoriesFields = AccessoriesParser.parse(input);
        Item item = new Accessories(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, accessoriesFields.type, accessoriesFields.material,
                accessoriesFields.isFragile);

        logger.log(Level.INFO, "Created accessories item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    /**
     * Parses a burger add-item command.
     *
     * @param input raw add-item arguments for the burger workflow.
     * @return add-item command containing a {@link Burger}.
     * @throws InventoryDockException if any required field is missing or malformed.
     */
    public Command handleBurger(String input) throws InventoryDockException {
        assert input != null : "AddItemCommandParser received null burger input.";
        logger.log(Level.INFO, "Parsing add-item command for burger.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "isSpicy/", "pattyType/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "isSpicy/");
        BurgerParser burgerFields = BurgerParser.parse(input);
        Item item = new Burger(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, burgerFields.isSpicy, burgerFields.pattyType);

        logger.log(Level.INFO, "Created burger item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }
}
