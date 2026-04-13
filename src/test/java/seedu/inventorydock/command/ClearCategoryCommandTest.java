package seedu.inventorydock.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.ui.UI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClearCategoryCommandTest {

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
    public void execute_confirmYes_itemsClearedCategoryKept() {
        ClearCategoryCommand command = new ClearCategoryCommand("fruits");
        TestUI ui = new TestUI("yes");

        command.execute(inventory, ui);

        assertTrue(fruitsCategory.isEmpty());
        assertEquals(0, fruitsCategory.getItemCount());
        // Category itself still in inventory
        assertTrue(inventory.hasCategory("fruits"));
        assertEquals(2, inventory.getCategoryCount());
        assertEquals("fruits", ui.clearedCategoryName);
    }

    @Test
    public void execute_confirmNo_nothingCleared() {
        ClearCategoryCommand command = new ClearCategoryCommand("fruits");
        TestUI ui = new TestUI("no");

        command.execute(inventory, ui);

        assertEquals(2, fruitsCategory.getItemCount());
        assertTrue(inventory.hasCategory("fruits"));
        assertEquals("fruits", ui.cancelledCategoryName);
    }

    @Test
    public void execute_confirmNull_nothingCleared() {
        ClearCategoryCommand command = new ClearCategoryCommand("fruits");
        TestUI ui = new TestUI(null);

        command.execute(inventory, ui);

        assertEquals(2, fruitsCategory.getItemCount());
        assertEquals("fruits", ui.cancelledCategoryName);
    }

    @Test
    public void execute_nonExistingCategory_showsCategoryNotFound() {
        ClearCategoryCommand command = new ClearCategoryCommand("snacks");
        TestUI ui = new TestUI("yes");

        command.execute(inventory, ui);

        assertEquals("snacks", ui.notFoundCategoryName);
        assertEquals(2, inventory.getCategoryCount());
    }

    @Test
    public void execute_caseInsensitiveName_categoryCleared() {
        ClearCategoryCommand command = new ClearCategoryCommand("FRUITS");
        TestUI ui = new TestUI("yes");

        command.execute(inventory, ui);

        assertTrue(fruitsCategory.isEmpty());
        assertTrue(inventory.hasCategory("fruits"));
    }

    @Test
    public void execute_clearOneCategory_otherCategoryUnaffected() {
        ClearCategoryCommand command = new ClearCategoryCommand("fruits");
        TestUI ui = new TestUI("yes");

        command.execute(inventory, ui);

        assertTrue(fruitsCategory.isEmpty());
        assertEquals(1, vegetablesCategory.getItemCount());
        assertNotNull(vegetablesCategory.findItemByName("carrot"));
    }

    @Test
    public void execute_clearThenAddBack_categoryReusable() {
        ClearCategoryCommand command = new ClearCategoryCommand("fruits");
        TestUI ui = new TestUI("yes");

        command.execute(inventory, ui);
        assertTrue(fruitsCategory.isEmpty());

        fruitsCategory.addItem(new Item("mango", 15, "A-20", null));

        assertEquals(1, fruitsCategory.getItemCount());
        assertEquals("mango", fruitsCategory.getItem(0).getName());
    }

    @Test
    public void execute_confirmYesMixedCase_itemsCleared() {
        ClearCategoryCommand command = new ClearCategoryCommand("fruits");
        TestUI ui = new TestUI("YeS");

        command.execute(inventory, ui);

        assertTrue(fruitsCategory.isEmpty());
        assertEquals("fruits", ui.clearedCategoryName);
    }

    @Test
    public void execute_showsCorrectItemCountInConfirmation() {
        ClearCategoryCommand command = new ClearCategoryCommand("fruits");
        TestUI ui = new TestUI("yes");

        command.execute(inventory, ui);

        assertEquals("fruits", ui.confirmCategoryName);
        assertEquals(2, ui.confirmItemCount);
    }

    @Test
    public void execute_emptyCategory_showsAlreadyEmpty() {
        Category emptyCategory = new Category("snacks");
        inventory.addCategory(emptyCategory);

        ClearCategoryCommand command = new ClearCategoryCommand("snacks");
        TestUI ui = new TestUI("yes");

        command.execute(inventory, ui);

        assertEquals("snacks", ui.alreadyEmptyCategoryName);
        // should NOT show "Cleared category"
        assertNull(ui.categoryCleared);
    }

    /**
     * Stub UI that returns a pre-set confirmation response
     * and records which UI methods were called.
     */
    private static class TestUI extends UI {
        private final String confirmationResponse;

        private String clearedCategoryName;
        private String cancelledCategoryName;
        private String notFoundCategoryName;
        private String confirmCategoryName;
        private String alreadyEmptyCategoryName;
        private String categoryCleared;
        private int confirmItemCount;

        public TestUI(String confirmationResponse) {
            this.confirmationResponse = confirmationResponse;
        }

        @Override
        public String readCommand() {
            return confirmationResponse;
        }

        @Override
        public void showCategoryItemsCleared(String categoryName) {
            this.clearedCategoryName = categoryName;
        }

        @Override
        public void showClearCategoryCancelled(String categoryName) {
            this.cancelledCategoryName = categoryName;
        }

        @Override
        public void showCategoryNotFound(String categoryName) {
            this.notFoundCategoryName = categoryName;
        }

        @Override
        public void showClearCategoryConfirmation(String categoryName, int itemCount) {
            this.confirmCategoryName = categoryName;
            this.confirmItemCount = itemCount;
        }

        @Override
        public void showCategoryAlreadyEmpty(String categoryName) {
            this.alreadyEmptyCategoryName = categoryName;
        }

        @Override
        public void showCategoryCleared(String categoryName) {
            this.categoryCleared = categoryName;
        }

        @Override
        public void showDivider() {
            // no-op for testing
        }
    }
}
