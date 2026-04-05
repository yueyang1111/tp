package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.command.AddItemCommand;
import seedu.duke.command.ExitCommand;
import seedu.duke.command.FindItemByCategoryCommand;
import seedu.duke.command.HelpCommand;
import seedu.duke.command.ListCommand;
import seedu.duke.command.SortCommand;
import seedu.duke.command.UpdateItemCommand;
import seedu.duke.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    @Test
    public void parse_emptyInput_throwsException() {
        Parser parser = new Parser();

        DukeException exception = assertThrows(DukeException.class,
                () -> parser.parse("   "));
        assertEquals("Input is empty.", exception.getMessage());
    }

    @Test
    public void parse_unknownCommand_throwsException() {
        Parser parser = new Parser();

        DukeException exception = assertThrows(DukeException.class,
                () -> parser.parse("random command"));
        assertEquals("Unknown command.", exception.getMessage());
    }

    @Test
    public void parse_add_returnsAddItemCommand() throws DukeException {
        Parser parser = new Parser();

        assertInstanceOf(AddItemCommand.class,
                parser.parse("add category/fruits item/apple bin/A-10 qty/3 expiryDate/2026-03-20 size/big " +
                        "isRipe/true"));
    }

    @Test
    public void parse_find_returnsFindItemByCategoryCommand() throws DukeException {
        Parser parser = new Parser();

        assertInstanceOf(FindItemByCategoryCommand.class,
                parser.parse("find category/fruits"));
    }

    @Test
    public void parse_update_returnsUpdateItemCommand() throws DukeException {
        Parser parser = new Parser();

        assertInstanceOf(UpdateItemCommand.class,
                parser.parse("update category/vegetables index/1 qty/25"));
    }

    @Test
    public void parse_sort_returnsSortCommand() throws DukeException {
        Parser parser = new Parser();

        assertInstanceOf(SortCommand.class, parser.parse("sort qty"));
    }

    @Test
    public void parse_list_returnsListCommand() throws DukeException {
        Parser parser = new Parser();

        assertInstanceOf(ListCommand.class, parser.parse("list"));
    }

    @Test
    public void parse_help_returnsHelpCommand() throws DukeException {
        Parser parser = new Parser();

        assertInstanceOf(HelpCommand.class, parser.parse("help"));
    }

    @Test
    public void parse_bye_returnsExitCommand() throws DukeException {
        Parser parser = new Parser();

        assertInstanceOf(ExitCommand.class, parser.parse("bye"));
    }
}
