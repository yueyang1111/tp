package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InventoryDockException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PetFoodParserTest {
    @Test
    public void parse_validInput_success() {
        String input = "petType/Cat brand/Whiskas isDryFood/true";
        assertDoesNotThrow(() -> PetFoodParser.parse(input));
    }

    @Test
    public void parse_missingPetType_throwsException() {
        String input = "petType/ brand/Whiskas isDryFood/true";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> PetFoodParser.parse(input));
        assertEquals("Missing petType for pet food.", e.getMessage());
    }

    @Test
    public void parse_missingBrand_throwsException() {
        String input = "petType/Cat brand/ isDryFood/true";
        InventoryDockException e = assertThrows(InventoryDockException.class,
                () -> PetFoodParser.parse(input));
        assertEquals("Missing brand for pet food.", e.getMessage());
    }
}
