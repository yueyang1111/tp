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

public class AddItemCommandParser {
    public Command handleFruit(String input) throws DukeException {
        InputValidator.validateRequiredFields(input, "item/", "category/", "bin/", "qty/",
                "expiryDate/", "size/", "isRipe/");
        InputValidator.validateOrder(input, "item/", "category/", "bin/", "qty/",
                "expiryDate/", "size/", "isRipe/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "expiryDate/");
        FruitParser fruitFields = FruitParser.parse(input);
        Item item = new Fruit(commonFields.itemName, commonFields.quantity, commonFields.bin,
                fruitFields.expiryDate, fruitFields.size, fruitFields.isRipe);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleSnack(String input) throws DukeException {
        InputValidator.validateRequiredFields(input, "item/", "category/", "bin/", "qty/",
                "brand/", "expiryDate/");
        InputValidator.validateOrder(input, "item/", "category/", "bin/", "qty/",
                "brand/", "expiryDate/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        SnackParser snackFields = SnackParser.parse(input);
        Item item = new Snack(commonFields.itemName, commonFields.quantity, commonFields.bin,
                snackFields.brand, snackFields.expiryDate);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleToiletries(String input) throws DukeException {
        InputValidator.validateRequiredFields(input, "item/", "category/", "bin/", "qty/",
                "brand/", "isLiquid/");
        InputValidator.validateOrder(input, "item/", "category/", "bin/", "qty/",
                "brand/", "isLiquid/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        ToiletriesParser toiletriesFields = ToiletriesParser.parse(input);
        Item item = new Toiletries(commonFields.itemName, commonFields.quantity, commonFields.bin,
                toiletriesFields.brand, toiletriesFields.isLiquid);

        return new AddItemCommand(commonFields.categoryName, item);
    }

    public Command handleVegetables(String input) throws DukeException {
        InputValidator.validateRequiredFields(input, "item/", "category/", "bin/", "qty/",
                "expiryDate/", "isLeafy/");
        InputValidator.validateOrder(input, "item/", "category/", "bin/", "qty/",
                "expiryDate/", "isLeafy/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "expiryDate/");
        VegetableParser vegetableFields = VegetableParser.parse(input);
        Item item = new Vegetable(commonFields.itemName, commonFields.quantity, commonFields.bin,
                vegetableFields.expiryDate, vegetableFields.isLeafy);

        return new AddItemCommand(commonFields.categoryName, item);
    }
}
