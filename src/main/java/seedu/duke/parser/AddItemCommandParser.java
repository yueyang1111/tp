package seedu.duke.parser;

import seedu.duke.command.AddItemCommand;
import seedu.duke.command.Command;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.category.CommonFieldParser;
import seedu.duke.parser.category.FruitParser;
import seedu.duke.parser.category.InputValidator;
import seedu.duke.parser.category.SnackParser;
import seedu.duke.parser.category.ToiletriesParser;
import seedu.duke.parser.category.VegetableParser;

public class AddItemCommandParser {
    protected String itemName = null;
    protected String categoryName = null;
    protected String bin = null;
    protected int quantity = 0;

    protected String brand = "N/A";
    protected String expiryDate = "N/A";
    protected String size = "N/A";
    protected boolean isRipe = false;
    protected boolean isLeafy = false;
    protected boolean isLiquid = false;

    private void resetFields() {
        itemName = null;
        categoryName = null;
        bin = null;
        quantity = 0;
        brand = "N/A";
        expiryDate = "N/A";
        size = "N/A";
        isRipe = false;
        isLeafy = false;
        isLiquid = false;
    }

    private Command buildCommand() {
        return new AddItemCommand(
                itemName, categoryName, bin, quantity,
                brand, expiryDate, size,
                isRipe, isLeafy, isLiquid);
    }
    public Command handleFruit(
            String input) throws DukeException {
        resetFields();
        InputValidator.validateRequiredFields(input, "item/", "category/", "bin/", "qty/",
                "expiryDate/", "size/", "isRipe/");
        InputValidator.validateOrder(input, "item/", "category/", "bin/", "qty/",
                "expiryDate/", "size/", "isRipe/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "expiryDate/");
        itemName = commonFields.itemName;
        categoryName = commonFields.categoryName;
        bin = commonFields.bin;
        quantity = commonFields.quantity;

        FruitParser fruitFields = FruitParser.parse(input);
        expiryDate = fruitFields.expiryDate;
        size = fruitFields.size;
        isRipe = fruitFields.isRipe;

        return buildCommand();
    }

    public Command handleSnack(
            String input) throws DukeException {
        resetFields();
        InputValidator.validateRequiredFields(input, "item/", "category/", "bin/", "qty/",
                "brand/", "expiryDate/");
        InputValidator.validateOrder(input, "item/", "category/", "bin/", "qty/",
                "brand/", "expiryDate/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        itemName = commonFields.itemName;
        categoryName = commonFields.categoryName;
        bin = commonFields.bin;
        quantity = commonFields.quantity;

        SnackParser snackFields = SnackParser.parse(input);
        brand = snackFields.brand;
        expiryDate = snackFields.expiryDate;

        return buildCommand();
    }

    public Command handleToiletries(
            String input) throws DukeException {
        resetFields();
        InputValidator.validateRequiredFields(input, "item/", "category/", "bin/", "qty/",
                "brand/", "isLiquid/");
        InputValidator.validateOrder(input, "item/", "category/", "bin/", "qty/",
                "brand/", "isLiquid/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "brand/");
        itemName = commonFields.itemName;
        categoryName = commonFields.categoryName;
        bin = commonFields.bin;
        quantity = commonFields.quantity;

        ToiletriesParser toiletriesFields = ToiletriesParser.parse(input);
        brand = toiletriesFields.brand;
        isLiquid = toiletriesFields.isLiquid;

        return buildCommand();
    }

    public Command handleVegetables(
            String input) throws DukeException {
        resetFields();
        InputValidator.validateRequiredFields(input, "item/", "category/", "bin/", "qty/",
                "expiryDate/", "isLeafy/");
        InputValidator.validateOrder(input, "item/", "category/", "bin/", "qty/",
                "expiryDate/", "isLeafy/");

        CommonFieldParser commonFields = CommonFieldParser.parse(input, "expiryDate/");
        itemName = commonFields.itemName;
        categoryName = commonFields.categoryName;
        bin = commonFields.bin;
        quantity = commonFields.quantity;

        VegetableParser vegetableFields = VegetableParser.parse(input);
        expiryDate = vegetableFields.expiryDate;
        isLeafy = vegetableFields.isLeafy;

        return buildCommand();
    }
}
