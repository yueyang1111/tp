package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IceCreamParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "flavour/Vanilla isDairyFree/false";
        assertDoesNotThrow(() -> IceCreamParser.parse(input));
    }

    @Test
    public void parse_missingFlavour_throwsException() {
        String input = "flavour/ isDairyFree/false";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> IceCreamParser.parse(input));
        assertEquals("Missing flavour for ice cream.", e.getMessage());
    }

    @Test
    public void parse_invalidBoolean_throwsException() {
        String input = "flavour/Vanilla isDairyFree/maybe";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> IceCreamParser.parse(input));
        assertEquals("isDairyFree must be true or false", e.getMessage());
    }
}
