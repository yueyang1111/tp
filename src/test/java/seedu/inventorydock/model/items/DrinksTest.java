package seedu.inventorydock.model.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DrinksTest {
    @Test
    public void constructor_validInput_success() {
        Drinks drinks = new Drinks("Coke", 5, "A1", "2026-04-01",
                "CocaCola", "Cola", false);

        assertEquals("Coke", drinks.getName());
        assertEquals(5, drinks.getQuantity());
        assertEquals("A1", drinks.getBinLocation());
        assertEquals("2026-04-01", drinks.getExpiryDate());
        assertEquals("CocaCola", drinks.getBrand());
        assertEquals("Cola", drinks.getFlavour());
        assertFalse(drinks.isCarbonated());
    }
}
