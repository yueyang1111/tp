package seedu.inventorydock.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.items.Drinks;
import seedu.inventorydock.model.items.Fruit;
import seedu.inventorydock.model.items.Snack;
import seedu.inventorydock.ui.UI;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindItemByKeywordCommandTest {
    private Inventory inventory;
    private Category fruitsCategory;
    private Category snacksCategory;
    private Category drinksCategory;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        fruitsCategory = new Category("fruits");
        snacksCategory = new Category("snacks");
        drinksCategory = new Category("drinks");

        fruitsCategory.addItem(new Fruit("apple", 40, "A-10", "2026-10-03", true));
        fruitsCategory.addItem(new Fruit("pineapple", 10, "A-11", "2026-10-03", false));
        fruitsCategory.addItem(new Fruit("banana", 30, "B-10", "2026-10-03", true));
        snacksCategory.addItem(new Snack("potato chips", 50, "D-05", "2026-10-03", true));
        drinksCategory.addItem(new Drinks("apple_juice", 24, "F-01", "2026-10-03", true));

        inventory.addCategory(fruitsCategory);
        inventory.addCategory(snacksCategory);
        inventory.addCategory(drinksCategory);
    }

    @Test
    public void execute_matchingKeyword_showsGroupedResults() {
        FindItemByKeywordCommand command = new FindItemByKeywordCommand("apple");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.messages.stream().anyMatch(m -> m.contains("Found 3 item(s)")));
        assertTrue(ui.messages.stream().anyMatch(m -> m.equals("fruits (2):")));
        assertTrue(ui.messages.stream().anyMatch(m -> m.contains("apple")));
        assertTrue(ui.messages.stream().anyMatch(m -> m.contains("pineapple")));
        assertTrue(ui.messages.stream().anyMatch(m -> m.equals("drinks (1):")));
        assertTrue(ui.messages.stream().anyMatch(m -> m.contains("apple_juice")));
    }

    @Test
    public void execute_partialKeyword_showsGroupedPartialMatches() {
        FindItemByKeywordCommand command = new FindItemByKeywordCommand("chip");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.messages.stream().anyMatch(m -> m.equals("snacks (1):")));
        assertTrue(ui.messages.stream().anyMatch(m -> m.contains("potato chips")));
    }

    @Test
    public void execute_caseInsensitiveKeyword_showsMatches() {
        FindItemByKeywordCommand command = new FindItemByKeywordCommand("APPLE");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.messages.stream().anyMatch(m -> m.equals("fruits (2):")));
        assertTrue(ui.messages.stream().anyMatch(m -> m.contains("apple")));
        assertTrue(ui.messages.stream().anyMatch(m -> m.contains("pineapple")));
        assertTrue(ui.messages.stream().anyMatch(m -> m.equals("drinks (1):")));
        assertTrue(ui.messages.stream().anyMatch(m -> m.contains("apple_juice")));
    }

    @Test
    public void execute_noMatchingKeyword_showsNoItemsFound() {
        FindItemByKeywordCommand command = new FindItemByKeywordCommand("mango");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(1, ui.messages.size());
        assertEquals("No items found matching keyword: mango.", ui.messages.get(0));
        assertEquals(1, ui.dividerCount);
    }

    @Test
    public void execute_doesNotMutateInventory_inventoryUnchanged() {
        FindItemByKeywordCommand command = new FindItemByKeywordCommand("apple");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(3, fruitsCategory.getItemCount());
        assertEquals(1, snacksCategory.getItemCount());
        assertEquals(1, drinksCategory.getItemCount());
    }

    @Test
    public void execute_singleCharKeyword_showsBroadMatches() {
        FindItemByKeywordCommand command = new FindItemByKeywordCommand("a");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.messages.stream().anyMatch(m -> m.contains("Found")));
    }

    @Test
    public void execute_multiWordKeyword_showsError() {
        FindItemByKeywordCommand command = new FindItemByKeywordCommand("green apple");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(0, ui.dividerCount);
        assertEquals(1, ui.errors.size());
    }

    @Test
    public void execute_singleCategoryMatch_showsOnlyThatCategory() {
        FindItemByKeywordCommand command = new FindItemByKeywordCommand("banana");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.messages.stream().anyMatch(m -> m.equals("fruits (1):")));
        assertTrue(ui.messages.stream().anyMatch(m -> m.contains("banana")));
        assertTrue(ui.messages.stream().noneMatch(m -> m.contains("snacks")));
        assertTrue(ui.messages.stream().noneMatch(m -> m.contains("drinks")));
    }

    private static class TestUI extends UI {
        private final List<String> messages = new ArrayList<>();
        private int dividerCount;
        private final List<String> errors = new ArrayList<>();

        @Override
        public void showMessage(String message) {
            messages.add(message);
        }

        @Override
        public void showDivider() {
            dividerCount++;
        }

        @Override
        public void showError(String category, String message) {
            errors.add(category + ": " + message);
        }
    }
}
