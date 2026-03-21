package seedu.duke.parser;

import seedu.duke.command.AddItemCommand;
import seedu.duke.command.Command;
import seedu.duke.exception.DukeException;
import seedu.duke.model.Item;
import seedu.duke.model.items.Fruit;
import seedu.duke.model.items.Snack;
import seedu.duke.model.items.Toiletries;
import seedu.duke.model.items.Vegetable;
import seedu.duke.parser.category.CommonFieldParser;
import seedu.duke.parser.category.FruitParser;
import seedu.duke.parser.category.InputValidator;
import seedu.duke.parser.category.SnackParser;
import seedu.duke.parser.category.ToiletriesParser;
import seedu.duke.parser.category.VegetableParser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddItemCommandParser {
    private static final Logger logger = Logger.getLogger(AddItemCommandParser.class.getName());

    public Command handleFruit(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null fruit input.";
        logger.log(Level.FINE, "Parsing add-item command for fruits.");
        InputValidator.validate(input, "item/", "category/", "bin/", "qty/",
                "expiryDate/", "size/", "isRipe/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "expiryDate/");
        FruitParser fruitFields = FruitParser.parse(input);
        Item item = new Fruit(commonFields.itemName, commonFields.quantity, commonFields.bin,
                fruitFields.expiryDate, fruitFields.size, fruitFields.isRipe);

        logger.log(Level.FINE, "Built fruit add command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleSnack(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null snack input.";
        logger.log(Level.FINE, "Parsing add-item command for snacks.");
        InputValidator.validate(input, "item/", "category/", "bin/", "qty/",
                "brand/", "expiryDate/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        SnackParser snackFields = SnackParser.parse(input);
        Item item = new Snack(commonFields.itemName, commonFields.quantity, commonFields.bin,
                snackFields.brand, snackFields.expiryDate);

        logger.log(Level.FINE, "Built snack add command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleToiletries(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null toiletries input.";
        logger.log(Level.FINE, "Parsing add-item command for toiletries.");
        InputValidator.validate(input, "item/", "category/", "bin/", "qty/",
                "brand/", "isLiquid/", "expiryDate/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        ToiletriesParser toiletriesFields = ToiletriesParser.parse(input);
        Item item = new Toiletries(commonFields.itemName, commonFields.quantity, commonFields.bin,
                toiletriesFields.brand, toiletriesFields.isLiquid, toiletriesFields.expiryDate);

        logger.log(Level.FINE, "Built toiletries add command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleVegetables(String input) throws DukeException {
        assert input != null : "AddItemCommandParser received null vegetable input.";
        logger.log(Level.FINE, "Parsing add-item command for vegetables.");
        InputValidator.validate(input, "item/", "category/", "bin/", "qty/",
                "expiryDate/", "isLeafy/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "expiryDate/");
        VegetableParser vegetableFields = VegetableParser.parse(input);
        Item item = new Vegetable(commonFields.itemName, commonFields.quantity, commonFields.bin,
                vegetableFields.expiryDate, vegetableFields.isLeafy);

        logger.log(Level.FINE, "Built vegetable add command for category: " + commonFields.categoryName);
        return new AddItemCommand(commonFields.categoryName, item);
    }
}
