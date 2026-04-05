package seedu.inventorydock.parser;

import seedu.inventorydock.exception.DukeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for validating and parsing date strings.
 */
public class DateParser {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-M-d");

    /**
     * Validates that the given date string is nonempty and follows the expected format of yyyy-M-d.
     *
     * @param date The date string to validate.
     * @throws DukeException If the date is missing or invalid.
     */
    public static void validateDate(String date) throws DukeException {
        if (date == null || date.trim().isEmpty()) {
            throw new DukeException("Missing expiry date");
        }

        try {
            parseDate(date);
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid date. Please use yyyy-M-d.");
        }
    }

    /**
     * Parse the given date string into a LocalDate object.
     *
     * @param date The date string to parse.
     * @return The corresponding LocalDate object.
     * @throws DukeException If the date is missing or invalid.
     */
    public static LocalDate parseDate(String date) throws DukeException {
        if (date == null || date.trim().isEmpty()) {
            throw new DukeException("Missing expiry date");
        }

        try {
            return LocalDate.parse(date.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid date. Please use yyyy-M-d.");
        }
    }
}
