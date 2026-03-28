package seedu.duke.model.items;

import org.junit.jupiter.api.Test;
import seedu.duke.model.items.Meat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeatTest {
    @Test
    public void constructor_validInput_success() {
        Meat meat = new Meat("Wagyu Beef", 8, "B2", "2026-03-29",
                "Beef", "Japan");

        assertEquals("Wagyu Beef", meat.getName());
        assertEquals(8, meat.getQuantity());
        assertEquals("B2", meat.getBinLocation());
        assertEquals("2026-03-29", meat.getExpiryDate());
        assertEquals("Beef", meat.getMeatType());
        assertEquals("Japan", meat.getOrigin());
    }
}
