package seedu.duke.model.items;

import org.junit.jupiter.api.Test;
import seedu.duke.model.items.Toiletries;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToiletriesTest {
    @Test
    public void constructor_validInput_success() {
        Toiletries toiletries = new Toiletries("Shampoo", 9, "G2",
                "Pantene", true, "2026-07-01");

        assertEquals("Shampoo", toiletries.getName());
        assertEquals(9, toiletries.getQuantity());
        assertEquals("G2", toiletries.getBinLocation());
        assertEquals("2026-07-01", toiletries.getExpiryDate());
        assertEquals("Pantene", toiletries.getBrand());
        assertEquals(true, toiletries.isLiquid());
    }
}
