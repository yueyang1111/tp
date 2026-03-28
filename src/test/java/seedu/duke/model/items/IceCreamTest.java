package seedu.duke.model.items;

import org.junit.jupiter.api.Test;
import seedu.duke.model.items.IceCream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class IceCreamTest {
    @Test
    public void constructor_validInput_success() {
        IceCream icecream = new IceCream("Vanilla Cup", 3, "B1",
                "2026-03-28", "Vanilla", false);

        assertEquals("Vanilla Cup", icecream.getName());
        assertEquals(3, icecream.getQuantity());
        assertEquals("B1", icecream.getBinLocation());
        assertEquals("2026-03-28", icecream.getExpiryDate());
        assertEquals("Vanilla", icecream.getFlavour());
        assertFalse(icecream.isDairyFree());
    }
}
