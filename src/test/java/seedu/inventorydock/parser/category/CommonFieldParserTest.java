package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommonFieldParserTest {
    @Test
    public void parseQuantity_validQuantity_returnsParsedInt() throws Exception {
        int quantity = CommonFieldParser.parseQuantity("123");

        assertEquals(123, quantity);
    }

    @Test
    public void parseQuantity_nullQuantity_throwsException() {
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> CommonFieldParser.parseQuantity(null));
        assertEquals("Missing quantity.", e.getMessage());
    }

    @Test
    public void parseQuantity_emptyQuantity_throwsException() {
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> CommonFieldParser.parseQuantity("   "));
        assertEquals("Missing quantity.", e.getMessage());
    }

    @Test
    public void parseQuantity_nonNumeric_throwsException() {
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> CommonFieldParser.parseQuantity("abc"));
        assertEquals("Quantity must be an integer.", e.getMessage());
    }

    @Test
    public void parseQuantity_zero_throwsException() {
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> CommonFieldParser.parseQuantity("0"));
        assertEquals("Quantity must be a positive integer.", e.getMessage());
    }

    @Test
    public void parseQuantity_negative_throwsException() {
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> CommonFieldParser.parseQuantity("-5"));
        assertEquals("Quantity must be a positive integer.", e.getMessage());
    }

    @Test
    public void parseQuantity_exceedsIntegerMaxValue_throwsException() {
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> CommonFieldParser.parseQuantity("999999999999"));
        assertEquals("Quantity exceeded the max value.", e.getMessage());
    }

    @Test
    public void validateExpiryDate_validDate_success() {
        assertDoesNotThrow(() -> CommonFieldParser.validateExpiryDate("2028-02-29"));
    }

    @Test
    public void validateExpiryDate_nullDate_throwsException() {
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> CommonFieldParser.validateExpiryDate(null));
        assertEquals("Missing expiry date.", e.getMessage());
    }

    @Test
    public void validateExpiryDate_emptyDate_throwsException() {
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> CommonFieldParser.validateExpiryDate("   "));
        assertEquals("Missing expiry date.", e.getMessage());
    }

    @Test
    public void validateExpiryDate_invalidDate_throwsException() {
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> CommonFieldParser.validateExpiryDate("2026-02-30"));
        assertEquals("Invalid date. Please enter a valid calendar date in yyyy-M-d format.", e.getMessage());
    }

    @Test
    public void parse_validInput_returnsParsedFields() throws Exception {
        String input = "category/FRUITS item/apple bin/A-1 qty/10 expiryDate/2026-12-31 size/big isRipe/true";

        CommonFieldParser parser = CommonFieldParser.parse(input, "size/");

        assertEquals("FRUITS", parser.categoryName);
        assertEquals("apple", parser.itemName);
        assertEquals("A-1", parser.bin);
        assertEquals(10, parser.quantity);
        assertEquals("2026-12-31", parser.expiryDate);
    }

    @Test
    public void parse_missingItemName_throwsException() {
        String input = "category/FRUITS item/ bin/A-1 qty/10 expiryDate/2026-12-31 size/big isRipe/true";

        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> CommonFieldParser.parse(input, "size/"));
        assertEquals("Missing item name.", e.getMessage());
    }

    @Test
    public void parse_missingBin_throwsException() {
        String input = "category/FRUITS item/apple bin/ qty/10 expiryDate/2026-12-31 size/big isRipe/true";

        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> CommonFieldParser.parse(input, "size/"));
        assertEquals("Missing bin location.", e.getMessage());
    }

    @Test
    public void parse_missingQuantity_throwsException() {
        String input = "category/FRUITS item/apple bin/A-1 qty/ expiryDate/2026-12-31 size/big isRipe/true";

        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> CommonFieldParser.parse(input, "size/"));
        assertEquals("Missing quantity.", e.getMessage());
    }

    @Test
    public void parse_invalidExpiryDate_throwsException() {
        String input = "category/FRUITS item/apple bin/A-1 qty/10 expiryDate/2026-02-30 size/big isRipe/true";

        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> CommonFieldParser.parse(input, "size/"));
        assertEquals("Invalid date. Please enter a valid calendar date in yyyy-M-d format.", e.getMessage());
    }
}
