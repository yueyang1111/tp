package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.model.items.Fruit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FruitTest {
    @Test
    public void constructor_validInput_success() {
        Fruit fruit = new Fruit("Apple", 10, "A2", "2026-03-20",
                "big", true);

        assertEquals("Apple", fruit.getName());
        assertEquals(10, fruit.getQuantity());
        assertEquals("A2", fruit.getBinLocation());
        assertEquals("2026-03-20", fruit.getExpiryDate());
        assertEquals("big", fruit.getSize());
        assertEquals(true, fruit.isRipe());
    }
}
