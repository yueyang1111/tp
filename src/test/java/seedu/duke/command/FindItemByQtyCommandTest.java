package seedu.duke.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.ui.UI;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindItemByQtyCommandTest {
    private Inventory inventory;
    private Category fruitsCategory;
    private Category vegetablesCategory;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        fruitsCategory = new Category("fruits");
        vegetablesCategory = new Category("vegetables");

        fruitsCategory.addItem(new Item("apple", 10, "A-1", null));
        fruitsCategory.addItem(new Item("banana", 30, "A-10", null));
        vegetablesCategory.addItem(new Item("carrot", 10, "B-10", null));
        vegetablesCategory.addItem(new Item("spinach", 15, "C-2", null));

        inventory.addCategory(fruitsCategory);
        inventory.addCategory(vegetablesCategory);
    }

    @Test
    public void execute_thresholdQuantity_showsItemsWithQuantityInputOrLower() throws DukeException {
        FindItemByQtyCommand command = new FindItemByQtyCommand(10);
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(2, ui.dividerCount);
        assertEquals("Items with quantity: 10", ui.messages.get(0));
        assertTrue(ui.messages.stream().anyMatch(message ->
                message.equals("1. Name: apple, Quantity: 10, Bin: A-1, Expiry: null")));
        assertTrue(ui.messages.stream().anyMatch(message ->
                message.equals("2. Name: carrot, Quantity: 10, Bin: B-10, Expiry: null")));
        assertFalse(ui.messages.stream().anyMatch(message -> message.contains("Name: banana")));
        assertFalse(ui.messages.stream().anyMatch(message -> message.contains("Name: spinach")));
    }

    @Test
    public void execute_higherThreshold_includesLowerQuantities() throws DukeException {
        FindItemByQtyCommand command = new FindItemByQtyCommand(15);
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(2, ui.dividerCount);
        assertEquals("Items with quantity: 15", ui.messages.get(0));
        assertTrue(ui.messages.stream().anyMatch(message -> message.contains("Name: apple")));
        assertTrue(ui.messages.stream().anyMatch(message -> message.contains("Name: carrot")));
        assertTrue(ui.messages.stream().anyMatch(message -> message.contains("Name: spinach")));
        assertFalse(ui.messages.stream().anyMatch(message -> message.contains("Name: banana")));
    }

    @Test
    public void execute_quantityBelowAllItems_showsNoItemsFoundMessage() throws DukeException {
        FindItemByQtyCommand command = new FindItemByQtyCommand(5);
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(1, ui.messages.size());
        assertEquals("No items found with quantity: 5.", ui.messages.get(0));
        assertEquals(0, ui.dividerCount);
    }

    @Test
    public void parseQtyInput_nonInteger_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> FindItemByQtyCommand.parseQtyInput("abc"));

        assertEquals("Quantity must be an integer.", exception.getMessage());
    }

    @Test
    public void parseQtyInput_zero_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> FindItemByQtyCommand.parseQtyInput("0"));

        assertEquals("Quantity must be a positive integer.", exception.getMessage());
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








