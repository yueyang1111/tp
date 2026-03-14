package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.exception.DukeException;

public class AddCommandParser {
    public Command parse(String input) throws DukeException {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            throw new DukeException("Incomplete add command.");
        }

        String itemName = FieldParser.extractField(trimmed, "item/", "category/");
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new DukeException("Missing item name.");
        }

        String afterCategory = FieldParser.extractField(trimmed, "category/", null);
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
            return parser.handleFruit(trimmed);
        case "snacks":
            return parser.handleSnack(trimmed);
        case "toiletries":
            return parser.handleToiletries(trimmed);
        case "vegetables":
            return parser.handleVegetables(trimmed);
        default:
            throw new DukeException("Unknown category: " + category);
        }
    }
}
