package seedu.duke.command;

import org.junit.jupiter.api.Test;
import seedu.duke.command.ListCommand;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.ui.UI;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListCommandTest {

    @Test
    public void execute_nonEmptyInventory_callsUiWithInventory() {
        Inventory inventory = new Inventory();
        Category fruits = new Category("fruits");
        fruits.addItem(new Item("Apple", 5, "A1", null));
        inventory.addCategory(fruits);
        ListCommand command = new ListCommand();
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.showInventoryCalled);
        assertSame(inventory, ui.inventoryShown);
    }

    @Test
    public void execute_emptyInventory_stillCallsUiWithInventory() {
        Inventory inventory = new Inventory();
        ListCommand command = new ListCommand();
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.showInventoryCalled);
        assertSame(inventory, ui.inventoryShown);
        assertFalse(ui.inventoryShown.getCategoryCount() > 0);
    }

    private static class TestUI extends UI {
        private boolean showInventoryCalled;
        private Inventory inventoryShown;

        @Override
        public void showInventory(Inventory inventory) {
            this.showInventoryCalled = true;
            this.inventoryShown = inventory;
        }
    }
}
