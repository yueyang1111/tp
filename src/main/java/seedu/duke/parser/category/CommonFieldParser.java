package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;
import seedu.duke.parser.DateParser;
import seedu.duke.parser.FieldParser;

public class CommonFieldParser {
    public final String itemName;
    public final String categoryName;
    public final String bin;
    public final int quantity;
    public final String expiryDate;

    private CommonFieldParser(String itemName, String categoryName,
                              String bin, int quantity, String expiryDate) {
        this.itemName = itemName;
        this.categoryName = categoryName;
        this.bin = bin;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public static CommonFieldParser parse(String input, String fieldAfterExpiry) throws DukeException {
        assert input != null : "CommonFieldParser received null input.";

        String categoryName = FieldParser.extractField(
                input, "category/", "item/");

        String itemName = FieldParser.extractField(
                input, "item/", "bin/");
        if (itemName == null || itemName.isEmpty()) {
            throw new DukeException("Missing item name.");
        }

        String bin = FieldParser.extractField(input, "bin/", "qty/");
        if (bin == null || bin.trim().isEmpty()) {
            throw new DukeException("Missing bin location.");
        }

        String quantityString = FieldParser.extractField(
                input, "qty/", "expiryDate/");
        int quantity = parseQuantity(quantityString);

        String expiryDate = FieldParser.extractField(
                input, "expiryDate/", fieldAfterExpiry);
        validateExpiryDate(expiryDate);

        return new CommonFieldParser(itemName, categoryName, bin, quantity, expiryDate);
    }

    public static int parseQuantity(String quantityString) throws DukeException {
        if (quantityString == null
                || quantityString.trim().isEmpty()) {
            throw new DukeException("Missing quantity.");
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityString.trim());
        } catch (NumberFormatException e) {
            throw new DukeException("Quantity must be an integer.");
        }

        if (quantity <= 0) {
            throw new DukeException("Quantity must be a positive integer.");
        }

        return quantity;
    }

    public static void validateExpiryDate(String expiryDate) throws DukeException {
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            throw new DukeException("Missing expiry date.");
        }
        DateParser.validateDate(expiryDate);
    }
}
