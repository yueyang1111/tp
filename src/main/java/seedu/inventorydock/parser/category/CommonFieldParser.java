package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.DukeException;
import seedu.inventorydock.parser.DateParser;
import seedu.inventorydock.parser.FieldParser;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses and validates fields shared by item categories.
 * A <code>CommonFieldParser</code> object stores the parsed values for
 * category name, item name, bin location, quantity, and expiry date.
 */
public class CommonFieldParser {
    private static final Logger logger = Logger.getLogger(CommonFieldParser.class.getName());
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

    /**
     * Parses the shared item fields from the specified input.
     *
     * @param input Input containing the common item fields.
     * @param fieldAfterExpiry Field marker that follows <code>expiryDate/</code>.
     * @return Parser result containing the validated field values.
     * @throws DukeException If any required field is missing or invalid.
     */
    public static CommonFieldParser parse(String input, String fieldAfterExpiry) throws DukeException {
        assert input != null : "CommonFieldParser received null input.";

        String categoryName = FieldParser.extractField(
                input, "category/", "item/");

        String itemName = FieldParser.extractField(
                input, "item/", "bin/");
        if (itemName == null || itemName.isEmpty()) {
            logger.log(Level.WARNING, "Missing item name while parsing common fields.");
            throw new DukeException("Missing item name.");
        }

        String bin = FieldParser.extractField(input, "bin/", "qty/");
        if (bin == null || bin.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing bin location while parsing common fields.");
            throw new DukeException("Missing bin location.");
        }

        String quantityString = FieldParser.extractField(
                input, "qty/", "expiryDate/");
        int quantity = parseQuantity(quantityString);

        String expiryDate = FieldParser.extractField(
                input, "expiryDate/", fieldAfterExpiry);
        validateExpiryDate(expiryDate);

        logger.log(Level.INFO, "Parsed common fields for item '" + itemName
                + "' in category '" + categoryName + "'.");
        return new CommonFieldParser(itemName, categoryName, bin, quantity, expiryDate);
    }

    /**
     * Parses the specified quantity string as a positive integer.
     *
     * @param quantityString Quantity string to parse.
     * @return Parsed quantity.
     * @throws DukeException If the quantity is missing, non-numeric, or not positive.
     */
    public static int parseQuantity(String quantityString) throws DukeException {
        if (quantityString == null
                || quantityString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing quantity while parsing common fields.");
            throw new DukeException("Missing quantity.");
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityString.trim());
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid quantity format: " + quantityString);
            throw new DukeException("Quantity must be an integer.");
        }

        if (quantity <= 0) {
            logger.log(Level.WARNING, "Non-positive quantity encountered: " + quantity);
            throw new DukeException("Quantity must be a positive integer.");
        }

        return quantity;
    }

    /**
     * Validates that the specified expiry date is present and in a valid format.
     *
     * @param expiryDate Expiry date string to validate.
     * @throws DukeException If the expiry date is missing or invalid.
     */
    public static void validateExpiryDate(String expiryDate) throws DukeException {
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing expiry date while parsing common fields.");
            throw new DukeException("Missing expiry date.");
        }
        logger.log(Level.INFO, "Validating expiry date: " + expiryDate);
        DateParser.validateDate(expiryDate);
    }
}
