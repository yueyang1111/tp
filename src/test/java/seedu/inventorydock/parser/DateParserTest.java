package seedu.inventorydock.parser;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateParserTest {
    @Test
    public void validateDate_validDate_success() {
        assertDoesNotThrow(() -> DateParser.validateDate("2026-03-20"));
    }

    @Test
    public void validateDate_nullDate_throwsException() {
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> DateParser.validateDate(null));
        assertEquals("Missing expiry date", e.getMessage());
    }

    @Test
    public void validateDate_invalidDate_throwsException() {
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> DateParser.validateDate("20-03-2026"));
        assertEquals("Invalid date. Please use yyyy-M-d.", e.getMessage());
    }
}
