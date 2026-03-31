package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddItemCommandParserTest {
    @Test
    public void handleFruit_validInput_success() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/fruits item/apple bin/A-10 qty/3 "
                + "expiryDate/2026-03-20 size/big isRipe/true";

        assertDoesNotThrow(() -> parser.handleFruit(input));
    }

    @Test
    public void handleFruit_missingItemField_throwsException() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/fruits bin/A-10 qty/3 "
                + "expiryDate/2026-03-20 size/big isRipe/true";

        DukeException e = assertThrows(DukeException.class,
                () -> parser.handleFruit(input));
        assertEquals("Missing required field: item/", e.getMessage());
    }

    @Test
    public void handleFruit_missingCategoryField_throwsException() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "item/apple bin/A-10 qty/3 "
                + "expiryDate/2026-03-20 size/big isRipe/true";

        DukeException e = assertThrows(DukeException.class,
                () -> parser.handleFruit(input));
        assertEquals("Missing required field: category/", e.getMessage());
    }

    @Test
    public void handleFruit_missingBinField_throwsException() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/fruits item/apple qty/3 "
                + "expiryDate/2026-03-20 size/big isRipe/true";

        DukeException e = assertThrows(DukeException.class,
                () -> parser.handleFruit(input));
        assertEquals("Missing required field: bin/", e.getMessage());
    }

    @Test
    public void handleFruit_missingQuantityField_throwsException() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/fruits item/apple bin/A-10 "
                + "expiryDate/2026-03-20 size/big isRipe/true";

        DukeException e = assertThrows(DukeException.class,
                () -> parser.handleFruit(input));
        assertEquals("Missing required field: qty/", e.getMessage());
    }

    @Test
    public void handleFruit_missingExpiryDateField_throwsException() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/fruits item/apple bin/A-10 qty/3 "
                + "size/big isRipe/true";

        DukeException e = assertThrows(DukeException.class,
                () -> parser.handleFruit(input));
        assertEquals("Missing required field: expiryDate/", e.getMessage());
    }

    @Test
    public void handleFruit_missingSizeField_throwsException() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/fruits item/apple bin/A-10 qty/3 "
                + "expiryDate/2026-03-20 isRipe/true";

        DukeException e = assertThrows(DukeException.class,
                () -> parser.handleFruit(input));
        assertEquals("Missing required field: size/", e.getMessage());
    }

    @Test
    public void handleFruit_missingRipenessField_throwsException() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/fruits item/apple bin/A-10 qty/3 "
                + "expiryDate/2026-03-20 size/big";

        DukeException e = assertThrows(DukeException.class,
                () -> parser.handleFruit(input));
        assertEquals("Missing required field: isRipe/", e.getMessage());
    }

    @Test
    public void handleFruit_wrongFieldOrder_throwsException() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/fruits item/apple bin/A-10 qty/3 "
                + "expiryDate/2026-03-20 isRipe/true size/big";

        DukeException e = assertThrows(DukeException.class,
                () -> parser.handleFruit(input));
        assertEquals("Fields must follow the correct order. Run 'help' for supported command.", e.getMessage());
    }

    @Test
    public void handleFruit_nonIntegerQuantity_throwsException() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/fruits item/apple bin/A-10 qty/hi "
                + "expiryDate/2026-03-20 size/big isRipe/true";

        DukeException e = assertThrows(DukeException.class,
                () -> parser.handleFruit(input));
        assertEquals("Quantity must be an integer.", e.getMessage());
    }

    @Test
    public void handleFruit_nonPositiveQuantity_throwsException() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/fruits item/apple bin/A-10 qty/-3 "
                + "expiryDate/2026-03-20 size/big isRipe/true";

        DukeException e = assertThrows(DukeException.class,
                () -> parser.handleFruit(input));
        assertEquals("Quantity must be a positive integer.", e.getMessage());
    }

    @Test
    public void handleToiletries_validInput_success() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/toiletries item/shampoo bin/E-02 qty/15 "
                + "expiryDate/2026-03-20 brand/Dove isLiquid/true";

        assertDoesNotThrow(() -> parser.handleToiletries(input));
    }

    @Test
    public void handleToiletries_missingExpiryDateField_throwsException() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/toiletries item/shampoo bin/E-02 qty/15 "
                + "brand/Dove isLiquid/true";

        DukeException e = assertThrows(DukeException.class,
                () -> parser.handleToiletries(input));
        assertEquals("Missing required field: expiryDate/", e.getMessage());
    }
}
