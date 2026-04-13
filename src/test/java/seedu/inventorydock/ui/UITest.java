package seedu.inventorydock.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

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

    // ---- readCommand branches ----

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

    // ---- showInventory branches ----

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

    // ---- showSortedInventory branches ----

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
    public void showClearCategoryConfirmation_singleItem_usesSingularGrammar() {
        UI ui = createUIWithInput("");

        ui.showClearCategoryConfirmation("fruits", 1);

        assertTrue(output.toString().contains("Category 'fruits' still has 1 item."));
    }

    // ---- showItemUpdated branches ----

    @Test
    public void showItemUpdated_sameName_noNewNameLine() {
        UI ui = createUIWithInput("");
        ui.showItemUpdated("apple", "apple", "fruits");
        String out = output.toString();
        assertTrue(out.contains("Updated item: apple"));
        assertTrue(!out.contains("New item name:"));
    }

    @Test
    public void showItemUpdated_differentName_showsNewName() {
        UI ui = createUIWithInput("");
        ui.showItemUpdated("apple", "orange", "fruits");
        String out = output.toString();
        assertTrue(out.contains("Updated item: apple"));
        assertTrue(out.contains("New item name: orange"));
    }

    // ---- other methods for line coverage ----

    @Test
    public void showWelcome_printsLogo() {
        UI ui = createUIWithInput("");
        ui.showWelcome();
        assertTrue(output.toString().contains("Welcome to InventoryDock"));
    }

    @Test
    public void showGoodbye_printsMessage() {
        UI ui = createUIWithInput("");
        ui.showGoodbye();
        assertTrue(output.toString().contains("Goodbye"));
    }

    @Test
    public void showError_printsErrorPrefix() {
        UI ui = createUIWithInput("");
        ui.showError("bad input");
        assertTrue(output.toString().contains("[Error] bad input"));
    }

    @Test
    public void showError_exception_printsUniformCategoryLabel() {
        UI ui = createUIWithInput("");
        ui.showError(new InvalidCommandException("Unknown command."));
        assertTrue(output.toString().contains("[Error] Invalid input: Unknown command."));
    }

    @Test
    public void showHelp_printsUserGuideLink() {
        UI ui = createUIWithInput("");
        ui.showHelp();
        assertTrue(output.toString().contains("User Guide"));
    }

    @Test
    public void showItemAdded_printsDetails() {
        UI ui = createUIWithInput("");
        ui.showItemAdded("apple", 5, "fruits", "A-1");
        assertTrue(output.toString().contains("Added item: apple"));
    }

    @Test
    public void showItemDeleted_printsDetails() {
        UI ui = createUIWithInput("");
        ui.showItemDeleted("apple", "fruits");
        assertTrue(output.toString().contains("Deleted item: apple"));
    }

    @Test
    public void showSkippedLine_printsLineAndReason() {
        UI ui = createUIWithInput("");
        ui.showSkippedLine("bad line", "corrupt");
        String out = output.toString();
        assertTrue(out.contains("bad line"));
        assertTrue(out.contains("corrupt"));
    }

    @Test
    public void showNumberedList_printsList() {
        UI ui = createUIWithInput("");
        ui.showNumberedList(List.of("a", "b"));
        String out = output.toString();
        assertTrue(out.contains("1. a"));
        assertTrue(out.contains("2. b"));
    }
}
