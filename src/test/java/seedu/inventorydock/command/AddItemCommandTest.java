package seedu.inventorydock.command;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.CategoryNotFoundException;

import seedu.inventorydock.exception.DuplicateItemException;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.ui.UI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddItemCommandTest {

    @Test
    public void execute_validCategoryAndItem_itemAddedAndUiUpdated()

            throws InventoryDockException {

        Inventory inventory = new Inventory();
        Category fruits = new Category("fruits");
        inventory.addCategory(fruits);
        Item item = new Item("Apple", 5, "A1", "2026-12-31");
        AddItemCommand command = new AddItemCommand("fruits", item);
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(1, fruits.getItemCount());
        assertSame(item, fruits.getItem(0));
        assertEquals("Apple", ui.itemName);
        assertEquals(5, ui.quantity);
        assertEquals("fruits", ui.categoryName);
        assertEquals("A1", ui.binLocation);
    }

    @Test
    public void execute_missingCategory_throwsException() {
        Inventory inventory = new Inventory();
        Item item = new Item("Apple", 5, "A1", "2026-12-31");
        AddItemCommand command = new AddItemCommand("fruits", item);
        TestUI ui = new TestUI();

        CategoryNotFoundException e = assertThrows(CategoryNotFoundException.class,
                () -> command.execute(inventory, ui));
        assertEquals("Category not found: fruits", e.getMessage());
    }

    @Test
    public void execute_nullItem_throwsException() {
        Inventory inventory = new Inventory();
        inventory.addCategory(new Category("fruits"));
        AddItemCommand command = new AddItemCommand("fruits", null);
        TestUI ui = new TestUI();

        MissingArgumentException e = assertThrows(MissingArgumentException.class,
                () -> command.execute(inventory, ui));
        assertEquals("Item cannot be null.", e.getMessage());
    }

    @Test
    public void execute_duplicateItemInSameCategory_throwsException() {
        Inventory inventory = new Inventory();
        Category fruits = new Category("fruits");
        inventory.addCategory(fruits);
        fruits.addItem(new Item("apple", 2, "A-01", "2026-01-01"));

        AddItemCommand command = new AddItemCommand("fruits", new Item("Apple", 2, "A-01", "2026-01-01"));

        DuplicateItemException e = assertThrows(DuplicateItemException.class,
                () -> command.execute(inventory, new TestUI()));
        assertEquals("Duplicate item found for category/fruits item/Apple.", e.getMessage());
    }

    @Test
    public void execute_sameNameDifferentFields_allowed() throws InventoryDockException {
        Inventory inventory = new Inventory();
        Category fruits = new Category("fruits");
        inventory.addCategory(fruits);
        fruits.addItem(new Item("apple", 2, "A-01", "2026-01-01"));

        AddItemCommand command = new AddItemCommand("fruits", new Item("Apple", 5, "B-02", "2026-02-01"));
        command.execute(inventory, new TestUI());

        assertEquals(2, fruits.getItemCount());
    }

    private static class TestUI extends UI {
        private String itemName;
        private int quantity;
        private String categoryName;
        private String binLocation;

        @Override
        public void showItemAdded(String itemName, int quantity,
                                  String categoryName, String bin) {
            this.itemName = itemName;
            this.quantity = quantity;
            this.categoryName = categoryName;
            this.binLocation = bin;
        }
    }
}



