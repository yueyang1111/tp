package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.UI;

public class AddCommandParser {

    private final UI ui;

    public AddCommandParser(UI ui) {
        this.ui = ui;
    }

    public Command parse(String input) throws DukeException {
        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            throw new DukeException("Input is empty.");
        }

        String itemName = FieldParser.extractField(trimmedInput, "item/", "category/");
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new DukeException("Missing item name.");
        }

        String afterCategory = FieldParser.extractField(trimmedInput, "category/", null);
        if (afterCategory == null || afterCategory.trim().isEmpty()) {
            throw new DukeException("Missing category.");
        }

        String category = afterCategory.split(" ", 2)[0].trim().toLowerCase();
        if (category.isEmpty()) {
            throw new DukeException("Missing category.");
        }

        AddItemCommandParser parser = new AddItemCommandParser();

        switch (category) {
        case "fruits":
            return parser.handleFruit(trimmedInput);
        case "snacks":
            return parser.handleSnack(trimmedInput);
        case "toiletries":
            return parser.handleToiletries(trimmedInput);
        case "vegetables":
            return parser.handleVegetables(trimmedInput);
        default:
            throw new DukeException("Unknown category: " + category);
        }
    }
}
