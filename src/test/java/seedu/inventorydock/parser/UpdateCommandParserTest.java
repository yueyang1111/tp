package seedu.inventorydock;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.command.UpdateItemCommand;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.parser.UpdateCommandParser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateCommandParserTest {
    @Test
    public void parse_validInput_returnsUpdateItemCommand() throws InventoryDockException {
        UpdateCommandParser parser = new UpdateCommandParser();

        assertInstanceOf(UpdateItemCommand.class, parser.parse(
                "category/vegetables index/1 qty/25"));
    }

    @Test
    public void parse_missingCategory_throwsException() {
        UpdateCommandParser parser = new UpdateCommandParser();

        assertThrows(InventoryDockException.class,
                () -> parser.parse("index/1 qty/25"));
    }

    @Test
    public void parse_noFieldsToUpdate_throwsException() {
        UpdateCommandParser parser = new UpdateCommandParser();

        assertThrows(InventoryDockException.class,
                () -> parser.parse("category/vegetables index/1"));
    }
}
