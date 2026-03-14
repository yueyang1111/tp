package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.category.FruitParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FruitParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "expiryDate/2026-03-20 size/big isRipe/true";
        assertDoesNotThrow(() -> FruitParser.parse(input));
    }

    @Test
    public void parse_missingExpiryDate_throwsException() {
        String input = "expiryDate/ size/big isRipe/true";
        DukeException e = assertThrows(DukeException.class,
                () -> FruitParser.parse(input));
        assertEquals("Missing expiry date for fruit.", e.getMessage());
    }

    @Test
    public void parse_missingSize_throwsException() {
        String input = "expiryDate/2026-03-20 size/ isRipe/true";
        DukeException e = assertThrows(DukeException.class,
                () -> FruitParser.parse(input));
        assertEquals("Missing size for fruit.", e.getMessage());
    }

    @Test
    public void parse_missingRipeness_throwsException() {
        String input = "expiryDate/2026-03-20 size/big isRipe/";
        DukeException e = assertThrows(DukeException.class,
                () -> FruitParser.parse(input));
        assertEquals("Missing ripeness for fruit.", e.getMessage());
    }

    @Test
    public void parse_invalidRipeness_throwsException() {
        String input = "expiryDate/2026-03-20 size/big isRipe/hi";
        DukeException e = assertThrows(DukeException.class,
                () -> FruitParser.parse(input));
        assertEquals("Ripeness must be true or false", e.getMessage());
    }
}
