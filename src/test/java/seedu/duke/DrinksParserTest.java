package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.category.DrinksParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DrinksParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "brand/CocaCola flavour/Cola";
        assertDoesNotThrow(() -> DrinksParser.parse(input));
    }

    @Test
    public void parse_missingBrand_throwsException() {
        String input = "brand/ flavour/Cola ";
        DukeException e = assertThrows(DukeException.class,
                () -> DrinksParser.parse(input));
        assertEquals("Missing brand for drinks.", e.getMessage());
    }

    @Test
    public void parse_missingFlavour_throwsException() {
        String input = "brand/PepsiCola flavour/ ";
        DukeException e = assertThrows(DukeException.class,
                () -> DrinksParser.parse(input));
        assertEquals("Missing flavour for drinks.", e.getMessage());
    }

}
