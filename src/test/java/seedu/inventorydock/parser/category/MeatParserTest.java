package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MeatParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "meatType/Beef origin/Australia isFrozen/true";
        assertDoesNotThrow(() -> MeatParser.parse(input));
    }

    @Test
    public void parse_missingMeatType_throwsException() {
        String input = "meatType/ origin/Australia isFrozen/true";
        DukeException e = assertThrows(DukeException.class,
                () -> MeatParser.parse(input));
        assertEquals("Missing meatType for meat.", e.getMessage());
    }

    @Test
    public void parse_missingOrigin_throwsException() {
        String input = "meatType/Beef origin/ isFrozen/true";
        DukeException e = assertThrows(DukeException.class,
                () -> MeatParser.parse(input));
        assertEquals("Missing origin for meat.", e.getMessage());
    }
}
