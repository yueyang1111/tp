package seedu.duke.parser.category;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DrinksParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "brand/CocaCola flavour/Cola, isCarbonated/true";
        assertDoesNotThrow(() -> DrinksParser.parse(input));
    }

    @Test
    public void parse_missingBrand_throwsException() {
        String input = "brand/ flavour/Cola isCarbonated/true";
        DukeException e = assertThrows(DukeException.class,
                () -> DrinksParser.parse(input));
        assertEquals("Missing brand for drinks.", e.getMessage());
    }

    @Test
    public void parse_missingFlavour_throwsException() {
        String input = "brand/PepsiCola flavour/ isCarbonated/true";
        DukeException e = assertThrows(DukeException.class,
                () -> DrinksParser.parse(input));
        assertEquals("Missing flavour for drinks.", e.getMessage());
    }

}
