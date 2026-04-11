package seedu.inventorydock.parser;

import seedu.inventorydock.exception.InvalidDateException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Utility class for validating and parsing date strings.
 */
public class DateParser {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT);

    /**
     * Validates that the given date string is nonempty and follows the expected format of yyyy-M-d.
     *
     * @param date The date string to validate.
     * @throws InvalidDateException If the date is missing or invalid.
     */
    public static void validateDate(String date) throws InvalidDateException {
        if (date == null || date.trim().isEmpty()) {
            throw new InvalidDateException("Missing expiry date");
        }

        parseDate(date);
    }

    /**
     * Parse the given date string into a LocalDate object.
     *
     * @param date The date string to parse.
     * @return The corresponding LocalDate object.
     * @throws InvalidDateException If the date is missing or invalid.
     */
    public static LocalDate parseDate(String date) throws InvalidDateException {
        if (date == null || date.trim().isEmpty()) {
            throw new InvalidDateException("Missing expiry date");
        }

        try {
            return LocalDate.parse(date.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Invalid date. Please enter a valid calendar date in yyyy-M-d format.");
        }
    }
}
