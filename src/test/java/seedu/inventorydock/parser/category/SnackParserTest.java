package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SnackParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "brand/Lays, isCrunchy/true";
        assertDoesNotThrow(() -> SnackParser.parse(input));
    }

    @Test
    public void parse_missingBrand_throwsException() {
        String input = "brand/";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> SnackParser.parse(input));
        assertEquals("Missing brand for snack.", e.getMessage());
    }

    @Test
    public void parse_missingCrunchiness_throwsException() {
        String input = "brand/Lays isCrunchy/";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> SnackParser.parse(input));
        assertEquals("Missing crunchiness for snack.", e.getMessage());
    }
}

