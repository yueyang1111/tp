package seedu.duke.model.items;

import org.junit.jupiter.api.Test;
import seedu.duke.model.items.Sweets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SweetsTest {
    @Test
    public void constructor_validInput_success() {
        Sweets sweets = new Sweets("Gummy Bears", 8, "C1",
                "2026-05-10", "Haribo", "High");

        assertEquals("Gummy Bears", sweets.getName());
        assertEquals(8, sweets.getQuantity());
        assertEquals("C1", sweets.getBinLocation());
        assertEquals("2026-05-10", sweets.getExpiryDate());
        assertEquals("Haribo", sweets.getBrand());
        assertEquals("High", sweets.getSweetnessLevel());
    }
}

