package seedu.duke.parser.category;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.category.SetMealParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SetMealParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "mealType/Western foodSize/Large";
        assertDoesNotThrow(() -> SetMealParser.parse(input));
    }

    @Test
    public void parse_missingFoodSize_throwsException() {
        String input = "mealType/Western foodSize/";
        DukeException e = assertThrows(DukeException.class,
                () -> SetMealParser.parse(input));
        assertEquals("Missing foodSize for set meal.", e.getMessage());
    }

    @Test
    public void parse_missingMealType_throwsException() {
        String input = "mealType/ foodSize/Large";
        DukeException e = assertThrows(DukeException.class,
                () -> SetMealParser.parse(input));
        assertEquals("Missing mealType for set meal.", e.getMessage());
    }
}
