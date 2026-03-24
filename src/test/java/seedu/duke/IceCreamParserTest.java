package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.category.IceCreamParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IceCreamParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "expiryDate/2026-03-28 flavour/Vanilla isDairyFree/false";
        assertDoesNotThrow(() -> IceCreamParser.parse(input));
    }

    @Test
    public void parse_missingFlavour_throwsException() {
        String input = "expiryDate/2026-03-28 flavour/ isDairyFree/false";
        DukeException e = assertThrows(DukeException.class,
                () -> IceCreamParser.parse(input));
        assertEquals("Missing flavour for ice cream.", e.getMessage());
    }

    @Test
    public void parse_invalidBoolean_throwsException() {
        String input = "expiryDate/2026-03-28 flavour/Vanilla isDairyFree/maybe";
        DukeException e = assertThrows(DukeException.class,
                () -> IceCreamParser.parse(input));
        assertEquals("isDairyFree must be true or false", e.getMessage());
    }
}
