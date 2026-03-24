package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.category.SweetsParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SweetsParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "brand/Haribo sweetnessLevel/High";
        assertDoesNotThrow(() -> SweetsParser.parse(input));
    }

    @Test
    public void parse_missingBrand_throwsException() {
        String input = "brand/ sweetnessLevel/High";
        DukeException e = assertThrows(DukeException.class,
                () -> SweetsParser.parse(input));
        assertEquals("Missing brand for sweets.", e.getMessage());
    }

    @Test
    public void parse_missingSweetnessLevel_throwsException() {
        String input = "brand/Haribo sweetnessLevel/";
        DukeException e = assertThrows(DukeException.class,
                () -> SweetsParser.parse(input));
        assertEquals("Missing sweetness level for sweets.", e.getMessage());
    }
}
