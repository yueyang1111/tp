package seedu.duke.model;

import org.junit.jupiter.api.Test;
import seedu.duke.model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {

    @Test
    public void constructor_validInputs_fieldsSetCorrectly() {
        Item item = new Item("Apple", 5, "A1", "2026-03-20");

        assertEquals("Apple", item.getName());
        assertEquals(5, item.getQuantity());
        assertEquals("A1", item.getBinLocation());
        assertEquals("2026-03-20", item.getExpiryDate());
    }

    @Test
    public void setName_validName_nameUpdated() {
        Item item = new Item("Apple", 5, "A1", null);

        item.setName("Banana");

        assertEquals("Banana", item.getName());
    }

    @Test
    public void setQuantity_validQuantity_quantityUpdated() {
        Item item = new Item("Apple", 5, "A1", null);

        item.setQuantity(10);

        assertEquals(10, item.getQuantity());
    }

    @Test
    public void setBinLocation_validBinLocation_binLocationUpdated() {
        Item item = new Item("Apple", 5, "A1", null);

        item.setBinLocation("B2");

        assertEquals("B2", item.getBinLocation());
    }

    @Test
    public void toString_validItem_correctFormat() {
        Item item = new Item("Apple", 5, "A1", "2026-03-20");

        assertEquals("Name: Apple, Quantity: 5, Bin: A1, Expiry: 2026-03-20", item.toString());
    }
}
