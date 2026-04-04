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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindItemByCategoryCommandTest {
    private Inventory inventory;
    private Category fruitsCategory;
    private Category vegetablesCategory;
    private Category emptyCategory;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        fruitsCategory = new Category("fruits");
        vegetablesCategory = new Category("vegetables");
        emptyCategory = new Category("snacks");

        fruitsCategory.addItem(new Item("apple", 40, "A-10", null));
        fruitsCategory.addItem(new Item("banana", 30, "B-10", null));
        vegetablesCategory.addItem(new Item("carrot", 20, "C-5", null));

        inventory.addCategory(fruitsCategory);
        inventory.addCategory(vegetablesCategory);
        inventory.addCategory(emptyCategory);
    }

    @Test
    public void execute_existingCategory_showsItemsInCategory() throws DukeException {
        FindItemByCategoryCommand command = new FindItemByCategoryCommand("fruits");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(2, ui.dividerCount);
        assertEquals("Items in category: fruits", ui.messages.get(0));
        assertTrue(ui.messages.stream().anyMatch(message ->
                message.equals("1. Name: apple, Quantity: 40, Bin: A-10, Expiry: null")));
        assertTrue(ui.messages.stream().anyMatch(message ->
                message.equals("2. Name: banana, Quantity: 30, Bin: B-10, Expiry: null")));
    }

    @Test
    public void execute_emptyCategory_showsNoItemsFoundMessage() throws DukeException {
        FindItemByCategoryCommand command = new FindItemByCategoryCommand("snacks");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(1, ui.messages.size());
        assertEquals("No items found in category: snacks.", ui.messages.get(0));
        assertEquals(0, ui.dividerCount);
    }

    @Test
    public void execute_nonExistingCategory_showsCategoryNotFound() throws DukeException {
        FindItemByCategoryCommand command = new FindItemByCategoryCommand("toiletries");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals("toiletries", ui.notFoundCategoryName);
        assertEquals(0, ui.messages.size());
        assertEquals(0, ui.dividerCount);
    }

    @Test
    public void execute_caseInsensitiveCategory_showsMatchingItems() throws DukeException {
        FindItemByCategoryCommand command = new FindItemByCategoryCommand("FRUITS");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals("Items in category: fruits", ui.messages.get(0));
        assertEquals(2, ui.dividerCount);
    }

    @Test
    public void execute_doesNotMutateInventory_inventoryRemainsUnchanged() throws DukeException {
        FindItemByCategoryCommand command = new FindItemByCategoryCommand("vegetables");
        TestUI ui = new TestUI();

        command.execute(inventory, ui);

        assertEquals(2, fruitsCategory.getItemCount());
        assertEquals(1, vegetablesCategory.getItemCount());
        assertEquals(0, emptyCategory.getItemCount());
    }

    @Test
    public void parseCategoryInput_validCategory_returnsNormalizedCategory() throws DukeException {
        assertEquals("fruits", FindItemByCategoryCommand.parseCategoryInput(" Fruits "));
    }

    @Test
    public void parseCategoryInput_numericCategory_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> FindItemByCategoryCommand.parseCategoryInput("123"));

        assertEquals("Category must be a string.", exception.getMessage());
    }


    private static class TestUI extends UI {
        private final List<String> messages = new ArrayList<>();
        private int dividerCount;
        private String notFoundCategoryName;

        @Override
        public void showMessage(String message) {
            messages.add(message);
        }

        @Override
        public void showDivider() {
            dividerCount++;
        }

        @Override
        public void showCategoryNotFound(String categoryName) {
            notFoundCategoryName = categoryName;
        }
    }
}



