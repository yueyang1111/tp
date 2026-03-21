package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.category.ToiletriesParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ToiletriesParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "brand/Dove isLiquid/true expiryDate/2026-06-01";
        assertDoesNotThrow(() -> ToiletriesParser.parse(input));
    }

    @Test
    public void parse_missingBrand_throwsException() {
        String input = "brand/ isLiquid/true expiryDate/2026-06-01";
        DukeException e = assertThrows(DukeException.class,
                () -> ToiletriesParser.parse(input));
        assertEquals("Missing brand for toiletries.", e.getMessage());
    }

    @Test
    public void parse_missingLiquid_throwsException() {
        String input = "brand/Dove isLiquid/ expiryDate/2026-06-01";
        DukeException e = assertThrows(DukeException.class,
                () -> ToiletriesParser.parse(input));
        assertEquals("Missing liquid field for toiletries.", e.getMessage());
    }

    @Test
    public void parse_invalidLiquid_throwsException() {
        String input = "brand/Dove isLiquid/hi expiryDate/2026-06-01";
        DukeException e = assertThrows(DukeException.class,
                () -> ToiletriesParser.parse(input));
        assertEquals("Liquid field must be true or false.", e.getMessage());
    }

    @Test
    public void parse_missingExpiryDate_throwsException() {
        String input = "brand/Dove isLiquid/true expiryDate/";
        DukeException e = assertThrows(DukeException.class,
                () -> ToiletriesParser.parse(input));
        assertEquals("Missing expiry date for toiletries.", e.getMessage());
    }
}
