package seedu.duke.parser.category;

import seedu.duke.exception.DukeException;

public class InputValidator {
    public static void validate(
            String input, String... fields) throws DukeException {
        assert input != null : "InputValidator received null input.";
        assert fields != null && fields.length > 0 : "Fields must be provide for validation.";

        int previous = -1;

        for (String field : fields) {
            int current = input.indexOf(field);

            if (current == -1) {
                throw new DukeException("Missing required field: " + field);
            }

            if (current < previous) {
                throw new DukeException(
                        "Fields must follow the correct order.");
            }
            previous = current;
        }
    }
}

