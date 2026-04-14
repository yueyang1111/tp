package seedu.inventorydock.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.DuplicateItemException;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.items.Fruit;
import seedu.inventorydock.ui.UI;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateItemCommandTest {
    private Inventory inventory;
    private Category fruitsCategory;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        fruitsCategory = new Category("fruits");

        fruitsCategory.addItem(new Fruit("apple", 10, "A-01",
                "2026-03-20", true));

        inventory.addCategory(fruitsCategory);
    }

    @Test
    public void execute_commonFields_itemUpdated() throws Exception {
        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("qty", "25");
        updates.put("bin", "A-02");
        updates.put("newItem", "green_apple");
        updates.put("expiryDate", "2026-04-01");

        UpdateItemCommand command = new UpdateItemCommand(
                "fruits", 1, updates);

        command.execute(inventory, new UI());

        Fruit updated = (Fruit) fruitsCategory.findItemByName("green_apple");
        assertNotNull(updated);
        assertEquals(25, updated.getQuantity());
        assertEquals("A-02", updated.getBinLocation());
        assertEquals("2026-04-01", updated.getExpiryDate());
    }

    @Test
    public void execute_updateCreatesDuplicateItem_throwsExceptionAndRollsBack() {
        fruitsCategory.addItem(new Fruit("apple", 8, "A-08",
                "2026-05-01", true));

        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("qty", "10");
        updates.put("bin", "A-01");
        updates.put("expiryDate", "2026-03-20");

        UpdateItemCommand command = new UpdateItemCommand("fruits", 2, updates);

        DuplicateItemException exception = assertThrows(DuplicateItemException.class,
                () -> command.execute(inventory, new UI()));
        assertEquals("Duplicate item found for category/fruits item/apple.", exception.getMessage());

        Fruit secondItem = (Fruit) fruitsCategory.getItem(1);
        assertEquals("2026-05-01", secondItem.getExpiryDate());
    }

    @Test
    public void execute_invalidNonCommonField_throwsException() {
        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("brand", "acme");

        UpdateItemCommand command = new UpdateItemCommand(
                "fruits", 1, updates);

        InvalidCommandException exception = assertThrows(InvalidCommandException.class,
                () -> command.execute(inventory, new UI()));
        assertEquals("Unsupported update field: brand/.", exception.getMessage());
    }

    @Test
    public void execute_categorySpecificBooleanField_itemUpdated() throws Exception {
        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("isRipe", "false");

        UpdateItemCommand command = new UpdateItemCommand(
                "fruits", 1, updates);

        command.execute(inventory, new UI());

        Fruit updated = (Fruit) fruitsCategory.getItem(0);
        assertEquals(false, updated.isRipe());
    }

    @Test
    public void execute_invalidBooleanValue_rollsBackAndThrowsException() {
        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("newItem", "green_apple");
        updates.put("isRipe", "maybe");

        UpdateItemCommand command = new UpdateItemCommand(
                "fruits", 1, updates);

        InvalidCommandException exception = assertThrows(InvalidCommandException.class,
                () -> command.execute(inventory, new UI()));
        assertEquals("isRipe/ must be true or false.", exception.getMessage());

        Fruit original = (Fruit) fruitsCategory.getItem(0);
        assertEquals("apple", original.getName());
        assertEquals(true, original.isRipe());
    }

    @Test
    public void execute_booleanFieldForWrongCategory_throwsException() {
        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("isFrozen", "true");

        UpdateItemCommand command = new UpdateItemCommand(
                "fruits", 1, updates);

        InvalidCommandException exception = assertThrows(InvalidCommandException.class,
                () -> command.execute(inventory, new UI()));
        assertEquals("isFrozen/ can only be updated for meat.", exception.getMessage());
    }

    @Test
    public void execute_invalidQuantity_throwsException() {
        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("qty", "0");

        UpdateItemCommand command = new UpdateItemCommand(
                "fruits", 1, updates);

        InventoryDockException exception = assertThrows(InventoryDockException.class,
                () -> command.execute(inventory, new UI()));
        assertEquals("quantity must be a positive integer.", exception.getMessage());
    }
}





