package seedu.inventorydock.parser;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.DukeException;

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
    public void handleSnack_validInput_success() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/snacks item/chips bin/S-07 qty/6 "
                + "expiryDate/2026-06-01 brand/Lays isCrunchy/true";

        assertDoesNotThrow(() -> parser.handleSnack(input));
    }

    @Test
    public void handleVegetables_validInput_success() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/vegetables item/spinach bin/V-01 qty/4 "
                + "expiryDate/2026-03-18 isLeafy/true";

        assertDoesNotThrow(() -> parser.handleVegetables(input));
    }

    @Test
    public void handleDrinks_validInput_success() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/drinks item/cola bin/D-02 qty/8 "
                + "expiryDate/2026-05-10 brand/Coke flavour/Original isCarbonated/true";

        assertDoesNotThrow(() -> parser.handleDrinks(input));
    }

    @Test
    public void handleIceCream_validInput_success() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/icecream item/vanilla bin/I-03 qty/5 "
                + "expiryDate/2026-04-22 flavour/Vanilla isDairyFree/false";

        assertDoesNotThrow(() -> parser.handleIceCream(input));
    }

    @Test
    public void handleSweets_validInput_success() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/sweets item/gummy bin/W-02 qty/9 "
                + "expiryDate/2026-07-14 brand/Haribo sweetnessLevel/high isChewy/true";

        assertDoesNotThrow(() -> parser.handleSweets(input));
    }

    @Test
    public void handleSetMeal_validInput_success() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/setmeal item/chickenrice bin/M-01 qty/3 "
                + "expiryDate/2026-03-21 mealType/lunch foodSize/large hasDrinks/true";

        assertDoesNotThrow(() -> parser.handleSetMeal(input));
    }

    @Test
    public void handleSeafood_validInput_success() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/seafood item/salmon bin/F-04 qty/7 "
                + "expiryDate/2026-04-30 seafoodType/fish origin/Norway isFrozen/true";

        assertDoesNotThrow(() -> parser.handleSeafood(input));
    }

    @Test
    public void handleMeat_validInput_success() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/meat item/beef bin/M-02 qty/6 "
                + "expiryDate/2026-04-11 meatType/red origin/Australia isFrozen/true";

        assertDoesNotThrow(() -> parser.handleMeat(input));
    }

    @Test
    public void handlePetFood_validInput_success() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/petfood item/kibble bin/P-05 qty/12 "
                + "expiryDate/2026-08-01 petType/dog brand/Pedigree isDryFood/true";

        assertDoesNotThrow(() -> parser.handlePetFood(input));
    }

    @Test
    public void handleAccessories_validInput_success() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/accessories item/mug bin/A-04 qty/4 "
                + "expiryDate/2027-01-01 type/kitchen material/ceramic isFragile/true";

        assertDoesNotThrow(() -> parser.handleAccessories(input));
    }

    @Test
    public void handleBurger_validInput_success() {
        AddItemCommandParser parser = new AddItemCommandParser();
        String input = "category/burger item/zinger bin/B-07 qty/2 "
                + "expiryDate/2026-03-25 isSpicy/true pattyType/chicken";

        assertDoesNotThrow(() -> parser.handleBurger(input));
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

