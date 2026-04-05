package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SweetsParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "brand/Haribo sweetnessLevel/High, isChewy/true";
        assertDoesNotThrow(() -> SweetsParser.parse(input));
    }

    @Test
    public void parse_missingBrand_throwsException() {
        String input = "brand/ sweetnessLevel/High isChewy/true";
        DukeException e = assertThrows(DukeException.class,
                () -> SweetsParser.parse(input));
        assertEquals("Missing brand for sweets.", e.getMessage());
    }

    @Test
    public void parse_missingSweetnessLevel_throwsException() {
        String input = "brand/Haribo sweetnessLevel/ isChewy/true";
        DukeException e = assertThrows(DukeException.class,
                () -> SweetsParser.parse(input));
        assertEquals("Missing sweetness level for sweets.", e.getMessage());
    }
}
