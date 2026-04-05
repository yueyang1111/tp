package seedu.inventorydock.model.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VegetableTest {
    @Test
    public void constructor_validInput_success() {
        Vegetable vegetable = new Vegetable("Spinach", 14, "H1",
                "2026-03-30", true);

        assertEquals("Spinach", vegetable.getName());
        assertEquals(14, vegetable.getQuantity());
        assertEquals("H1", vegetable.getBinLocation());
        assertEquals("2026-03-30", vegetable.getExpiryDate());
        assertEquals(true, vegetable.isLeafy());
    }
}

