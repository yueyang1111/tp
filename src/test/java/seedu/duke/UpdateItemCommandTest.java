package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.command.UpdateItemCommand;
import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.items.Fruit;
import seedu.duke.ui.UI;

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
                "2026-03-20", "small", true));

        inventory.addCategory(fruitsCategory);
    }

    @Test
    public void execute_commonFields_itemUpdated() throws DukeException {
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
    public void execute_invalidNonCommonField_throwsException() {
        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("isRipe", "false");

        UpdateItemCommand command = new UpdateItemCommand(
                "fruits", 1, updates);

        assertThrows(DukeException.class,
                () -> command.execute(inventory, new UI()));
    }

    @Test
    public void execute_invalidQuantity_throwsException() {
        Map<String, String> updates = new LinkedHashMap<>();
        updates.put("qty", "0");

        UpdateItemCommand command = new UpdateItemCommand(
                "fruits", 1, updates);

        assertThrows(DukeException.class,
                () -> command.execute(inventory, new UI()));
    }
}
