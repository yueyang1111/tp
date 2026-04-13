package seedu.inventorydock.parser;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.command.SummaryCommand;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SummaryCommandParserTest {
    @Test
    public void parse_emptyArguments_returnsAllSummaryCommand() throws InventoryDockException {
        SummaryCommandParser parser = new SummaryCommandParser();
        SummaryCommand command = parser.parse("");

        assertEquals(SummaryCommand.SummaryType.ALL, command.getSummaryType());
    }

    @Test
    public void parse_stock_returnsStockSummaryCommand() throws InventoryDockException {
        SummaryCommandParser parser = new SummaryCommandParser();
        SummaryCommand command = parser.parse("stock");

        assertEquals(SummaryCommand.SummaryType.STOCK, command.getSummaryType());
    }

    @Test
    public void parse_expiryDate_returnsExpirySummaryCommand() throws InventoryDockException {
        SummaryCommandParser parser = new SummaryCommandParser();
        SummaryCommand command = parser.parse("expirydate");

        assertEquals(SummaryCommand.SummaryType.EXPIRYDATE, command.getSummaryType());
    }

    @Test
    public void parse_invalidArgument_throwsInvalidCommandException() {
        SummaryCommandParser parser = new SummaryCommandParser();

        assertThrows(InvalidCommandException.class, () -> parser.parse("invalid"));
    }
}
