package seedu.inventorydock.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FieldParserTest {

    // ---- Basic extraction with start and end markers ----

    @Test
    public void extractField_validStartAndEnd_returnsValue() {
        String input = "category/fruits item/apple bin/A-1";
        String result = FieldParser.extractField(input, "item/", "bin/");
        assertEquals("apple", result);
    }

    @Test
    public void extractField_valueWithSpaces_returnsTrimmedValue() {
        String input = "category/fruits item/  green apple  bin/A-1";
        String result = FieldParser.extractField(input, "item/", "bin/");
        assertEquals("green apple", result);
    }

    @Test
    public void extractField_startKeyNotFound_returnsNull() {
        String input = "category/fruits bin/A-1";
        String result = FieldParser.extractField(input, "item/", "bin/");
        assertNull(result);
    }

    @Test
    public void extractField_endKeyNotFound_extractsToEnd() {
        String input = "category/fruits item/apple";
        String result = FieldParser.extractField(input, "item/", "bin/");
        assertEquals("apple", result);
    }

    // ---- Extraction with null end marker ----

    @Test
    public void extractField_nullEndKey_extractsToEnd() {
        String input = "category/fruits item/apple";
        String result = FieldParser.extractField(input, "item/", null);
        assertEquals("apple", result);
    }

    @Test
    public void extractField_nullEndKeyWithTrailingSpaces_returnsTrimmed() {
        String input = "category/fruits item/apple   ";
        String result = FieldParser.extractField(input, "item/", null);
        assertEquals("apple", result);
    }

    // ---- Edge cases ----

    @Test
    public void extractField_emptyValueBetweenMarkers_returnsEmpty() {
        String input = "category/fruits item/bin/A-1";
        String result = FieldParser.extractField(input, "item/", "bin/");
        assertEquals("", result);
    }

    @Test
    public void extractField_emptyInput_returnsNull() {
        String result = FieldParser.extractField("", "item/", "bin/");
        assertNull(result);
    }

    @Test
    public void extractField_firstField_extractsCorrectly() {
        String input = "category/fruits item/apple bin/A-1";
        String result = FieldParser.extractField(input, "category/", "item/");
        assertEquals("fruits", result);
    }

    @Test
    public void extractField_lastFieldWithNullEnd_extractsCorrectly() {
        String input = "category/fruits item/apple bin/A-1 qty/10";
        String result = FieldParser.extractField(input, "qty/", null);
        assertEquals("10", result);
    }

    @Test
    public void extractField_lastFieldWithEndMarker_extractsToEndOfString() {
        String input = "category/fruits item/apple qty/10";
        String result = FieldParser.extractField(input, "qty/", "nonexistent/");
        assertEquals("10", result);
    }

    @Test
    public void extractField_multipleWordsInValue_extractsAll() {
        String input = "category/instant noodles item/cup noodle bin/A-1";
        String result = FieldParser.extractField(input, "category/", "item/");
        assertEquals("instant noodles", result);
    }
}
