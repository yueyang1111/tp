package seedu.inventorydock.parser.category;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
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
    private static final String INVALID_BIN_LOCATION_MESSAGE =
            "Bin location must be LETTER-NUMBER (e.g. A-10).";
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
            throw new InventoryDockException("Missing item name.");
        }

        String bin = FieldParser.extractField(input, "bin/", "qty/");
        if (bin == null || bin.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing bin location while parsing common fields.");
            throw new InventoryDockException("Missing bin location.");
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
        String trimmedBin = bin.trim();
        int dashIndex = trimmedBin.indexOf('-');

        if (dashIndex == -1 || dashIndex != trimmedBin.lastIndexOf('-')) {
            logger.log(Level.WARNING, "Invalid bin location format: " + trimmedBin);
            throw new InvalidCommandException(INVALID_BIN_LOCATION_MESSAGE);
        }

        String letterPart = trimmedBin.substring(0, dashIndex);
        String numberPart = trimmedBin.substring(dashIndex + 1);
        if (!isSingleLetter(letterPart) || !isInteger(numberPart)) {
            logger.log(Level.WARNING, "Invalid bin location value: " + trimmedBin);
            throw new InvalidCommandException(INVALID_BIN_LOCATION_MESSAGE);
        }

        return trimmedBin;
    }

    /**
     * Parses the specified quantity string as a positive integer.
     *
     * @param quantityString Quantity string to parse.
     * @return Parsed quantity.
     * @throws InventoryDockException If the quantity is missing, non-numeric, or not positive.
     */
    public static int parseQuantity(String quantityString) throws InventoryDockException {
        if (quantityString == null
                || quantityString.trim().isEmpty()) {
            logger.log(Level.WARNING, "Missing quantity while parsing common fields.");
            throw new InventoryDockException("Missing quantity.");
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityString.trim());
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid quantity format: " + quantityString);
            throw new InventoryDockException("Quantity must be an integer.");
        }

        if (quantity <= 0) {
            logger.log(Level.WARNING, "Non-positive quantity encountered: " + quantity);
            throw new InventoryDockException("Quantity must be a positive integer.");
        }

        return quantity;
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
            throw new InventoryDockException("Missing expiry date.");
        }
        logger.log(Level.INFO, "Validating expiry date: " + expiryDate);
        DateParser.validateDate(expiryDate);
    }

    private static boolean isSingleLetter(String input) {
        assert input != null : "isSingleLetter received null input.";
        return input.length() == 1 && Character.isLetter(input.charAt(0));
    }

    private static boolean isInteger(String input) {
        assert input != null : "isInteger received null input.";

        if (input.isEmpty()) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
