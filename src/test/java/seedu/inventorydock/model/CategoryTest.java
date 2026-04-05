package seedu.inventorydock.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryTest {

    @Test
    public void constructor_validName_nameSetAndItemsEmpty() {
        Category category = new Category("Fruits");

        assertEquals("Fruits", category.getName());
        assertEquals(0, category.getItemCount());
        assertTrue(category.isEmpty());
    }

    @Test
    public void setName_validName_nameUpdated() {
        Category category = new Category("Fruits");
        category.setName("Vegetables");

        assertEquals("Vegetables", category.getName());
    }

    @Test
    public void addItem_validItem_itemAdded() {
        Category category = new Category("Fruits");
        Item item = new Item("Apple", 5, "A1", null);

        category.addItem(item);

        assertEquals(1, category.getItemCount());
        assertFalse(category.isEmpty());
        assertEquals(item, category.getItem(0));
    }

    @Test
    public void getItem_validIndex_returnsCorrectItem() {
        Category category = new Category("Fruits");
        Item item = new Item("Apple", 5, "A1", null);
        category.addItem(item);

        assertEquals(item, category.getItem(0));
    }

    @Test
    public void removeItem_validIndex_itemRemoved() {
        Category category = new Category("Fruits");
        Item item1 = new Item("Apple", 5, "A1", null);
        Item item2 = new Item("Banana", 3, "A2", null);

        category.addItem(item1);
        category.addItem(item2);
        category.removeItem(0);

        assertEquals(1, category.getItemCount());
        assertEquals(item2, category.getItem(0));
    }

    @Test
    public void findItemByName_existingItem_returnsItem() {
        Category category = new Category("Fruits");
        Item item = new Item("Apple", 5, "A1", null);
        category.addItem(item);

        assertEquals(item, category.findItemByName("Apple"));
    }

    @Test
    public void findItemByName_caseInsensitive_returnsItem() {
        Category category = new Category("Fruits");
        Item item = new Item("Apple", 5, "A1", null);
        category.addItem(item);

        assertEquals(item, category.findItemByName("apple"));
    }

    @Test
    public void findItemByName_nonExistingItem_returnsNull() {
        Category category = new Category("Fruits");
        category.addItem(new Item("Apple", 5, "A1", null));

        assertNull(category.findItemByName("Banana"));
    }

    @Test
    public void toString_emptyCategory_correctFormat() {
        Category category = new Category("Fruits");

        assertEquals("Category: Fruits\n", category.toString());
    }

    @Test
    public void toString_withItems_correctFormat() {
        Category category = new Category("Fruits");
        Item item1 = new Item("Apple", 5, "A1", null);
        Item item2 = new Item("Banana", 3, "A2", null);

        category.addItem(item1);
        category.addItem(item2);

        String expected = "Category: Fruits\n"
                + "  1. Name: Apple, Quantity: 5, Bin: A1, Expiry: null\n"
                + "  2. Name: Banana, Quantity: 3, Bin: A2, Expiry: null\n";

        assertEquals(expected, category.toString());
    }
}
