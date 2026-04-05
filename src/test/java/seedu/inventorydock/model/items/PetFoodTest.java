package seedu.inventorydock.model.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PetFoodTest {
    @Test
    public void constructor_validInput_success() {
        PetFood petFood = new PetFood("Dog Kibble", 15, "E2", "2026-06-01",
                "Dog", "Pedigree", false);

        assertEquals("Dog Kibble", petFood.getName());
        assertEquals(15, petFood.getQuantity());
        assertEquals("E2", petFood.getBinLocation());
        assertEquals("2026-06-01", petFood.getExpiryDate());
        assertEquals("Dog", petFood.getPetType());
        assertEquals("Pedigree", petFood.getBrand());
        assertFalse(petFood.isDryFood());
    }
}
