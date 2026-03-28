package seedu.duke.parser;

import org.junit.jupiter.api.Test;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.BinLocationParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinLocationParserTest {
    @Test
    public void parseSearchInput_validInputs_returnsNormalizedInput() throws DukeException {
        assertEquals("a-10", BinLocationParser.parseSearchInput("A-10"));
        assertEquals("a", BinLocationParser.parseSearchInput("A"));
        assertEquals("10", BinLocationParser.parseSearchInput("10"));
    }

    @Test
    public void parseSearchInput_invalidInputs_throwsException() {
        DukeException exception = assertThrows(DukeException.class,
                () -> BinLocationParser.parseSearchInput("A10"));
        assertEquals("Bin search must be either LETTER-NUMBER (e.g. A-10), "
                + "LETTER only (e.g. A), or NUMBER only (e.g. 10).",
                exception.getMessage());

        assertThrows(DukeException.class, () -> BinLocationParser.parseSearchInput("AA-10"));
        assertThrows(DukeException.class, () -> BinLocationParser.parseSearchInput("10-A"));
        assertThrows(DukeException.class, () -> BinLocationParser.parseSearchInput("A-10-1"));
    }

    @Test
    public void isMatchingBin_validSearchTypes_returnsExpectedMatches() {
        assertTrue(BinLocationParser.isMatchingBin("A-10", "a-10"));
        assertTrue(BinLocationParser.isMatchingBin("A-10", "a"));
        assertTrue(BinLocationParser.isMatchingBin("A-10", "10"));

        assertFalse(BinLocationParser.isMatchingBin("A-10", "a-1"));
        assertFalse(BinLocationParser.isMatchingBin("A-10", "1"));
        assertFalse(BinLocationParser.isMatchingBin("B-10", "a"));
    }
}
