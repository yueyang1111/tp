package seedu.inventorydock.parser;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BinLocationParserTest {
    @Test
    public void parseSearchInput_validInputs_returnsNormalizedInput() throws InventoryDockException {
        assertEquals("a-10", BinLocationParser.parseSearchInput("A-10"));
        assertEquals("a", BinLocationParser.parseSearchInput("A"));
        assertEquals("10", BinLocationParser.parseSearchInput("10"));
    }

    @Test
    public void parseSearchInput_invalidInputs_throwsException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> BinLocationParser.parseSearchInput("A10"));
        assertEquals("Bin search must be either LETTER-NUMBER (e.g. A-10), "
                + "LETTER only (e.g. A), or NUMBER only (e.g. 10).",
                exception.getMessage());

        assertThrows(InventoryDockException.class, () -> BinLocationParser.parseSearchInput("AA-10"));
        assertThrows(InventoryDockException.class, () -> BinLocationParser.parseSearchInput("10-A"));
        assertThrows(InventoryDockException.class, () -> BinLocationParser.parseSearchInput("A-10-1"));
    }

}
