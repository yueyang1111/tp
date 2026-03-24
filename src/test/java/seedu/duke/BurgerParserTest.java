package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.category.BurgerParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BurgerParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "expiryDate/2026-03-25 isSpicy/true pattyType/Chicken";
        assertDoesNotThrow(() -> BurgerParser.parse(input));
    }

    @Test
    public void parse_missingPattyType_throwsException() {
        String input = "expiryDate/2026-03-25 isSpicy/true pattyType/";
        DukeException e = assertThrows(DukeException.class,
                () -> BurgerParser.parse(input));
        assertEquals("Missing pattyType for burger.", e.getMessage());
    }

    @Test
    public void parse_invalidSpicyValue_throwsException() {
        String input = "expiryDate/2026-03-25 isSpicy/maybe pattyType/Chicken";
        DukeException e = assertThrows(DukeException.class,
                () -> BurgerParser.parse(input));
        assertEquals("isSpicy must be true or false", e.getMessage());
    }
}
