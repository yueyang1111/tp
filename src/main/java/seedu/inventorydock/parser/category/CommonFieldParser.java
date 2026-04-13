package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.parser.BinLocationParser;
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
     * @throws InventoryDockException If any required field is missing or invalid.
     */
    public static CommonFieldParser parse(String input, String fieldAfterExpiry) throws InventoryDockException {
        assert input != null : "CommonFieldParser received null input.";

        String categoryName = FieldParser.extractField(
                input, "category/", "item/");

        String itemName = FieldParser.extractField(
                input, "item/", "bin/");
        if (itemName == null || itemName.isEmpty()) {
            logger.log(Level.WARNING, "Missing item name while parsing common fields.");
            throw new MissingArgumentException("item name is required.");
        }

        String bin = FieldParser.extractField(input, "bin/", "qty/");
        if (bin == null || bin.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing bin location while parsing common fields.");
            throw new MissingArgumentException("bin location is required.");
        }
        bin = parseBinLocation(bin);

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
     * Parses and validates a concrete bin location in LETTER-NUMBER format.
     *
     * @param bin bin location to validate.
     * @return trimmed bin location.
     * @throws InventoryDockException if the bin location format is invalid.
     */
    private static String parseBinLocation(String bin) throws InventoryDockException {
        return BinLocationParser.parseExactInput(bin);
    }

    /**
     * Parses the specified quantity string as a positive integer.
     *
     * @param quantityString Quantity string to parse.
     * @return Parsed quantity.
     * @throws InventoryDockException If the quantity is missing, non-numeric, too large or not positive.
     */
    public static int parseQuantity(String quantityString) throws InventoryDockException {
        if (quantityString == null
                || quantityString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing quantity while parsing common fields.");
            throw new MissingArgumentException("quantity is required.");
        }

        try {
            long parsedQuantity = Long.parseLong(quantityString.trim());

            if (parsedQuantity > Integer.MAX_VALUE) {
                logger.log(Level.WARNING, "Quantity exceeded maximum value: " + parsedQuantity);
                throw new InvalidCommandException("quantity exceeded the maximum supported value.");
            }

            if (parsedQuantity <= 0) {
                logger.log(Level.WARNING, "Non-positive quantity encountered: " + parsedQuantity);
                throw new InvalidCommandException("quantity must be a positive integer.");
            }

            return (int) parsedQuantity;
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid quantity format: " + quantityString);
            throw new InvalidCommandException("quantity must be an integer.");
        }
    }

    /**
     * Validates that the specified expiry date is present and in a valid format.
     *
     * @param expiryDate Expiry date string to validate.
     * @throws InventoryDockException If the expiry date is missing or invalid.
     */
    public static void validateExpiryDate(String expiryDate) throws InventoryDockException {
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing expiry date while parsing common fields.");
            throw new MissingArgumentException("expiry date is required.");
        }
        logger.log(Level.INFO, "Validating expiry date: " + expiryDate);
        DateParser.validateDate(expiryDate);
    }

}
