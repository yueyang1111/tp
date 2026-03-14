package seedu.duke.parser;

import seedu.duke.command.AddItemCommand;
import seedu.duke.command.Command;
import seedu.duke.exception.DukeException;

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

    private void validateOrder(String input, String[] fields) throws DukeException {
        int previous = -1;

        for (String field : fields) {
            int current = input.indexOf(field);

            if (current != -1) {
                if (current < previous) {
                    throw new DukeException("Fields must follow the correct order.");
                }
                previous = current;
            }
        }
    }

    private void parseCommonFields(String input) throws DukeException {
        itemName = FieldParser.extractField(input, "item/", "category/");
        categoryName = FieldParser.extractField(input, "category/", "bin/");

        bin = FieldParser.extractField(input, "bin/", "qty/");
        if (bin == null || bin.trim().isEmpty()) {
            throw new DukeException("Missing bin location.");
        }

        String quantityString = FieldParser.extractField(input, "qty/", null);
        if (quantityString == null || quantityString.trim().isEmpty()) {
            throw new DukeException("Missing quantity.");
        }

        quantityString = quantityString.trim().split(" ", 2)[0];

        try {
            quantity = Integer.parseInt(quantityString);
        } catch (NumberFormatException e) {
            throw new DukeException("Quantity must be an integer.");
        }

        if (quantity <= 0) {
            throw new DukeException("Quantity must be a positive integer.");
        }
    }

    private void parseFruitFields(String input) throws DukeException {
        expiryDate = FieldParser.extractField(input, "expiryDate/", "size/");
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            throw new DukeException("Missing expiry date for fruit.");
        }

        size = FieldParser.extractField(input, "size/", "isRipe/");
        if (size == null || size.trim().isEmpty()) {
            throw new DukeException("Missing size for fruit.");
        }

        String ripeString = FieldParser.extractField(input, "isRipe/", null);
        if (ripeString == null || ripeString.trim().isEmpty()) {
            throw new DukeException("Missing ripeness for fruit.");
        }

        if (!(ripeString.equalsIgnoreCase("true")
                || ripeString.equalsIgnoreCase("false"))) {
            throw new DukeException("Ripeness must be true or false");
        }
        isRipe = Boolean.parseBoolean(ripeString);
    }

    private void parseSnackFields(String input) throws DukeException {
        brand = FieldParser.extractField(input, "brand/", "expiryDate/");
        if (brand == null || brand.trim().isEmpty()) {
            throw new DukeException("Missing brand for snack.");
        }

        expiryDate = FieldParser.extractField(input, "expiryDate/", null);
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            throw new DukeException("Missing expiry date for snack.");
        }
    }

    private void parseToiletriesFields(String input) throws DukeException {
        brand = FieldParser.extractField(input, "brand/", "isLiquid/");
        if (brand == null || brand.trim().isEmpty()) {
            throw new DukeException("Missing brand for toiletries.");
        }

        String liquidString = FieldParser.extractField(input, "isLiquid/", null);
        if (liquidString == null || liquidString.trim().isEmpty()) {
            throw new DukeException("Missing liquid field for toiletries.");
        }

        if (!(liquidString.equalsIgnoreCase("true")
                || liquidString.equalsIgnoreCase("false"))) {
            throw new DukeException("Liquid field must be true or false.");
        }
        isLiquid = Boolean.parseBoolean(liquidString);
    }

    private void parseVegetableFields(String input) throws DukeException {
        expiryDate = FieldParser.extractField(input, "expiryDate/", "isLeafy/");
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            throw new DukeException("Missing expiry date for vegetable.");
        }

        String leafyString = FieldParser.extractField(input, "isLeafy/", null);
        if (leafyString == null || leafyString.trim().isEmpty()) {
            throw new DukeException("Missing leafy field for vegetable.");
        }

        if (!(leafyString.equalsIgnoreCase("true")
                || leafyString.equalsIgnoreCase("false"))) {
            throw new DukeException("Leafy field must be true or false.");
        }
        isLeafy = Boolean.parseBoolean(leafyString);
    }

    private Command buildCommand() {
        return new AddItemCommand(itemName, categoryName, bin, quantity,
                brand, expiryDate, size, isRipe, isLeafy, isLiquid);
    }

    public Command handleFruit(String input) throws DukeException {
        resetFields();
        String[] fruitFields = {"item/", "category/", "bin/", "qty/",
                "expiryDate/", "size/", "isRipe/"};
        validateOrder(input, fruitFields);
        parseCommonFields(input);
        parseFruitFields(input);
        return buildCommand();
    }

    public Command handleSnack(String input) throws DukeException {
        resetFields();
        String[] snackFields = {"item/", "category/", "bin/", "qty/",
                "brand/", "expiryDate/"};
        validateOrder(input, snackFields);
        parseCommonFields(input);
        parseSnackFields(input);
        return buildCommand();
    }

    public Command handleToiletries(String input) throws DukeException {
        resetFields();
        String[] toiletriesFields = {"item/", "category/", "bin/", "qty/",
                "brand/", "isLiquid/"};
        validateOrder(input, toiletriesFields);
        parseCommonFields(input);
        parseToiletriesFields(input);
        return buildCommand();
    }

    public Command handleVegetables(String input) throws DukeException {
        resetFields();
        String[] vegetableFields = {"item/", "category/", "bin/", "qty/",
                "expiryDate/", "isLeafy/"};
        validateOrder(input, vegetableFields);
        parseCommonFields(input);
        parseVegetableFields(input);
        return buildCommand();
    }
}