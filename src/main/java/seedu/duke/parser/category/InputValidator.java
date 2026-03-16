package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;

public class InputValidator {
    private static void validateRequiredFields(
            String input, String... fields) throws DukeException {
        for (String field : fields) {
            if (!input.contains(field)) {
                throw new DukeException("Missing required field: " + field);
            }
        }
    }

    private static void validateOrder(
            String input, String... fields) throws DukeException {
        int previous = -1;

        for (String field : fields) {
            int current = input.indexOf(field);

            if (current != -1) {
                if (current < previous) {
                    throw new DukeException(
                            "Fields must follow the correct order.");
                }
                previous = current;
            }
        }
    }

    public static void validate(
            String input, String... fields) throws DukeException {
        assert input != null : "InputValidator received null input.";
        assert fields != null && fields.length > 0 : "Fields must be provide for validation.";

        validateRequiredFields(input, fields);
        validateOrder(input, fields);
    }
}
