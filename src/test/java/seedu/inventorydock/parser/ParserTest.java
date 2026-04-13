package seedu.inventorydock.parser;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.command.AddItemCommand;
import seedu.inventorydock.command.ClearCategoryCommand;
import seedu.inventorydock.command.DeleteItemCommand;
import seedu.inventorydock.command.ExitCommand;
import seedu.inventorydock.command.FindItemByCategoryCommand;
import seedu.inventorydock.command.HelpCommand;
import seedu.inventorydock.command.ListCommand;
import seedu.inventorydock.command.SortCommand;
import seedu.inventorydock.command.SummaryCommand;
import seedu.inventorydock.command.UpdateItemCommand;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    @Test
    public void parse_emptyInput_returnsNull() throws InventoryDockException {
        Parser parser = new Parser();

        assertNull(parser.parse("   "));
    }

    @Test
    public void parse_unknownCommand_throwsException() {
        Parser parser = new Parser();

        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> parser.parse("random command"));
        assertEquals("command is not recognized. Type 'help' to see available commands.", exception.getMessage());
    }

    @Test
    public void parse_add_returnsAddItemCommand() throws InventoryDockException {
        Parser parser = new Parser();

        assertInstanceOf(AddItemCommand.class,
                parser.parse("add category/fruits item/apple bin/A-10 qty/3 expiryDate/2026-03-20 " +
                        "isRipe/true"));
    }

    @Test
    public void parse_delete_returnsDeleteItemCommand() throws InventoryDockException {
        Parser parser = new Parser();

        assertInstanceOf(DeleteItemCommand.class,
                parser.parse("delete category/fruits index/1"));
    }

    @Test
    public void parse_clear_returnsClearCategoryCommand() throws InventoryDockException {
        Parser parser = new Parser();

        assertInstanceOf(ClearCategoryCommand.class,
                parser.parse("clear category/fruits"));
    }

    @Test
    public void parse_find_returnsFindItemByCategoryCommand() throws InventoryDockException {
        Parser parser = new Parser();

        assertInstanceOf(FindItemByCategoryCommand.class,
                parser.parse("find category/fruits"));
    }

    @Test
    public void parse_update_returnsUpdateItemCommand() throws InventoryDockException {
        Parser parser = new Parser();

        assertInstanceOf(UpdateItemCommand.class,
                parser.parse("update category/vegetables index/1 qty/25"));
    }

    @Test
    public void parse_sort_returnsSortCommand() throws InventoryDockException {
        Parser parser = new Parser();

        assertInstanceOf(SortCommand.class, parser.parse("sort qty"));
    }

    @Test
    public void parse_list_returnsListCommand() throws InventoryDockException {
        Parser parser = new Parser();

        assertInstanceOf(ListCommand.class, parser.parse("list"));
    }

    @Test
    public void parse_help_returnsHelpCommand() throws InventoryDockException {
        Parser parser = new Parser();

        assertInstanceOf(HelpCommand.class, parser.parse("help"));
    }

    @Test
    public void parse_bye_returnsExitCommand() throws InventoryDockException {
        Parser parser = new Parser();

        assertInstanceOf(ExitCommand.class, parser.parse("bye"));
    }

    @Test
    public void parse_summary_returnsSummaryCommand() throws InventoryDockException {
        Parser parser = new Parser();
        assertInstanceOf(SummaryCommand.class, parser.parse("summary"));
    }
}
