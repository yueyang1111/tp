package seedu.inventorydock.parser;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.command.FindItemByBinCommand;
import seedu.inventorydock.command.FindItemByCategoryCommand;
import seedu.inventorydock.command.FindItemByExpiryDateCommand;
import seedu.inventorydock.command.FindItemByKeywordCommand;
import seedu.inventorydock.command.FindItemByQtyCommand;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FindItemParserTest {
    private final FindItemParser parser = new FindItemParser();

    @Test
    public void parse_emptyInput_throwsException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse(""));
        assertEquals("Please specify what to find. Use: find keyword/KEYWORD, "
                + "find category/CATEGORY, find expiryDate/DATE, find bin/BIN, or find qty/QTY.",
                exception.getMessage());
    }

    @Test
    public void parse_missingName_throwsException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse("keyword/   "));
        assertEquals("Missing name. Use: find keyword/KEYWORD, "
                + "find category/CATEGORY, find expiryDate/DATE, find bin/BIN, or find qty/QTY.",
                exception.getMessage());
    }

    @Test
    public void parse_unknownType_throwsException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse("item/apple"));
        assertEquals("Unknown find type: 'item'. Use: find keyword/KEYWORD, "
                + "find category/CATEGORY, find expiryDate/DATE, find bin/BIN, or find qty/QTY.",
                exception.getMessage());
    }

    @Test
    public void parse_keyword_returnsKeywordCommand() throws InventoryDockException {
        assertInstanceOf(FindItemByKeywordCommand.class, parser.parse("keyword/apple"));
    }

    @Test
    public void parse_category_returnsCategoryCommand() throws InventoryDockException {
        assertInstanceOf(FindItemByCategoryCommand.class, parser.parse("category/fruits"));
    }

    @Test
    public void parse_expiryDate_returnsExpiryDateCommand() throws InventoryDockException {
        assertInstanceOf(FindItemByExpiryDateCommand.class, parser.parse("expiryDate/2026-3-21"));
    }

    @Test
    public void parse_bin_returnsBinCommand() throws InventoryDockException {
        assertInstanceOf(FindItemByBinCommand.class, parser.parse("bin/A-1"));
    }

    @Test
    public void parse_qty_returnsQtyCommand() throws InventoryDockException {
        assertInstanceOf(FindItemByQtyCommand.class, parser.parse("qty/10"));
    }

    @Test
    public void parse_qtyNonInteger_throwsException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse("qty/abc"));
        assertEquals("Quantity must be an integer.", exception.getMessage());
    }

    @Test
    public void parse_qtyMissing_throwsException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse("qty/   "));
        assertEquals("Missing name. Use: find keyword/KEYWORD, "
                + "find category/CATEGORY, find expiryDate/DATE, find bin/BIN, or find qty/QTY.",
                exception.getMessage());
    }

    @Test
    public void parse_qtyZero_throwsException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse("qty/0"));
        assertEquals("Quantity must be a positive integer.", exception.getMessage());
    }

    @Test
    public void parse_qtyNegative_throwsException() {
        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse("qty/-2"));
        assertEquals("Quantity must be a positive integer.", exception.getMessage());
    }



}

