package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SetMealParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "mealType/Western foodSize/Large hasDrinks/true";
        assertDoesNotThrow(() -> SetMealParser.parse(input));
    }

    @Test
    public void parse_missingFoodSize_throwsException() {
        String input = "mealType/Western foodSize/ hasDrinks/true";
        DukeException e = assertThrows(DukeException.class,
                () -> SetMealParser.parse(input));
        assertEquals("Missing foodSize for set meal.", e.getMessage());
    }

    @Test
    public void parse_missingMealType_throwsException() {
        String input = "mealType/ foodSize/Large hasDrinks/true";
        DukeException e = assertThrows(DukeException.class,
                () -> SetMealParser.parse(input));
        assertEquals("Missing mealType for set meal.", e.getMessage());
    }
}
