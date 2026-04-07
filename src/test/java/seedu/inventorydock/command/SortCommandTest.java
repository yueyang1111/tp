package seedu.inventorydock.command;

import org.junit.jupiter.api.Test;

import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.ui.UI;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortCommandTest {

    @Test
    public void execute_sortByName_callsUiWithSortedCopy() {
        Inventory inventory = new Inventory();
        Category fruits = new Category("fruits");
        fruits.addItem(new Item("Banana", 5, "A1", "2026-12-01"));
        fruits.addItem(new Item("Apple", 3, "A2", "2026-11-01"));
        inventory.addCategory(fruits);
        SortCommand command = new SortCommand("name");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.showSortedInventoryCalled);
        assertEquals("name", ui.sortLabelUsed);
        assertSame(inventory, ui.inventoryShown);
        assertEquals("Apple", ui.sortedItemsShown.get(0).get(0).getName());
        assertEquals("Banana", ui.sortedItemsShown.get(0).get(1).getName());
        assertEquals("Banana", fruits.getItems().get(0).getName());
    }

    @Test
    public void execute_sortByExpiryDate_callsUiWithSortedCopy() {
        Inventory inventory = new Inventory();
        Category fruits = new Category("fruits");
        fruits.addItem(new Item("Apple", 5, "A1", "2026-12-01"));
        fruits.addItem(new Item("Banana", 3, "A2", "2026-11-01"));
        inventory.addCategory(fruits);
        SortCommand command = new SortCommand("expirydate");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.showSortedInventoryCalled);
        assertEquals("expiry date", ui.sortLabelUsed);
        assertSame(inventory, ui.inventoryShown);
        assertEquals("Banana", ui.sortedItemsShown.get(0).get(0).getName());
        assertEquals("Apple", ui.sortedItemsShown.get(0).get(1).getName());
    }

    @Test
    public void execute_sortByQty_callsUiWithSortedCopy() {
        Inventory inventory = new Inventory();
        Category fruits = new Category("fruits");
        fruits.addItem(new Item("Apple", 3, "A1", "2026-12-01"));
        fruits.addItem(new Item("Banana", 5, "A2", "2026-11-01"));
        inventory.addCategory(fruits);
        SortCommand command = new SortCommand("qty");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertTrue(ui.showSortedInventoryCalled);
        assertEquals("quantity", ui.sortLabelUsed);
        assertSame(inventory, ui.inventoryShown);
        assertEquals("Banana", ui.sortedItemsShown.get(0).get(0).getName());
        assertEquals("Apple", ui.sortedItemsShown.get(0).get(1).getName());
    }

    private static class TestUI extends UI {
        private boolean showSortedInventoryCalled;
        private String sortLabelUsed;
        private Inventory inventoryShown;
        private List<List<Item>> sortedItemsShown;

        @Override
        public void showSortedInventory(Inventory inventory, List<List<Item>> sortedItemsByCategory, String sortLabel) {
            this.showSortedInventoryCalled = true;
            this.sortLabelUsed = sortLabel;
            this.inventoryShown = inventory;
            this.sortedItemsShown = sortedItemsByCategory;
        }
    }
}
