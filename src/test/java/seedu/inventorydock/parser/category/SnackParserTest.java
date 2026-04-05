package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SnackParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "brand/Lays, isCrunchy/true";
        assertDoesNotThrow(() -> SnackParser.parse(input));
    }

    @Test
    public void parse_missingBrand_throwsException() {
        String input = "brand/";
        DukeException e = assertThrows(DukeException.class,
                () -> SnackParser.parse(input));
        assertEquals("Missing brand for snack.", e.getMessage());
    }

    @Test
    public void parse_missingCrunchiness_throwsException() {
        String input = "brand/Lays isCrunchy/";
        DukeException e = assertThrows(DukeException.class,
                () -> SnackParser.parse(input));
        assertEquals("Missing crunchiness for snack.", e.getMessage());
    }
}

