package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.category.SnackParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SnackParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "brand/Lays";
        assertDoesNotThrow(() -> SnackParser.parse(input));
    }

    @Test
    public void parse_missingBrand_throwsException() {
        String input = "brand/";
        DukeException e = assertThrows(DukeException.class,
                () -> SnackParser.parse(input));
        assertEquals("Missing brand for snack.", e.getMessage());
    }
}

