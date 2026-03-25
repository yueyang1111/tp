package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.command.FindItemByKeywordCommand;
import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.items.Drinks;
import seedu.duke.model.items.Fruit;
import seedu.duke.model.items.Snack;
import seedu.duke.ui.UI;

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

        fruitsCategory.addItem(new Fruit("apple", 40, "A-10",
                "2026-10-03", "big", true));
        fruitsCategory.addItem(new Fruit("pineapple", 10, "A-11",
                "2026-10-03", "big", false));
        fruitsCategory.addItem(new Fruit("banana", 30, "B-10",
                "2026-10-03", "small", true));
        snacksCategory.addItem(new Snack("chips", 50, "D-05",
                "Lays", "2026-10-03"));
        drinksCategory.addItem(new Drinks("apple_juice", 24, "F-01",
                "2026-10-03", "Marigold", "Apple"));

        inventory.addCategory(fruitsCategory);
        inventory.addCategory(snacksCategory);
        inventory.addCategory(drinksCategory);
    }

    @Test
    public void execute_matchingKeyword_showsMatchingItems()
            throws DukeException {
        FindItemByKeywordCommand command =
                new FindItemByKeywordCommand("apple");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(2, ui.dividerCount);
        assertTrue(ui.messages.stream().anyMatch(
                message -> message.contains("fruits: "
                        + "[Fruit] Name: apple")));
        assertTrue(ui.messages.stream().anyMatch(
                message -> message.contains("fruits: "
                        + "[Fruit] Name: pineapple")));
        assertTrue(ui.messages.stream().anyMatch(
                message -> message.contains("drinks: "
                        + "[Drinks] Name: apple_juice")));
    }

    @Test
    public void execute_partialKeyword_showsPartialMatches()
            throws DukeException {
        FindItemByKeywordCommand command =
                new FindItemByKeywordCommand("chip");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(2, ui.dividerCount);
        assertTrue(ui.messages.stream().anyMatch(
                message -> message.contains("snacks: "
                        + "[Snack] Name: chips")));
    }

    @Test
    public void execute_caseInsensitiveKeyword_showsMatches()
            throws DukeException {
        FindItemByKeywordCommand command =
                new FindItemByKeywordCommand("APPLE");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.messages.stream().anyMatch(
                message -> message.contains("fruits: "
                        + "[Fruit] Name: apple")));
        assertTrue(ui.messages.stream().anyMatch(
                message -> message.contains("fruits: "
                        + "[Fruit] Name: pineapple")));
        assertTrue(ui.messages.stream().anyMatch(
                message -> message.contains("drinks: "
                        + "[Drinks] Name: apple_juice")));
    }

    @Test
    public void execute_noMatchingKeyword_showsNoItemsFound()
            throws DukeException {
        FindItemByKeywordCommand command =
                new FindItemByKeywordCommand("mango");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(1, ui.messages.size());
        assertEquals("No items found matching keyword: "
                + "mango.", ui.messages.get(0));
        assertEquals(0, ui.dividerCount);
    }

    @Test
    public void execute_doesNotMutateInventory_inventoryUnchanged()
            throws DukeException {
        FindItemByKeywordCommand command =
                new FindItemByKeywordCommand("apple");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(3, fruitsCategory.getItemCount());
        assertEquals(1, snacksCategory.getItemCount());
        assertEquals(1, drinksCategory.getItemCount());
    }

    @Test
    public void execute_singleCharKeyword_showsBroadMatches()
            throws DukeException {
        FindItemByKeywordCommand command =
                new FindItemByKeywordCommand("a");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(2, ui.dividerCount);
        assertTrue(ui.messages.size() > 1);
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
