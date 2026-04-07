package seedu.duke.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.inventorydock.command.Command;
import seedu.inventorydock.command.DeleteCategoryCommand;
import seedu.inventorydock.command.DeleteItemCommand;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.parser.DeleteCommandParser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteCommandParserTest {

    private DeleteCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new DeleteCommandParser();
    }

    @Test
    public void parse_categoryAndIndex_returnsDeleteItemCommand() throws InventoryDockException {
        Command result = parser.parse("category/fruits index/2");
        assertInstanceOf(DeleteItemCommand.class, result);
    }

    @Test
    public void parse_categoryOnly_returnsDeleteCategoryCommand() throws InventoryDockException {
        Command result = parser.parse("category/fruits");
        assertInstanceOf(DeleteCategoryCommand.class, result);
    }

    @Test
    public void parse_emptyInput_throwsInventoryDockException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse(""));
        assertTrue(exception.getMessage().contains("specify what to delete"));
    }

    @Test
    public void parse_missingCategory_throwsInventoryDockException() {
        assertThrows(InventoryDockException.class,
                () -> parser.parse("index/1"));
    }

    @Test
    public void parse_unknownField_throwsInventoryDockException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse("category/fruits name/apple"));
        assertTrue(exception.getMessage().contains("Unknown field"));
    }

    @Test
    public void parse_tokenWithoutSlash_throwsInventoryDockException() {
        assertThrows(InventoryDockException.class,
                () -> parser.parse("fruits"));
    }

    @Test
    public void parse_nonIntegerIndex_throwsInventoryDockException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse("category/fruits index/abc"));
        assertTrue(exception.getMessage().contains("integer"));
    }

    @Test
    public void parse_zeroIndex_throwsInventoryDockException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse("category/fruits index/0"));
        assertTrue(exception.getMessage().contains("positive"));
    }

    @Test
    public void parse_negativeIndex_throwsInventoryDockException() {
        assertThrows(InventoryDockException.class,
                () -> parser.parse("category/fruits index/-1"));
    }

    @Test
    public void parse_slashAtEnd_throwsInventoryDockException() {
        assertThrows(InventoryDockException.class,
                () -> parser.parse("category/"));
    }
}
