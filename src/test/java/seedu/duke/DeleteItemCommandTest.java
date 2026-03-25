package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.command.DeleteItemCommand;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.ui.UI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    public void execute_existingItem_itemDeletedFromCategory() {
        DeleteItemCommand command = new DeleteItemCommand("apple");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(1, fruitsCategory.getItemCount());
        assertNull(fruitsCategory.findItemByName("apple"));
        assertEquals("apple", ui.deletedItemName);
        assertEquals("fruits", ui.deletedFromCategory);
    }

    @Test
    public void execute_nonExistingItem_showsItemNotFound() {
        DeleteItemCommand command = new DeleteItemCommand("mango");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals("mango", ui.notFoundItemName);
        assertEquals(2, fruitsCategory.getItemCount());
        assertEquals(1, vegetablesCategory.getItemCount());
    }

    @Test
    public void execute_caseInsensitiveName_itemDeleted() {
        DeleteItemCommand command = new DeleteItemCommand("APPLE");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertNull(fruitsCategory.findItemByName("apple"));
        assertEquals(1, fruitsCategory.getItemCount());
    }

    @Test
    public void execute_itemInSecondCategory_deletedFromCorrectCategory() {
        DeleteItemCommand command = new DeleteItemCommand("carrot");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(0, vegetablesCategory.getItemCount());
        assertNull(vegetablesCategory.findItemByName("carrot"));
        assertEquals("carrot", ui.deletedItemName);
        assertEquals("vegetables", ui.deletedFromCategory);
    }

    @Test
    public void execute_deleteAllItemsOneByOne_categoryBecomesEmpty() {
        TestUI ui = new TestUI();

        new DeleteItemCommand("apple").execute(inventory, ui);
        new DeleteItemCommand("banana").execute(inventory, ui);

        assertEquals(0, fruitsCategory.getItemCount());
    }

    @Test
    public void execute_sameNameDifferentCategories_deletesFirstMatch() {
        vegetablesCategory.addItem(new Item("apple", 10, "V-1", null));
        DeleteItemCommand command = new DeleteItemCommand("apple");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        // First match (fruits) is deleted; vegetable apple remains
        assertNull(fruitsCategory.findItemByName("apple"));
        assertNotNull(vegetablesCategory.findItemByName("apple"));
    }

    @Test
    public void execute_deleteThenReAdd_itemReAdded() {
        TestUI ui = new TestUI();

        new DeleteItemCommand("apple").execute(inventory, ui);
        assertNull(fruitsCategory.findItemByName("apple"));

        fruitsCategory.addItem(new Item("apple", 50, "A-20", null));
        assertNotNull(fruitsCategory.findItemByName("apple"));
        assertEquals(50, fruitsCategory.findItemByName("apple").getQuantity());
    }

    private static class TestUI extends UI {
        private String deletedItemName;
        private String deletedFromCategory;
        private String notFoundItemName;

        @Override
        public void showItemDeleted(String itemName, String categoryName) {
            this.deletedItemName = itemName;
            this.deletedFromCategory = categoryName;
        }

        @Override
        public void showItemNotFound(String itemName) {
            this.notFoundItemName = itemName;
        }
    }
}
