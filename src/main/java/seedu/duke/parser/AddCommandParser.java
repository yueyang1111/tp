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

        String[] tokens = trimmedInput.split(" ");

        String itemName = null;
        String category = null;

        for (String token : tokens) {

            if (token.startsWith("item/")) {
                itemName = token.substring("item/".length()).trim();
            }

            if (token.startsWith("category/")) {
                category = token.substring("category/".length()).trim().toLowerCase();
            }

            if (itemName != null && category != null) {
                break;
            }
        }

        if (itemName == null || itemName.isEmpty()) {
            throw new DukeException("Missing item name.");
        }

        if (category == null || category.isEmpty()) {
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
