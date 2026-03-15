package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.FieldParser;

public class CommonFieldParser {
    public final String itemName;
    public final String categoryName;
    public final String bin;
    public final int quantity;

    private CommonFieldParser(String itemName, String categoryName,
                              String bin, int quantity) {
        this.itemName = itemName;
        this.categoryName = categoryName;
        this.bin = bin;
        this.quantity = quantity;
    }

    public static CommonFieldParser parse(String input, String fieldAfterQty) throws DukeException {
        assert input != null : "CommonFieldParser received null input.";

        String itemName = FieldParser.extractField(
                input, "item/", "category/");
        String categoryName = FieldParser.extractField(
                input, "category/", "bin/");

        String bin = FieldParser.extractField(input, "bin/", "qty/");
        if (bin == null || bin.trim().isEmpty()) {
            throw new DukeException("Missing bin location.");
        }

        String quantityString = FieldParser.extractField(
                input, "qty/", fieldAfterQty);
        if (quantityString == null
                || quantityString.trim().isEmpty()) {
            throw new DukeException("Missing quantity.");
        }

        quantityString = quantityString.trim().split(" ", 2)[0];

        int quantity;
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (NumberFormatException e) {
            throw new DukeException(
                    "Quantity must be an integer.");
        }

        if (quantity <= 0) {
            throw new DukeException(
                    "Quantity must be a positive integer.");
        }

        return new CommonFieldParser(itemName, categoryName, bin, quantity);
    }
}
