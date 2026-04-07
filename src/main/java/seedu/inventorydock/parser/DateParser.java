package seedu.inventorydock.parser;

import seedu.inventorydock.exception.InvalidDateException;

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
     * @throws InventoryDockException If the date is missing or invalid.
     */
    public static void validateDate(String date) throws InvalidDateException {
        if (date == null || date.trim().isEmpty()) {
            throw new InvalidDateException("Missing expiry date");
        }

        try {
            parseDate(date);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Invalid date. Please use yyyy-M-d.");
        }
    }

    /**
     * Parse the given date string into a LocalDate object.
     *
     * @param date The date string to parse.
     * @return The corresponding LocalDate object.
     * @throws InventoryDockException If the date is missing or invalid.
     */
    public static LocalDate parseDate(String date) throws InvalidDateException {
        if (date == null || date.trim().isEmpty()) {
            throw new InvalidDateException("Missing expiry date");
        }

        try {
            return LocalDate.parse(date.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Invalid date. Please use yyyy-M-d.");
        }
    }
}
