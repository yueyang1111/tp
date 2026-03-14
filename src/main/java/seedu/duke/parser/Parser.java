package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.ListCommand;
import seedu.duke.exception.DukeException;

public class Parser {

    public Command parse(String input) throws DukeException {
        String trimmed = input.trim();

        if (trimmed.isEmpty()) {
            throw new DukeException("Input cannot be empty.");
        }

        String[] parts = trimmed.split(" ", 2);
        String commandWord = parts[0].toLowerCase();

        switch (commandWord) {
        case "add":
            String addInput = parts.length > 1 ? parts[1].trim() : "";
            return new AddCommandParser().parse(addInput);
        case "list":
            return new ListCommand();
        default:
            throw new DukeException("Unknown command");
        }
    }
}
