package seedu.inventorydock.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.DukeException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.ui.UI;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindItemByBinCommandTest {
    private Inventory inventory;
    private Category fruitsCategory;
    private Category vegetablesCategory;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        fruitsCategory = new Category("fruits");
        vegetablesCategory = new Category("vegetables");

        fruitsCategory.addItem(new Item("apple", 40, "A-1", null));
        fruitsCategory.addItem(new Item("banana", 30, "A-10", null));
        vegetablesCategory.addItem(new Item("carrot", 20, "B-10", null));
        vegetablesCategory.addItem(new Item("spinach", 15, "C-2", null));

        inventory.addCategory(fruitsCategory);
        inventory.addCategory(vegetablesCategory);
    }

    @Test
    public void execute_exactBinSearch_doesNotOvermatchBinPrefix() throws DukeException {
        FindItemByBinCommand command = new FindItemByBinCommand("a-1");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(2, ui.dividerCount);
        assertEquals("Items in bin location: a-1", ui.messages.get(0));
        assertTrue(ui.messages.stream().anyMatch(message ->
                message.equals("1. Name: apple, Quantity: 40, Bin: A-1, Expiry: null")));
        assertFalse(ui.messages.stream().anyMatch(message -> message.contains("A-10")));
    }

    @Test
    public void execute_letterSearch_showsAllItemsWithMatchingLetter() throws DukeException {
        FindItemByBinCommand command = new FindItemByBinCommand("a");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals("Items in bin location: a", ui.messages.get(0));
        assertTrue(ui.messages.stream().anyMatch(message -> message.contains("Name: apple")));
        assertTrue(ui.messages.stream().anyMatch(message -> message.contains("Name: banana")));
        assertFalse(ui.messages.stream().anyMatch(message -> message.contains("Name: carrot")));
    }

    @Test
    public void execute_numberSearch_matchesExactNumberOnly() throws DukeException {
        FindItemByBinCommand command = new FindItemByBinCommand("10");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals("Items in bin location: 10", ui.messages.get(0));
        assertTrue(ui.messages.stream().anyMatch(message -> message.contains("Name: banana")));
        assertTrue(ui.messages.stream().anyMatch(message -> message.contains("Name: carrot")));
        assertFalse(ui.messages.stream().anyMatch(message -> message.contains("Name: apple")));
    }

    @Test
    public void execute_noMatchingBin_showsNoItemsFoundMessage() throws DukeException {
        FindItemByBinCommand command = new FindItemByBinCommand("z");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(1, ui.messages.size());
        assertEquals("No items found in bin location: z.", ui.messages.get(0));
        assertEquals(0, ui.dividerCount);
    }

    private static class TestUI extends UI {
        private final List<String> messages = new ArrayList<>();
        private int dividerCount;

        @Override
        public void showMessage(String message) {
            messages.add(message);
        }

        @Override
        public void showDivider() {
            dividerCount++;
        }
    }
}
