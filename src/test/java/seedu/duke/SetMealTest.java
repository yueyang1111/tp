package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.model.items.SetMeal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetMealTest {
    @Test
    public void constructor_validInput_success() {
        SetMeal setMeal = new SetMeal("Chicken Set", 7, "F1", "2026-04-20",
                "Lunch", "Large");

        assertEquals("Chicken Set", setMeal.getName());
        assertEquals(7, setMeal.getQuantity());
        assertEquals("F1", setMeal.getBinLocation());
        assertEquals("2026-04-20", setMeal.getExpiryDate());
        assertEquals("Lunch", setMeal.getMealType());
        assertEquals("Large", setMeal.getFoodSize());
    }
}
