package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.category.VegetableParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VegetableParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "expiryDate/2026-03-18 isLeafy/true";
        assertDoesNotThrow(() -> VegetableParser.parse(input));
    }

    @Test
    public void parse_missingExpiryDate_throwsException() {
        String input = "expiryDate/ isLeafy/true";
        DukeException e = assertThrows(DukeException.class,
                () -> VegetableParser.parse(input));
        assertEquals("Missing expiry date for vegetable.", e.getMessage());
    }

    @Test
    public void parse_missingRipeness_throwsException() {
        String input = "expiryDate/2026-03-18 isLeafy/";
        DukeException e = assertThrows(DukeException.class,
                () -> VegetableParser.parse(input));
        assertEquals("Missing leafy field for vegetable.", e.getMessage());
    }

    @Test
    public void parse_invalidRipeness_throwsException() {
        String input = "expiryDate/2026-03-18 isLeafy/hi";
        DukeException e = assertThrows(DukeException.class,
                () -> VegetableParser.parse(input));
        assertEquals("Leafy field must be true or false.", e.getMessage());
    }
}
