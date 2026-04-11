package seedu.inventorydock.parser.category;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.MissingArgumentException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputValidatorTest {
    @Test
    public void validate_validInput_success() {
        String input = "category/FRUITS item/bread bin/0 qty/1 expiryDate/2026-03-20 size/100 isRipe/true";

        assertDoesNotThrow(() -> InputValidator.validate(input,
                "category/", "item/", "bin/", "qty/", "expiryDate/", "size/", "isRipe/"));
    }

    @Test
    public void validate_missingField_throwsException() {
        String input = "category/FRUITS item/bread bin/0 expiryDate/2026-03-20 size/100 isRipe/true";

        MissingArgumentException e = assertThrows(MissingArgumentException.class,
                () -> InputValidator.validate(input,
                        "category/", "item/", "bin/", "qty/", "expiryDate/", "size/", "isRipe/"));

        assertEquals("Missing required field: qty/", e.getMessage());
    }

    @Test
    public void validate_duplicateQty_throwsException() {
        String input = "category/FRUITS item/bread bin/0 qty/1 qty/12301 expiryDate/2026-03-20 size/100 isRipe/true";

        InvalidCommandException e = assertThrows(InvalidCommandException.class,
                () -> InputValidator.validate(input,
                        "category/", "item/", "bin/", "qty/", "expiryDate/", "size/", "isRipe/"));

        assertEquals("Duplicate field: qty/. Please provide each field only once. " +
                "Run 'help' for supported command.", e.getMessage());
    }

    @Test
    public void validate_duplicateExpiryDate_throwsException() {
        String input = "category/FRUITS item/bread bin/0 qty/1 expiryDate/2026-03-20 expiryDate/2026-03-21 "
                + "size/100 isRipe/true";

        InvalidCommandException e = assertThrows(InvalidCommandException.class,
                () -> InputValidator.validate(input,
                        "category/", "item/", "bin/", "qty/", "expiryDate/", "size/", "isRipe/"));

        assertEquals("Duplicate field: expiryDate/. Please provide each field only once. " +
                "Run 'help' for supported command.", e.getMessage());
    }

    @Test
    public void validate_wrongOrder_throwsException() {
        String input = "category/FRUITS item/bread qty/1 bin/0 expiryDate/2026-03-20 size/100 isRipe/true";

        InvalidCommandException e = assertThrows(InvalidCommandException.class,
                () -> InputValidator.validate(input,
                        "category/", "item/", "bin/", "qty/", "expiryDate/", "size/", "isRipe/"));

        assertEquals("Fields must follow the correct order. Run 'help' for supported command.", e.getMessage());
    }
}
