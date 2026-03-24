package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.category.PetFoodParser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PetFoodParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "expiryDate/2026-07-01 petType/Cat brand/Whiskas";
        assertDoesNotThrow(() -> PetFoodParser.parse(input));
    }

    @Test
    public void parse_missingPetType_throwsException() {
        String input = "expiryDate/2026-07-01 petType/ brand/Whiskas";
        DukeException e = assertThrows(DukeException.class,
                () -> PetFoodParser.parse(input));
        assertEquals("Missing petType for pet food.", e.getMessage());
    }

    @Test
    public void parse_missingBrand_throwsException() {
        String input = "expiryDate/2026-07-01 petType/Cat brand/";
        DukeException e = assertThrows(DukeException.class,
                () -> PetFoodParser.parse(input));
        assertEquals("Missing brand for pet food.", e.getMessage());
    }
}
