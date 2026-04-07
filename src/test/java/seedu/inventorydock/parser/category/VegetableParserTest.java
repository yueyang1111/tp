package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VegetableParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "isLeafy/true";
        assertDoesNotThrow(() -> VegetableParser.parse(input));
    }

    @Test
    public void parse_missingRipeness_throwsException() {
        String input = "isLeafy/";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> VegetableParser.parse(input));
        assertEquals("Missing leafy field for vegetable.", e.getMessage());
    }

    @Test
    public void parse_invalidRipeness_throwsException() {
        String input = "isLeafy/hi";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> VegetableParser.parse(input));
        assertEquals("Leafy field must be true or false.", e.getMessage());
    }
}
