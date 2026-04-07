package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ToiletriesParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "brand/Dove isLiquid/true";
        assertDoesNotThrow(() -> ToiletriesParser.parse(input));
    }

    @Test
    public void parse_missingBrand_throwsException() {
        String input = "brand/ isLiquid/true";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> ToiletriesParser.parse(input));
        assertEquals("Missing brand for toiletries.", e.getMessage());
    }

    @Test
    public void parse_missingLiquid_throwsException() {
        String input = "brand/Dove isLiquid/";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> ToiletriesParser.parse(input));
        assertEquals("Missing liquid field for toiletries.", e.getMessage());
    }

    @Test
    public void parse_invalidLiquid_throwsException() {
        String input = "brand/Dove isLiquid/hi";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> ToiletriesParser.parse(input));
        assertEquals("Liquid field must be true or false.", e.getMessage());
    }
}
