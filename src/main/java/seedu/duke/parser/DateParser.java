package seedu.duke.parser;

import seedu.duke.exception.DukeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-M-d");

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
