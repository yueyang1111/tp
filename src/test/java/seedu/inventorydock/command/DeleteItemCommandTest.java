package seedu.inventorydock.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.ui.UI;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteItemCommandTest {

    private Inventory inventory;
    private Category fruitsCategory;
    private Category vegetablesCategory;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        fruitsCategory = new Category("fruits");
        vegetablesCategory = new Category("vegetables");

        fruitsCategory.addItem(new Item("apple", 40, "A-10", null));
        fruitsCategory.addItem(new Item("banana", 30, "B-10", null));
        vegetablesCategory.addItem(new Item("carrot", 20, "C-5", null));

        inventory.addCategory(fruitsCategory);
        inventory.addCategory(vegetablesCategory);
    }

    @Test
    public void execute_validIndex_itemDeleted() {
        DeleteItemCommand command = new DeleteItemCommand("fruits", 1);
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(1, fruitsCategory.getItemCount());
        assertNull(fruitsCategory.findItemByName("apple"));
        assertEquals("apple", ui.deletedItemName);
        assertEquals("fruits", ui.deletedFromCategory);
    }

    @Test
    public void execute_lastIndex_itemDeleted() {
        DeleteItemCommand command = new DeleteItemCommand("fruits", 2);
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(1, fruitsCategory.getItemCount());
        assertNull(fruitsCategory.findItemByName("banana"));
        assertEquals("banana", ui.deletedItemName);
    }

    @Test
    public void execute_invalidIndexTooHigh_showsError() {
        DeleteItemCommand command = new DeleteItemCommand("fruits", 5);
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(2, fruitsCategory.getItemCount());
        assertEquals(1, ui.errors.size());
    }

    @Test
    public void execute_invalidIndexZero_showsError() {
        DeleteItemCommand command = new DeleteItemCommand("fruits", 0);
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(2, fruitsCategory.getItemCount());
        assertEquals(1, ui.errors.size());
    }

    @Test
    public void execute_categoryNotFound_showsCategoryNotFound() {
        DeleteItemCommand command = new DeleteItemCommand("dairy", 1);
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals("dairy", ui.notFoundCategoryName);
    }

    @Test
    public void execute_deleteAllOneByOne_categoryEmpty() {
        TestUI ui = new TestUI();

        new DeleteItemCommand("fruits", 1).execute(inventory, ui);
        new DeleteItemCommand("fruits", 1).execute(inventory, ui);

        assertEquals(0, fruitsCategory.getItemCount());
    }

    @Test
    public void execute_deleteFromSecondCategory_correctCategory() {
        DeleteItemCommand command = new DeleteItemCommand("vegetables", 1);
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(0, vegetablesCategory.getItemCount());
        assertEquals("carrot", ui.deletedItemName);
        assertEquals("vegetables", ui.deletedFromCategory);
    }

    private static class TestUI extends UI {
        private String deletedItemName;
        private String deletedFromCategory;
        private String notFoundCategoryName;
        private final List<String> errors = new ArrayList<>();

        @Override
        public void showItemDeleted(String itemName, String categoryName) {
            this.deletedItemName = itemName;
            this.deletedFromCategory = categoryName;
        }

        @Override
        public void showCategoryNotFound(String categoryName) {
            this.notFoundCategoryName = categoryName;
        }

        @Override
        public void showError(String message) {
            errors.add(message);
        }
    }
}
