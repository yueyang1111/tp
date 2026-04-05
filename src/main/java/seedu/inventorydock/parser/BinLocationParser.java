package seedu.inventorydock.parser;

import seedu.inventorydock.exception.DukeException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Validates and normalizes bin-search input used by the {@code find bin/...}
 * workflow.
 */
public class BinLocationParser {
    private static final Logger logger = Logger.getLogger(BinLocationParser.class.getName());

    private static final String INVALID_BIN_SEARCH_MESSAGE =
            "Bin search must be either LETTER-NUMBER (e.g. A-10), "
                    + "LETTER only (e.g. A), or NUMBER only (e.g. 10).";

    /**
     * Normalizes a bin search term to lower case and verifies that it is either
     * a full bin location, a single bin letter, or a numeric bin index.
     *
     * @param input raw bin search input.
     * @return normalized bin search input.
     * @throws DukeException if the input does not match a supported bin-search format.
     */
    public static String parseSearchInput(String input) throws DukeException {
        assert input != null : "BinLocationParser received null input.";

        String normalizedInput = input.trim().toLowerCase();

        if (normalizedInput.isEmpty()) {
            logger.log(Level.WARNING, "Bin search input is empty.");
            throw new DukeException(INVALID_BIN_SEARCH_MESSAGE);
        }

        int dashIndex = normalizedInput.indexOf('-');

        if (dashIndex == -1) {
            if (isSingleLetter(normalizedInput) || isInteger(normalizedInput)) {
                return normalizedInput;
            }

            logger.log(Level.WARNING, "Invalid bin search input without dash: " + normalizedInput);
            throw new DukeException(INVALID_BIN_SEARCH_MESSAGE);
        }

        if (dashIndex != normalizedInput.lastIndexOf('-')) {
            logger.log(Level.WARNING, "Invalid bin search input with multiple dashes: " + normalizedInput);
            throw new DukeException(INVALID_BIN_SEARCH_MESSAGE);
        }

        String firstPart = normalizedInput.substring(0, dashIndex);
        String secondPart = normalizedInput.substring(dashIndex + 1);

        if (!isSingleLetter(firstPart) || !isInteger(secondPart)) {
            logger.log(Level.WARNING, "Invalid bin search input format: " + normalizedInput);
            throw new DukeException(INVALID_BIN_SEARCH_MESSAGE);
        }
        return normalizedInput;
    }

    /**
     * Checks whether the input is exactly one alphabetic character.
     *
     * @param input input to validate.
     * @return {@code true} if the input is a single letter.
     */
    private static boolean isSingleLetter(String input) {
        assert input != null : "isSingleLetter received null input.";
        return input.length() == 1 && Character.isLetter(input.charAt(0));
    }

    /**
     * Checks whether the input contains only digits.
     *
     * @param input input to validate.
     * @return {@code true} if the input is a non-empty integer string.
     */
    private static boolean isInteger(String input) {
        assert input != null : "isInteger received null input.";

        if (input.isEmpty()) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
