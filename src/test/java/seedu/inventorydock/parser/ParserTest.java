package seedu.inventorydock.parser;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.command.AddItemCommand;
import seedu.inventorydock.command.ExitCommand;
import seedu.inventorydock.command.FindItemByCategoryCommand;
import seedu.inventorydock.command.HelpCommand;
import seedu.inventorydock.command.ListCommand;
import seedu.inventorydock.command.SortCommand;
import seedu.inventorydock.command.UpdateItemCommand;
import seedu.inventorydock.exception.DukeException;
import seedu.inventorydock.ui.UI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ParserTest {
    @Test
    public void parse_emptyInput_returnsNullAndShowsEmptyInput() throws DukeException {
        TestUI ui = new TestUI();
        Parser parser = new Parser(ui);

        assertNull(parser.parse("   "));
        assertEquals(1, ui.emptyInputCount);
    }

    @Test
    public void parse_unknownCommand_returnsNullAndShowsUnknownCommand() throws DukeException {
        TestUI ui = new TestUI();
        Parser parser = new Parser(ui);

        assertNull(parser.parse("random command"));
        assertEquals(1, ui.unknownCommandCount);
    }

    @Test
    public void parse_add_returnsAddItemCommand() throws DukeException {
        Parser parser = new Parser(new UI());

        assertInstanceOf(AddItemCommand.class,
                parser.parse("add category/fruits item/apple bin/A-10 qty/3 expiryDate/2026-03-20 size/big " +
                        "isRipe/true"));
    }

    @Test
    public void parse_find_returnsFindItemByCategoryCommand() throws DukeException {
        Parser parser = new Parser(new UI());

        assertInstanceOf(FindItemByCategoryCommand.class,
                parser.parse("find category/fruits"));
    }

    @Test
    public void parse_update_returnsUpdateItemCommand() throws DukeException {
        Parser parser = new Parser(new UI());

        assertInstanceOf(UpdateItemCommand.class,
                parser.parse("update category/vegetables index/1 qty/25"));
    }

    @Test
    public void parse_sort_returnsSortCommand() throws DukeException {
        Parser parser = new Parser(new UI());

        assertInstanceOf(SortCommand.class, parser.parse("sort qty"));
    }

    @Test
    public void parse_list_returnsListCommand() throws DukeException {
        Parser parser = new Parser(new UI());

        assertInstanceOf(ListCommand.class, parser.parse("list"));
    }

    @Test
    public void parse_help_returnsHelpCommand() throws DukeException {
        Parser parser = new Parser(new UI());

        assertInstanceOf(HelpCommand.class, parser.parse("help"));
    }

    @Test
    public void parse_bye_returnsExitCommand() throws DukeException {
        Parser parser = new Parser(new UI());

        assertInstanceOf(ExitCommand.class, parser.parse("bye"));
    }

    private static class TestUI extends UI {
        private int emptyInputCount;
        private int unknownCommandCount;

        @Override
        public void showEmptyInput() {
            emptyInputCount++;
        }

        @Override
        public void showUnknownCommand() {
            unknownCommandCount++;
        }
    }
}
