package seedu.inventorydock.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.inventorydock.command.ClearCategoryCommand;
import seedu.inventorydock.command.Command;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClearCommandParserTest {

    private ClearCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new ClearCommandParser();
    }

    @Test
    public void parse_validCategory_returnsClearCategoryCommand() throws InventoryDockException {
        Command result = parser.parse("category/fruits");
        assertInstanceOf(ClearCategoryCommand.class, result);
    }

    @Test
    public void parse_emptyInput_throwsInventoryDockException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse(""));
        assertTrue(exception.getMessage().contains("specify what to clear"));
    }

    @Test
    public void parse_missingCategory_throwsInventoryDockException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse("index/1"));
        assertTrue(exception.getMessage().contains("is not supported"));
    }

    @Test
    public void parse_unknownField_throwsInventoryDockException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse("category/fruits name/apple"));
        assertTrue(exception.getMessage().contains("is not supported"));
    }

    @Test
    public void parse_tokenWithoutSlash_throwsInventoryDockException() {
        assertThrows(InventoryDockException.class,
                () -> parser.parse("fruits"));
    }

    @Test
    public void parse_categoryWithEmptyValue_throwsInventoryDockException() {
        assertThrows(InventoryDockException.class,
                () -> parser.parse("category/"));
    }
}
