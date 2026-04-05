package seedu.inventorydock.parser;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.command.AddItemCommand;
import seedu.inventorydock.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddCommandParserTest {
    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_emptyInput_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> parser.parse(""));
        assertEquals("Input is empty.", exception.getMessage());
    }

    @Test
    public void parse_missingCategory_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> parser.parse("item/apple bin/A-10 qty/3 expiryDate/2026-03-20 size/big isRipe/true"));
        assertEquals("Missing category.", exception.getMessage());
    }

    @Test
    public void parse_unknownCategory_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> parser.parse("category/books item/novel bin/A-10 qty/3 expiryDate/2026-03-20 author/x"));
        assertEquals("Unknown category: books", exception.getMessage());
    }

    @Test
    public void parse_validFruitInput_returnsAddItemCommand() throws DukeException {
        assertInstanceOf(AddItemCommand.class,
                parser.parse("category/fruits item/apple bin/A-10 qty/3 expiryDate/2026-03-20 size/big isRipe/true"));
    }

    @Test
    public void parse_caseInsensitiveCategory_returnsAddItemCommand() throws DukeException {
        assertInstanceOf(AddItemCommand.class,
                parser.parse("category/FRUITS item/apple bin/A-10 qty/3 expiryDate/2026-03-20 size/big isRipe/true"));
    }
}

