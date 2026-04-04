package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.command.FindItemByBinCommand;
import seedu.duke.command.FindItemByCategoryCommand;
import seedu.duke.command.FindItemByExpiryDateCommand;
import seedu.duke.command.FindItemByKeywordCommand;
import seedu.duke.command.FindItemByQtyCommand;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.UI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FindItemParserTest {
    private final FindItemParser parser = new FindItemParser(new UI());

    @Test
    public void parse_emptyInput_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> parser.parse(""));
        assertEquals("Please specify what to find. Use: find keyword/KEYWORD, "
                + "find category/CATEGORY, find expiryDate/DATE, find bin/BIN, or find qty/QTY.",
                exception.getMessage());
    }

    @Test
    public void parse_missingName_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> parser.parse("keyword/   "));
        assertEquals("Missing name. Use: find keyword/KEYWORD, "
                + "find category/CATEGORY, find expiryDate/DATE, find bin/BIN, or find qty/QTY.",
                exception.getMessage());
    }

    @Test
    public void parse_unknownType_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> parser.parse("item/apple"));
        assertEquals("Unknown find type: 'item'. Use: find keyword/KEYWORD, "
                + "find category/CATEGORY, find expiryDate/DATE, find bin/BIN, or find qty/QTY.",
                exception.getMessage());
    }

    @Test
    public void parse_keyword_returnsKeywordCommand() throws DukeException {
        assertInstanceOf(FindItemByKeywordCommand.class, parser.parse("keyword/apple"));
    }

    @Test
    public void parse_category_returnsCategoryCommand() throws DukeException {
        assertInstanceOf(FindItemByCategoryCommand.class, parser.parse("category/fruits"));
    }

    @Test
    public void parse_expiryDate_returnsExpiryDateCommand() throws DukeException {
        assertInstanceOf(FindItemByExpiryDateCommand.class, parser.parse("expiryDate/2026-3-21"));
    }

    @Test
    public void parse_bin_returnsBinCommand() throws DukeException {
        assertInstanceOf(FindItemByBinCommand.class, parser.parse("bin/A-1"));
    }

    @Test
    public void parse_qty_returnsQtyCommand() throws DukeException {
        assertInstanceOf(FindItemByQtyCommand.class, parser.parse("qty/10"));
    }

    @Test
    public void parse_qtyNonInteger_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> parser.parse("qty/abc"));
        assertEquals("Quantity must be an integer.", exception.getMessage());
    }

    @Test
    public void parse_qtyMissing_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> parser.parse("qty/   "));
        assertEquals("Missing name. Use: find keyword/KEYWORD, "
                + "find category/CATEGORY, find expiryDate/DATE, find bin/BIN, or find qty/QTY.",
                exception.getMessage());
    }

    @Test
    public void parse_qtyZero_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> parser.parse("qty/0"));
        assertEquals("Quantity must be a positive integer.", exception.getMessage());
    }

    @Test
    public void parse_qtyNegative_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> parser.parse("qty/-2"));
        assertEquals("Quantity must be a positive integer.", exception.getMessage());
    }



}

