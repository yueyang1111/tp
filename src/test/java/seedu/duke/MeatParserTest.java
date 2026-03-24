package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.category.MeatParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MeatParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "meatType/Beef origin/Australia";
        assertDoesNotThrow(() -> MeatParser.parse(input));
    }

    @Test
    public void parse_missingMeatType_throwsException() {
        String input = "meatType/ origin/Australia";
        DukeException e = assertThrows(DukeException.class,
                () -> MeatParser.parse(input));
        assertEquals("Missing meatType for meat.", e.getMessage());
    }

    @Test
    public void parse_missingOrigin_throwsException() {
        String input = "meatType/Beef origin/";
        DukeException e = assertThrows(DukeException.class,
                () -> MeatParser.parse(input));
        assertEquals("Missing origin for meat.", e.getMessage());
    }
}
