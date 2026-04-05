package seedu.inventorydock.parser;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.command.SortCommand;
import seedu.inventorydock.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SortCommandParserTest {
    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyInput_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> parser.parse(""));
        assertEquals("Please specify what to sort by. Use: sort name, sort expirydate, or sort qty.",
                exception.getMessage());
    }

    @Test
    public void parse_unknownSortType_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> parser.parse("invalid"));
        assertEquals("Unknown sort type: 'invalid'. Use: sort name, sort expirydate, or sort qty.",
                exception.getMessage());
    }

    @Test
    public void parse_expirydate_returnsSortCommand() throws DukeException {
        assertInstanceOf(SortCommand.class, parser.parse("expirydate"));
    }

    @Test
    public void parse_qty_returnsSortCommand() throws DukeException {
        assertInstanceOf(SortCommand.class, parser.parse("qty"));
    }

    @Test
    public void parse_caseInsensitive_returnsSortCommand() throws DukeException {
        assertInstanceOf(SortCommand.class, parser.parse("ExpiryDate"));
        assertInstanceOf(SortCommand.class, parser.parse("QTY"));
    }
}
