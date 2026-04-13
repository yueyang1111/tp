package seedu.inventorydock.parser;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.command.UpdateItemCommand;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.InvalidIndexException;
import seedu.inventorydock.exception.MissingArgumentException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateCommandParserTest {
    @Test
    public void parse_validInput_returnsUpdateItemCommand() throws Exception {
        UpdateCommandParser parser = new UpdateCommandParser();

        assertInstanceOf(UpdateItemCommand.class, parser.parse(
                "category/vegetables index/1 qty/25"));
    }

    @Test
    public void parse_missingCategory_throwsException() {
        UpdateCommandParser parser = new UpdateCommandParser();

        MissingArgumentException exception = assertThrows(MissingArgumentException.class,
                () -> parser.parse("index/1 qty/25"));
        assertEquals("Category is required.", exception.getMessage());
    }

    @Test
    public void parse_noFieldsToUpdate_throwsException() {
        UpdateCommandParser parser = new UpdateCommandParser();

        MissingArgumentException exception = assertThrows(MissingArgumentException.class,
                () -> parser.parse("category/vegetables index/1"));
        assertEquals("at least one field to update is required.", exception.getMessage());
    }

    @Test
    public void parse_blankInput_throwsException() {
        UpdateCommandParser parser = new UpdateCommandParser();

        MissingArgumentException exception = assertThrows(MissingArgumentException.class,
                () -> parser.parse("   "));
        assertEquals("specify the update details. Use: update category/CATEGORY index/INDEX "
                + "[newItem/NAME] [bin/BIN] [qty/QTY] [expiryDate/DATE] ...", exception.getMessage());
    }

    @Test
    public void parse_nonIntegerIndex_throwsException() {
        UpdateCommandParser parser = new UpdateCommandParser();

        InvalidIndexException exception = assertThrows(InvalidIndexException.class,
                () -> parser.parse("category/vegetables index/abc qty/25"));
        assertEquals("Item index must be an integer.", exception.getMessage());
    }

    @Test
    public void parse_duplicateUpdateField_throwsException() {
        UpdateCommandParser parser = new UpdateCommandParser();

        InvalidCommandException exception = assertThrows(InvalidCommandException.class,
                () -> parser.parse("category/vegetables index/1 qty/25 qty/30"));
        assertEquals("Duplicate update field: qty/.", exception.getMessage());
    }

    @Test
    public void parse_duplicateIndexField_throwsException() {
        UpdateCommandParser parser = new UpdateCommandParser();

        InvalidCommandException exception = assertThrows(InvalidCommandException.class,
                () -> parser.parse("category/vegetables index/1 index/2 qty/25"));
        assertEquals("Duplicate update field: index/.", exception.getMessage());
    }

    @Test
    public void parse_invalidBin_throwsException() {
        UpdateCommandParser parser = new UpdateCommandParser();

        InvalidCommandException exception = assertThrows(InvalidCommandException.class,
                () -> parser.parse("category/vegetables index/1 bin/A10"));
        assertEquals("Bin location must be LETTER-NUMBER (e.g. A-10).", exception.getMessage());
    }

    @Test
    public void parse_newItemWithSpaces_returnsUpdateItemCommand() throws Exception {
        UpdateCommandParser parser = new UpdateCommandParser();

        assertInstanceOf(UpdateItemCommand.class, parser.parse(
                "category/snacks index/1 newItem/salted chips"));
    }
}
