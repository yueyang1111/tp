package seedu.duke.parser.category;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccessoriesParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "type/Clip material/Plastic isFragile/true";
        assertDoesNotThrow(() -> AccessoriesParser.parse(input));
    }

    @Test
    public void parse_missingType_throwsException() {
        String input = "type/ material/Plastic isFragile/true";
        DukeException e = assertThrows(DukeException.class,
                () -> AccessoriesParser.parse(input));
        assertEquals("Missing type for accessories.", e.getMessage());
    }

    @Test
    public void parse_missingMaterial_throwsException() {
        String input = "type/Clip material/ isFragile/true";
        DukeException e = assertThrows(DukeException.class,
                () -> AccessoriesParser.parse(input));
        assertEquals("Missing material for accessories.", e.getMessage());
    }
}
