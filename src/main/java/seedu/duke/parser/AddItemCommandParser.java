package seedu.duke.parser;

import seedu.duke.command.AddItemCommand;
import seedu.duke.command.Command;

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

    private String[] splitToken(String input) {
        return input.split("/", 2);
    }

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

    private void parseFields(String[] words) {
        for (String token : words) {
            String[] parts = splitToken(token);
            String fieldName = parts[0];
            String fieldValue = parts.length > 1 ? parts[1] : "";

            switch (fieldName) {
            case "item":
                itemName = fieldValue;
                break;
            case "category":
                categoryName = fieldValue;
                break;
            case "bin":
                bin = fieldValue;
                break;
            case "qty":
                quantity = Integer.parseInt(fieldValue);
                break;
            case "brand":
                brand = fieldValue;
                break;
            case "expiryDate":
                expiryDate = fieldValue;
                break;
            case "size":
                size = fieldValue;
                break;
            case "isRipe":
                isRipe = Boolean.parseBoolean(fieldValue);
                break;
            case "isLeafy":
                isLeafy = Boolean.parseBoolean(fieldValue);
                break;
            case "isLiquid":
                isLiquid = Boolean.parseBoolean(fieldValue);
                break;
            default:
                break;
            }
        }
    }

    private Command buildCommand() {
        return new AddItemCommand(itemName, categoryName, bin, quantity,
                brand, expiryDate, size, isRipe, isLeafy, isLiquid);
    }

    public Command handleFruit(String[] words) {
        resetFields();
        parseFields(words);
        return buildCommand();
    }

    public Command handleSnack(String[] words) {
        resetFields();
        parseFields(words);
        return buildCommand();
    }

    public Command handleToiletries(String[] words) {
        resetFields();
        parseFields(words);
        return buildCommand();
    }

    public Command handleVegetables(String[] words) {
        resetFields();
        parseFields(words);
        return buildCommand();
    }
}
