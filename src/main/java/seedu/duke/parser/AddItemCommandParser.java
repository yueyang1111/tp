package seedu.duke.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.duke.command.AddItemCommand;
import seedu.duke.command.Command;
import seedu.duke.exception.DukeException;
import seedu.duke.model.Item;
import seedu.duke.model.items.Accessories;
import seedu.duke.model.items.Burger;
import seedu.duke.model.items.Drinks;
import seedu.duke.model.items.Fruit;
import seedu.duke.model.items.IceCream;
import seedu.duke.model.items.Meat;
import seedu.duke.model.items.PetFood;
import seedu.duke.model.items.Seafood;
import seedu.duke.model.items.SetMeal;
import seedu.duke.model.items.Snack;
import seedu.duke.model.items.Sweets;
import seedu.duke.model.items.Toiletries;
import seedu.duke.model.items.Vegetable;
import seedu.duke.parser.category.AccessoriesParser;
import seedu.duke.parser.category.BurgerParser;
import seedu.duke.parser.category.CommonFieldParser;
import seedu.duke.parser.category.DrinksParser;
import seedu.duke.parser.category.FruitParser;
import seedu.duke.parser.category.IceCreamParser;
import seedu.duke.parser.category.InputValidator;
import seedu.duke.parser.category.MeatParser;
import seedu.duke.parser.category.PetFoodParser;
import seedu.duke.parser.category.SeafoodParser;
import seedu.duke.parser.category.SetMealParser;
import seedu.duke.parser.category.SnackParser;
import seedu.duke.parser.category.SweetsParser;
import seedu.duke.parser.category.ToiletriesParser;
import seedu.duke.parser.category.VegetableParser;


public class AddItemCommandParser {
    private static final Logger logger = Logger.getLogger(AddItemCommandParser.class.getName());

    public Command handleFruit(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null fruit input.";
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "size/", "isRipe/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "size/");
        FruitParser fruitFields = FruitParser.parse(input);
        Item item = new Fruit(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, fruitFields.size, fruitFields.isRipe);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleSnack(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null snack input.";
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "brand/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        SnackParser snackFields = SnackParser.parse(input);
        Item item = new Snack(commonFields.itemName, commonFields.quantity, commonFields.bin,
                snackFields.brand, commonFields.expiryDate);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleToiletries(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null toiletries input.";
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "brand/", "isLiquid/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        ToiletriesParser toiletriesFields = ToiletriesParser.parse(input);
        Item item = new Toiletries(commonFields.itemName, commonFields.quantity, commonFields.bin,
                toiletriesFields.brand, toiletriesFields.isLiquid, commonFields.expiryDate);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleVegetables(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null vegetable input.";
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "isLeafy/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "isLeafy/");
        VegetableParser vegetableFields = VegetableParser.parse(input);
        Item item = new Vegetable(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, vegetableFields.isLeafy);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleDrinks(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null drinks input.";
        logger.log(Level.INFO, "Parsing add-item command for drinks.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "brand/", "flavour/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        DrinksParser drinksFields = DrinksParser.parse(input);
        Item item = new Drinks(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, drinksFields.brand, drinksFields.flavour);

        logger.log(Level.INFO, "Created drinks item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleIceCream(String input) throws DukeException {
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

    public Command handleSweets(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null sweets input.";
        logger.log(Level.INFO, "Parsing add-item command for sweets.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "brand/", "sweetnessLevel/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        SweetsParser sweetsFields = SweetsParser.parse(input);
        Item item = new Sweets(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, sweetsFields.brand, sweetsFields.sweetnessLevel);

        logger.log(Level.INFO, "Created sweets item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleSetMeal(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null set meal input.";
        logger.log(Level.INFO, "Parsing add-item command for set meal.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "mealType/", "foodSize/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "mealType/");
        SetMealParser setMealFields = SetMealParser.parse(input);
        Item item = new SetMeal(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, setMealFields.mealType, setMealFields.foodSize);

        logger.log(Level.INFO, "Created set meal item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleSeafood(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null seafood input.";
        logger.log(Level.INFO, "Parsing add-item command for seafood.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "seafoodType/", "origin/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "seafoodType/");
        SeafoodParser seafoodFields = SeafoodParser.parse(input);
        Item item = new Seafood(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, seafoodFields.seafoodType, seafoodFields.origin);

        logger.log(Level.INFO, "Created seafood item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleMeat(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null meat input.";
        logger.log(Level.INFO, "Parsing add-item command for meat.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "meatType/", "origin/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "meatType/");
        MeatParser meatFields = MeatParser.parse(input);
        Item item = new Meat(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, meatFields.meatType, meatFields.origin);

        logger.log(Level.INFO, "Created meat item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handlePetFood(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null pet food input.";
        logger.log(Level.INFO, "Parsing add-item command for pet food.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "petType/", "brand/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "petType/");
        PetFoodParser petFoodFields = PetFoodParser.parse(input);
        Item item = new PetFood(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, petFoodFields.petType, petFoodFields.brand);

        logger.log(Level.INFO, "Created pet food item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleAccessories(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null accessories input.";
        logger.log(Level.INFO, "Parsing add-item command for accessories.");
        InputValidator.validate(input, "category/", "item/", "bin/", "qty/",
                "expiryDate/", "type/", "material/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "type/");
        AccessoriesParser accessoriesFields = AccessoriesParser.parse(input);
        Item item = new Accessories(commonFields.itemName, commonFields.quantity, commonFields.bin,
                commonFields.expiryDate, accessoriesFields.type, accessoriesFields.material);

        logger.log(Level.INFO, "Created accessories item command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }


    public Command handleBurger(String input) throws DukeException {
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
