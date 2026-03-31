package seedu.duke.model.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SeafoodTest {
    @Test
    public void constructor_validInput_success() {
        Seafood seafood = new Seafood("Salmon", 12, "D4", "2026-04-15",
                "Fish", "Norway", false);

        assertEquals("Salmon", seafood.getName());
        assertEquals(12, seafood.getQuantity());
        assertEquals("D4", seafood.getBinLocation());
        assertEquals("2026-04-15", seafood.getExpiryDate());
        assertEquals("Fish", seafood.getSeafoodType());
        assertEquals("Norway", seafood.getOrigin());
        assertFalse(seafood.isFrozen());
    }
}

