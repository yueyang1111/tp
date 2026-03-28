package seedu.duke.parser.category;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.category.SeafoodParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SeafoodParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "seafoodType/Fish origin/Norway";
        assertDoesNotThrow(() -> SeafoodParser.parse(input));
    }

    @Test
    public void parse_missingOrigin_throwsException() {
        String input = "seafoodType/Fish origin/";
        DukeException e = assertThrows(DukeException.class,
                () -> SeafoodParser.parse(input));
        assertEquals("Missing origin for seafood.", e.getMessage());
    }

    @Test
    public void parse_missingSeafoodType_throwsException() {
        String input = "seafoodType/ origin/Norway";
        DukeException e = assertThrows(DukeException.class,
                () -> SeafoodParser.parse(input));
        assertEquals("Missing seafoodType for seafood.", e.getMessage());
    }
}
