package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SeafoodParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "seafoodType/Fish origin/Norway isFrozen/true";
        assertDoesNotThrow(() -> SeafoodParser.parse(input));
    }

    @Test
    public void parse_missingOrigin_throwsException() {
        String input = "seafoodType/Fish origin/ isFrozen/true";
        DukeException e = assertThrows(DukeException.class,
                () -> SeafoodParser.parse(input));
        assertEquals("Missing origin for seafood.", e.getMessage());
    }

    @Test
    public void parse_missingSeafoodType_throwsException() {
        String input = "seafoodType/ origin/Norway isFrozen/true";
        DukeException e = assertThrows(DukeException.class,
                () -> SeafoodParser.parse(input));
        assertEquals("Missing seafoodType for seafood.", e.getMessage());
    }
}
