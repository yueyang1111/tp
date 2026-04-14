package seedu.inventorydock.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.inventorydock.command.SummaryCommand;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UITest {
    private ByteArrayOutputStream output;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    private UI createUIWithInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        return new UI();
    }

    @Test
    public void readCommand_hasInput_returnsLine() {
        UI ui = createUIWithInput("hello\n");
        assertEquals("hello", ui.readCommand());
        ui.close();
    }

    @Test
    public void readCommand_noInput_returnsNull() {
        UI ui = createUIWithInput("");
        assertNull(ui.readCommand());
        ui.close();
    }

    @Test
    public void showInventory_emptyInventory_showsEmptyMessage() {
        UI ui = createUIWithInput("");
        Inventory inventory = new Inventory();
        ui.showInventory(inventory);
        assertTrue(output.toString().contains("Inventory is empty."));
    }

    @Test
    public void showInventory_withItems_showsCategoryAndItems() {
        UI ui = createUIWithInput("");
        Inventory inventory = new Inventory();
        Category cat = new Category("fruits");
        cat.addItem(new Item("apple", 10, "A-1", null));
        inventory.addCategory(cat);

        ui.showInventory(inventory);
        String out = output.toString();
        assertTrue(out.contains("Fruits"));
        assertTrue(out.contains("apple"));
    }

    @Test
    public void showInventory_singleItem_usesSingularGrammar() {
        UI ui = createUIWithInput("");
        Inventory inventory = new Inventory();
        Category cat = new Category("fruits");
        cat.addItem(new Item("apple", 10, "A-1", null));
        inventory.addCategory(cat);

        ui.showInventory(inventory);

        assertTrue(output.toString().contains("1. Fruits (1 item)"));
    }

    @Test
    public void showInventory_multipleItems_usesPluralGrammar() {
        UI ui = createUIWithInput("");
        Inventory inventory = new Inventory();
        Category cat = new Category("fruits");
        cat.addItem(new Item("apple", 10, "A-1", null));
        cat.addItem(new Item("banana", 5, "A-2", null));
        inventory.addCategory(cat);

        ui.showInventory(inventory);

        assertTrue(output.toString().contains("2 items"));
    }

    @Test
    public void showSortedInventory_emptyInventory_showsEmptyMessage() {
        UI ui = createUIWithInput("");
        Inventory inventory = new Inventory();
        ui.showSortedInventory(inventory, List.of(), "name");
        assertTrue(output.toString().contains("Inventory is empty."));
    }

    @Test
    public void showSortedInventory_withItems_showsSortedLabel() {
        UI ui = createUIWithInput("");
        Inventory inventory = new Inventory();
        Category cat = new Category("fruits");
        Item apple = new Item("apple", 10, "A-1", null);
        cat.addItem(apple);
        inventory.addCategory(cat);

        ui.showSortedInventory(inventory,
                List.of(List.of(apple)), "name");
        String out = output.toString();
        assertTrue(out.contains("sorted by name"));
        assertTrue(out.contains("apple"));
    }

    @Test
    public void showSortedInventory_singleItem_usesSingularGrammar() {
        UI ui = createUIWithInput("");
        Inventory inventory = new Inventory();
        Category cat = new Category("fruits");
        Item apple = new Item("apple", 10, "A-1", null);
        cat.addItem(apple);
        inventory.addCategory(cat);

        ui.showSortedInventory(inventory, List.of(List.of(apple)), "name");

        assertTrue(output.toString().contains("1. Fruits (1 item)"));
    }

    @Test
    public void showSortedInventory_multipleItems_usesPluralGrammar() {
        UI ui = createUIWithInput("");
        Inventory inventory = new Inventory();
        Category cat = new Category("fruits");
        Item apple = new Item("apple", 10, "A-1", null);
        Item banana = new Item("banana", 5, "A-2", null);
        cat.addItem(apple);
        cat.addItem(banana);
        inventory.addCategory(cat);

        ui.showSortedInventory(inventory, List.of(List.of(apple, banana)), "qty");

        assertTrue(output.toString().contains("2 items"));
    }

    @Test
    public void showClearCategoryConfirmation_singleItem_usesSingularGrammar() {
        UI ui = createUIWithInput("");

        ui.showClearCategoryConfirmation("fruits", 1);

        assertTrue(output.toString().contains("Category 'fruits' still has 1 item."));
    }

    @Test
    public void showClearCategoryConfirmation_multipleItems_usesPluralGrammar() {
        UI ui = createUIWithInput("");

        ui.showClearCategoryConfirmation("fruits", 5);

        String out = output.toString();
        assertTrue(out.contains("5 items"));
        assertTrue(out.contains("Type 'yes' to confirm"));
    }

    @Test
    public void showClearCategoryCancelled_printsCancelMessage() {
        UI ui = createUIWithInput("");
        ui.showClearCategoryCancelled("fruits");
        String out = output.toString();
        assertTrue(out.contains("Cancelled"));
        assertTrue(out.contains("fruits"));
    }

    @Test
    public void showCategoryAlreadyEmpty_printsEmptyMessage() {
        UI ui = createUIWithInput("");
        ui.showCategoryAlreadyEmpty("fruits");
        String out = output.toString();
        assertTrue(out.contains("already empty"));
        assertTrue(out.contains("fruits"));
    }

    @Test
    public void showCategoryItemsCleared_printsClearedMessage() {
        UI ui = createUIWithInput("");
        ui.showCategoryItemsCleared("fruits");
        assertTrue(output.toString().contains(
                "Cleared all items from category: fruits"));
    }

    @Test
    public void showCategoryCleared_printsClearedMessage() {
        UI ui = createUIWithInput("");
        ui.showCategoryCleared("fruits");
        assertTrue(output.toString().contains("Cleared category: fruits"));
    }

    @Test
    public void showCategoryNotFound_printsNotFoundError() {
        UI ui = createUIWithInput("");
        ui.showCategoryNotFound("seafood");
        String out = output.toString();
        assertTrue(out.contains("[Error] Not found: Category 'seafood' does not exist."));
    }

    @Test
    public void showItemUpdated_sameName_noNewNameLine() {
        UI ui = createUIWithInput("");
        ui.showItemUpdated("apple", "apple", "fruits");
        String out = output.toString();
        assertTrue(out.contains("Updated item: apple in category: fruits"));
        assertFalse(out.contains("New item name:"));
    }

    @Test
    public void showItemUpdated_differentName_showsNewName() {
        UI ui = createUIWithInput("");
        ui.showItemUpdated("apple", "orange", "fruits");
        String out = output.toString();
        assertTrue(out.contains("Updated item: apple"));
        assertTrue(out.contains("New item name: orange"));
    }

    @Test
    public void showWelcome_printsLogo() {
        UI ui = createUIWithInput("");
        ui.showWelcome();
        assertTrue(output.toString().contains("Welcome to InventoryDock"));
    }

    @Test
    public void showWelcome_printsQuickStartTips() {
        UI ui = createUIWithInput("");
        ui.showWelcome();
        String out = output.toString();
        assertTrue(out.contains("Quick start:"));
        assertTrue(out.contains("Type 'help'"));
        assertTrue(out.contains("Type 'list'"));
        assertTrue(out.contains("Type 'bye'"));
    }

    // ---- showGoodbye ----

    @Test
    public void showGoodbye_printsMessage() {
        UI ui = createUIWithInput("");
        ui.showGoodbye();
        String out = output.toString();
        assertTrue(out.contains("Thank you for using InventoryDock!"));
        assertTrue(out.contains("saved to data/inventory.txt"));
        assertTrue(out.contains("Goodbye! See you next time!"));
    }

    @Test
    public void showError_printsErrorPrefix() {
        UI ui = createUIWithInput("");
        ui.showError("bad input");
        assertTrue(output.toString().contains("[Error] bad input"));
    }

    @Test
    public void showError_twoArgs_printsFormattedError() {
        UI ui = createUIWithInput("");
        ui.showError("Invalid input", "category is required.");
        String out = output.toString();
        assertTrue(out.contains("[Error] Invalid input: category is required."));
    }

    @Test
    public void showError_exception_printsUniformCategoryLabel() {
        UI ui = createUIWithInput("");
        ui.showError(new InvalidCommandException("Unknown command."));
        assertTrue(output.toString().contains("[Error] Invalid input: Unknown command."));
    }

    @Test
    public void showItemAdded_printsAllDetails() {
        UI ui = createUIWithInput("");
        ui.showItemAdded("apple", 10, "fruits", "A-1");
        String out = output.toString();
        assertTrue(out.contains("Added item: apple"));
        assertTrue(out.contains("qty: 10"));
        assertTrue(out.contains("category: fruits"));
        assertTrue(out.contains("bin: A-1"));
    }

    @Test
    public void showItemDeleted_printsItemAndCategory() {
        UI ui = createUIWithInput("");
        ui.showItemDeleted("banana", "fruits");
        String out = output.toString();
        assertTrue(out.contains("Deleted item: banana from category: fruits"));
    }

    @Test
    public void showSkippedLine_printsLineAndReason() {
        UI ui = createUIWithInput("");
        ui.showSkippedLine("bad line", "corrupt");
        String out = output.toString();
        assertTrue(out.contains("Skip corrupted line: bad line"));
        assertTrue(out.contains("Reason: corrupt"));
    }

    @Test
    public void showNumberedList_printsList() {
        UI ui = createUIWithInput("");
        ui.showNumberedList(List.of("a", "b"));
        String out = output.toString();
        assertTrue(out.contains("1. a"));
        assertTrue(out.contains("2. b"));
    }

    @Test
    public void showHelp_printsUserGuideLink() {
        UI ui = createUIWithInput("");
        ui.showHelp();
        String out = output.toString();
        assertTrue(out.contains("User Guide"));
        assertTrue(out.contains("https://ay2526s2-cs2113-w09-2.github.io/tp/UserGuide.html"));
    }

    @Test
    public void showHelp_printsAllCommandFormats() {
        UI ui = createUIWithInput("");
        ui.showHelp();
        String out = output.toString();
        assertTrue(out.contains("category/CATEGORY item/ITEM"));
        assertTrue(out.contains("category/CATEGORY index/INDEX"));
        assertTrue(out.contains("clear category/CATEGORY"));
        assertTrue(out.contains("find keyword/KEYWORD"));
        assertTrue(out.contains("sort SORT_TYPE"));
        assertTrue(out.contains("Format: list"));
        assertTrue(out.contains("Format: help"));
        assertTrue(out.contains("Format: bye"));
    }

    @Test
    public void showHelp_printsExamples() {
        UI ui = createUIWithInput("");
        ui.showHelp();
        String out = output.toString();
        assertTrue(out.contains("add category/fruits item/apple"));
        assertTrue(out.contains("delete category/fruits index/2"));
        assertTrue(out.contains("clear category/fruits"));
        assertTrue(out.contains("update category/fruits index/1 qty/25"));
        assertTrue(out.contains("find keyword/apple"));
    }

    @Test
    public void showInventorySummary_emptyList_showsEmptyMessage() {
        UI ui = createUIWithInput("");
        ui.showInventorySummary(List.of(),
                SummaryCommand.SummaryType.ALL);
        assertTrue(output.toString().contains("Inventory is empty."));
    }

    @Test
    public void showInventorySummary_allType_showsBothSections() {
        UI ui = createUIWithInput("");
        SummaryCommand.CategorySummary summary =
                new SummaryCommand.CategorySummary("fruits", 1,
                        List.of(new SummaryCommand.IndexedItem(1,
                                new Item("apple", 5, "A-1", "2026-10-01"))),
                        List.of(new SummaryCommand.IndexedItem(1,
                                new Item("apple", 5, "A-1", "2026-10-01"))));

        ui.showInventorySummary(List.of(summary),
                SummaryCommand.SummaryType.ALL);
        String out = output.toString();
        assertTrue(out.contains("Inventory Summary:"));
        assertTrue(out.contains("Lowest stock:"));
        assertTrue(out.contains("Earliest expiry:"));
        assertTrue(out.contains("[Item 1]"));
    }

    @Test
    public void showInventorySummary_stockType_showsOnlyLowestStock() {
        UI ui = createUIWithInput("");
        SummaryCommand.CategorySummary summary =
                new SummaryCommand.CategorySummary("fruits", 2,
                        List.of(new SummaryCommand.IndexedItem(1,
                                new Item("apple", 5, "A-1", null))),
                        List.of());

        ui.showInventorySummary(List.of(summary),
                SummaryCommand.SummaryType.STOCK);
        String out = output.toString();
        assertTrue(out.contains("Inventory Summary (lowest stock):"));
        assertTrue(out.contains("Lowest stock:"));
        assertFalse(out.contains("Earliest expiry:"));
    }

    @Test
    public void showInventorySummary_expiryType_showsOnlyEarliestExpiry() {
        UI ui = createUIWithInput("");
        SummaryCommand.CategorySummary summary =
                new SummaryCommand.CategorySummary("fruits", 2,
                        List.of(),
                        List.of(new SummaryCommand.IndexedItem(1,
                                new Item("apple", 5, "A-1", "2026-10-01"))));

        ui.showInventorySummary(List.of(summary),
                SummaryCommand.SummaryType.EXPIRYDATE);
        String out = output.toString();
        assertTrue(out.contains("Inventory Summary (earliest expiry):"));
        assertTrue(out.contains("Earliest expiry:"));
        assertFalse(out.contains("Lowest stock:"));
    }

    @Test
    public void showInventorySummary_emptyIndexedItems_showsNA() {
        UI ui = createUIWithInput("");
        SummaryCommand.CategorySummary summary =
                new SummaryCommand.CategorySummary("fruits", 0,
                        List.of(), List.of());

        ui.showInventorySummary(List.of(summary),
                SummaryCommand.SummaryType.ALL);
        String out = output.toString();
        assertTrue(out.contains("N/A"));
    }

    @Test
    public void showInventorySummary_singleItem_usesSingularGrammar() {
        UI ui = createUIWithInput("");
        SummaryCommand.CategorySummary summary =
                new SummaryCommand.CategorySummary("fruits", 1,
                        List.of(), List.of());

        ui.showInventorySummary(List.of(summary),
                SummaryCommand.SummaryType.ALL);
        assertTrue(output.toString().contains("1 item"));
    }

    @Test
    public void showInventorySummary_multipleItems_usesPluralGrammar() {
        UI ui = createUIWithInput("");
        SummaryCommand.CategorySummary summary =
                new SummaryCommand.CategorySummary("fruits", 3,
                        List.of(), List.of());

        ui.showInventorySummary(List.of(summary),
                SummaryCommand.SummaryType.ALL);
        assertTrue(output.toString().contains("3 items"));
    }

    @Test
    public void formatFoundItemsMessage_returnsCorrectFormat() {
        UI ui = createUIWithInput("");
        String result = ui.formatFoundItemsMessage(3, "matching keyword 'apple'");
        assertEquals("Found 3 item(s) matching keyword 'apple'.", result);
    }

    @Test
    public void formatNoItemsFoundMessage_returnsCorrectFormat() {
        UI ui = createUIWithInput("");
        String result = ui.formatNoItemsFoundMessage("in category: fruits");
        assertEquals("No items found in category: fruits.", result);
    }

    @Test
    public void formatFindResultsHeader_returnsCorrectFormat() {
        UI ui = createUIWithInput("");
        String result = ui.formatFindResultsHeader("in bin location: A-1");
        assertEquals("Items in bin location: A-1", result);
    }
}
