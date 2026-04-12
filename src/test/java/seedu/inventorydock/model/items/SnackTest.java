package seedu.inventorydock.model.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnackTest {
    @Test
    public void constructor_validInput_success() {
        Snack snack = new Snack("Lays", 8, "B2", "2026-03-29", true);

        assertEquals("Lays", snack.getName());
        assertEquals(8, snack.getQuantity());
        assertEquals("B2", snack.getBinLocation());
        assertEquals("2026-03-29", snack.getExpiryDate());
        assertEquals(true, snack.isCrunchy());
    }
}
