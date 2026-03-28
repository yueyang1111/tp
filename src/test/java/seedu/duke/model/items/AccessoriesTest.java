package seedu.duke.model.items;

import org.junit.jupiter.api.Test;
import seedu.duke.model.items.Accessories;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccessoriesTest {
    @Test
    public void constructor_validInput_success() {
        Accessories accessories = new Accessories("Watch", 4, "J2",
                "2026-08-15", "Wearable", "Leather");

        assertEquals("Watch", accessories.getName());
        assertEquals(4, accessories.getQuantity());
        assertEquals("J2", accessories.getBinLocation());
        assertEquals("2026-08-15", accessories.getExpiryDate());
        assertEquals("Wearable", accessories.getType());
        assertEquals("Leather", accessories.getMaterial());
    }
}
