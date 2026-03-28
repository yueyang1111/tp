package seedu.duke.parser.category;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.category.BurgerParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BurgerParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "isSpicy/true pattyType/Chicken";
        assertDoesNotThrow(() -> BurgerParser.parse(input));
    }

    @Test
    public void parse_missingPattyType_throwsException() {
        String input = "isSpicy/true pattyType/";
        DukeException e = assertThrows(DukeException.class,
                () -> BurgerParser.parse(input));
        assertEquals("Missing pattyType for burger.", e.getMessage());
    }

    @Test
    public void parse_invalidSpicyValue_throwsException() {
        String input = "isSpicy/maybe pattyType/Chicken";
        DukeException e = assertThrows(DukeException.class,
                () -> BurgerParser.parse(input));
        assertEquals("isSpicy must be true or false", e.getMessage());
    }
}
