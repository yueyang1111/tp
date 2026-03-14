package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.ExitCommand;
import seedu.duke.command.HelpCommand;
import seedu.duke.command.ListCommand;
import seedu.duke.ui.UI;

public class Parser {

    private final UI ui;

    public Parser(UI ui) {
        this.ui = ui;
    }

    public Command parse(String input) {
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
                return new AddCommandParser(ui).parse(arguments);
            case "delete":
                return new DeleteCommandParser(ui).parse(arguments);
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