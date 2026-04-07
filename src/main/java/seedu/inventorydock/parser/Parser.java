package seedu.inventorydock.parser;

import seedu.inventorydock.command.Command;
import seedu.inventorydock.command.ExitCommand;
import seedu.inventorydock.command.HelpCommand;
import seedu.inventorydock.command.ListCommand;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.MissingArgumentException;

public class Parser {

    public Command parse(String input) throws InventoryDockException {
        assert input != null : "Parser received null input.";

        String trimmed = input.trim();

        if (trimmed.isEmpty()) {
            throw new MissingArgumentException("Input is empty.");
        }

        String[] parts = trimmed.split(" ", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        switch (commandWord) {
        case "add":
            return new AddCommandParser().parse(arguments);
        case "delete":
            return new DeleteCommandParser().parse(arguments);
        case "update":
            return new UpdateCommandParser().parse(arguments);
        case "find":
            return new FindItemParser().parse(arguments);
        case "sort":
            return new SortCommandParser().parse(arguments);
        case "list":
            return new ListCommand();
        case "help":
            return new HelpCommand();
        case "bye":
            return new ExitCommand();
        default:
            throw new InvalidCommandException("Unknown command.");
        }
    }
}

