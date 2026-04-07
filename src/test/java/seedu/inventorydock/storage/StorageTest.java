package seedu.inventorydock.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.items.Fruit;
import seedu.inventorydock.model.items.Snack;
import seedu.inventorydock.model.items.Toiletries;
import seedu.inventorydock.model.items.Vegetable;
import seedu.inventorydock.ui.UI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {
    private static final String FILE_PATH = "./test/test.txt";

    @AfterEach
    public void delete() throws IOException {
        Files.deleteIfExists(Path.of(FILE_PATH));
    }

    private Inventory createInventory() {
        Inventory inventory = new Inventory();
        inventory.addCategory(new Category("fruits"));
        inventory.addCategory(new Category("vegetables"));
        inventory.addCategory(new Category("toiletries"));
        inventory.addCategory(new Category("snacks"));
        return inventory;
    }

    @Test
    public void load_missingFile_fileCreated() throws IOException, InventoryDockException {
        Files.deleteIfExists(Path.of(FILE_PATH));

        Storage storage = new Storage(FILE_PATH);
        Inventory inventory = createInventory();
        storage.load(inventory, new UI());
        assertTrue(new File(FILE_PATH).exists());
    }

    @Test
    public void saveAndLoad_validInventory_inventoryLoaded() throws InventoryDockException {
        Storage storage = new Storage(FILE_PATH);
        Inventory inventory = createInventory();

        inventory.findCategoryByName("fruits").addItem(
                new Fruit("apple", 3, "A-10",
                        "2026-03-20", "big", true)
        );
        inventory.findCategoryByName("vegetables").addItem(
                new Vegetable("spinach", 4, "V-01",
                        "2026-03-18", true)
        );
        inventory.findCategoryByName("snacks").addItem(
                new Snack("chips", 6, "S-07",
                        "2026-06-01", "lays", true)
        );
        inventory.findCategoryByName("toiletries").addItem(
                new Toiletries("shampoo", 2, "T-03",
                        "Dove", true, "2026-06-01")
        );
        storage.save(inventory);

        Inventory loadedInventory = createInventory();
        storage.load(loadedInventory, new UI());

        assertEquals(1,
                loadedInventory.findCategoryByName("fruits").getItemCount());
        assertEquals("apple",
                loadedInventory.findCategoryByName("fruits").getItem(0).getName());

        assertEquals(1,
                loadedInventory.findCategoryByName("vegetables").getItemCount());
        assertEquals("spinach",
                loadedInventory.findCategoryByName("vegetables").getItem(0).getName());

        assertEquals(1,
                loadedInventory.findCategoryByName("snacks").getItemCount());
        assertEquals("chips",
                loadedInventory.findCategoryByName("snacks").getItem(0).getName());

        assertEquals(1,
                loadedInventory.findCategoryByName("toiletries").getItemCount());
        assertEquals("shampoo",
                loadedInventory.findCategoryByName("toiletries").getItem(0).getName());
    }

    @Test
    public void load_corruptedLines_skipCorruptedLines() throws IOException, InventoryDockException {
        Storage storage = new Storage(FILE_PATH);

        List<String> lines = List.of(
                "category/fruits item/apple bin/A-10 qty/3 expiryDate/2026-03-20 size/big isRipe/true",
                "category/vegetables item/spinach bin/V-01 qty/4 expiryDate/2026-03-18 isLeafy/true",
                "Corrupted line.",
                "category/snacks item/chips bin/S-07 qty/6 expiryDate/2026-06-01 brand/lays, isCrunchy/true"
        );

        Files.write(Path.of(FILE_PATH), lines);
        Inventory inventory = createInventory();
        storage.load(inventory, new UI());

        assertEquals(1,
                inventory.findCategoryByName("fruits").getItemCount());
        assertEquals("apple",
                inventory.findCategoryByName("fruits").getItem(0).getName());

        assertEquals(1,
                inventory.findCategoryByName("vegetables").getItemCount());
        assertEquals("spinach",
                inventory.findCategoryByName("vegetables").getItem(0).getName());

        assertEquals(1,
                inventory.findCategoryByName("snacks").getItemCount());
        assertEquals("chips",
                inventory.findCategoryByName("snacks").getItem(0).getName());

        assertEquals(0,
                inventory.findCategoryByName("toiletries").getItemCount());
    }
}
