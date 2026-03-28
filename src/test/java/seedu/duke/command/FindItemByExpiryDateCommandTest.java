package seedu.duke.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.command.FindItemByExpiryDateCommand;
import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.items.Fruit;
import seedu.duke.model.items.Snack;
import seedu.duke.model.items.Toiletries;
import seedu.duke.model.items.Vegetable;
import seedu.duke.ui.UI;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindItemByExpiryDateCommandTest {
    private Inventory inventory;
    private Category fruitsCategory;
    private Category snacksCategory;
    private Category toiletriesCategory;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        fruitsCategory = new Category("fruits");
        snacksCategory = new Category("snacks");
        toiletriesCategory = new Category("toiletries");

        fruitsCategory.addItem(new Fruit("apple", 10, "A-1",
                "2026-3-19", "medium", true));
        fruitsCategory.addItem(new Fruit("banana", 8, "A-2",
                "2026-3-25", "small", true));
        snacksCategory.addItem(new Snack("chips", 20, "B-1",
                "Lays", "2026-3-21"));
        toiletriesCategory.addItem(new Toiletries("shampoo", 5, "C-1",
                "Dove", true, "2026-4-1"));
        inventory.addCategory(fruitsCategory);
        inventory.addCategory(snacksCategory);
        inventory.addCategory(toiletriesCategory);
    }

    @Test
    public void execute_matchingItems_showsAllItemsExpiringOnOrBeforeDate() throws DukeException {
        FindItemByExpiryDateCommand command = new FindItemByExpiryDateCommand("2026-3-21");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals("Items expiring by 2026-3-21:", ui.messages.get(0));
        assertTrue(ui.messages.stream().anyMatch(message -> message.contains("fruits: [Fruit] Name: apple")));
        assertTrue(ui.messages.stream().anyMatch(message -> message.contains("snacks: [Snack] Name: chips")));
        assertEquals(2, ui.dividerCount);
    }

    @Test
    public void execute_noMatchingItems_showsNoItemsFoundMessage() throws DukeException {
        FindItemByExpiryDateCommand command = new FindItemByExpiryDateCommand("2026-3-18");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(1, ui.messages.size());
        assertEquals("No items found expiring by 2026-3-18.", ui.messages.get(0));
    }

    @Test
    public void execute_invalidDate_throwsException() {
        FindItemByExpiryDateCommand command = new FindItemByExpiryDateCommand("21-3-2026");
        TestUI ui = new TestUI();

        DukeException exception = assertThrows(DukeException.class,
                () -> command.execute(inventory, ui));
        assertEquals("Invalid date. Please use yyyy-M-d.", exception.getMessage());
    }

    @Test
    public void execute_doesNotMutateInventory_inventoryRemainsUnchanged() throws DukeException {
        FindItemByExpiryDateCommand command = new FindItemByExpiryDateCommand("2026-4-1");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(2, fruitsCategory.getItemCount());
        assertEquals(1, snacksCategory.getItemCount());
        assertEquals(1, toiletriesCategory.getItemCount());
    }

    @Test
    public void execute_vegetableItemsIncludedWhenMatchingDate() throws DukeException {
        Category vegetablesCategory = new Category("vegetables");
        vegetablesCategory.addItem(new Vegetable("spinach", 7, "D-1",
                "2026-3-20", true));
        inventory.addCategory(vegetablesCategory);

        FindItemByExpiryDateCommand command = new FindItemByExpiryDateCommand("2026-3-21");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.messages.stream().anyMatch(message -> message.contains("vegetables: [Vegetable] Name: spinach")));
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
