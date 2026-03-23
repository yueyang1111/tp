package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.model.items.Seafood;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeafoodTest {
    @Test
    public void constructor_validInput_success() {
        Seafood seafood = new Seafood("Salmon", 12, "D4", "2026-04-15",
                "Fish", "Norway");

        assertEquals("Salmon", seafood.getName());
        assertEquals(12, seafood.getQuantity());
        assertEquals("D4", seafood.getBinLocation());
        assertEquals("2026-04-15", seafood.getExpiryDate());
        assertEquals("Fish", seafood.getSeafoodType());
        assertEquals("Norway", seafood.getOrigin());
    }
}
