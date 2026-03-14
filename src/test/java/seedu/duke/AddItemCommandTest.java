package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.command.AddItemCommand;
import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.ui.UI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddItemCommandTest {

    @Test
    public void execute_validCategoryAndItem_itemAddedAndUiUpdated() throws DukeException {
        Inventory inventory = new Inventory();
        Category fruits = new Category("fruits");
        inventory.addCategories(fruits);
        Item item = new Item("Apple", 5, "A1");
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
        Item item = new Item("Apple", 5, "A1");
        AddItemCommand command = new AddItemCommand("fruits", item);
        TestUI ui = new TestUI();

        DukeException e = assertThrows(DukeException.class,
                () -> command.execute(inventory, ui));
        assertEquals("Category not found: fruits", e.getMessage());
    }

    @Test
    public void execute_nullItem_throwsException() {
        Inventory inventory = new Inventory();
        inventory.addCategories(new Category("fruits"));
        AddItemCommand command = new AddItemCommand("fruits", null);
        TestUI ui = new TestUI();

        DukeException e = assertThrows(DukeException.class,
                () -> command.execute(inventory, ui));
        assertEquals("Item cannot be null.", e.getMessage());
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
