package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FruitParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "size/big isRipe/true";
        assertDoesNotThrow(() -> FruitParser.parse(input));
    }

    @Test
    public void parse_missingSize_throwsException() {
        String input = "size/ isRipe/true";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> FruitParser.parse(input));
        assertEquals("Missing size for fruit.", e.getMessage());
    }

    @Test
    public void parse_missingRipeness_throwsException() {
        String input = "size/big isRipe/";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> FruitParser.parse(input));
        assertEquals("Missing ripeness for fruit.", e.getMessage());
    }

    @Test
    public void parse_invalidRipeness_throwsException() {
        String input = "size/big isRipe/hi";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> FruitParser.parse(input));
        assertEquals("Ripeness must be true or false", e.getMessage());
    }
}
