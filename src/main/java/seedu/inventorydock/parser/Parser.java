package seedu.inventorydock.parser;

import seedu.inventorydock.command.Command;
import seedu.inventorydock.command.ExitCommand;
import seedu.inventorydock.command.HelpCommand;
import seedu.inventorydock.command.ListCommand;
import seedu.inventorydock.exception.DukeException;
import seedu.inventorydock.ui.UI;

public class Parser {

    private final UI ui;

    public Parser(UI ui) {
        this.ui = ui;
    }

    public Command parse(String input) throws DukeException {
        assert input != null : "Parser received null input.";

        String trimmed = input.trim();

        if (trimmed.isEmpty()) {
            ui.showEmptyInput();
            return null;
        }

        String[] parts = trimmed.split(" ", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        switch (commandWord) {
        case "add":
            return new AddCommandParser().parse(arguments);
        case "delete":
            return new DeleteCommandParser(ui).parse(arguments);
        case "update":
            return new UpdateCommandParser(ui).parse(arguments);
        case "find":
            return new FindItemParser(ui).parse(arguments);
        case "sort":
            return new SortCommandParser().parse(arguments);
        case "list":
            return new ListCommand();
        case "help":
            return new HelpCommand();
        case "bye":
            return new ExitCommand();
        default:
            ui.showUnknownCommand();
            return null;
        }
    }
}

