package seedu.inventorydock.model.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BurgerTest {
    @Test
    public void constructor_validInput_success() {
        Burger burger = new Burger("Zinger Burger", 6, "C3", "2026-03-25",
                true, "Chicken");

        assertEquals("Zinger Burger", burger.getName());
        assertEquals(6, burger.getQuantity());
        assertEquals("C3", burger.getBinLocation());
        assertEquals("2026-03-25", burger.getExpiryDate());
        assertEquals(true, burger.isSpicy());
        assertEquals("Chicken", burger.getPattyType());
    }
}
