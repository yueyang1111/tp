package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.UI;

public class AddCommandParser {

    private final UI ui;

    public AddCommandParser(UI ui) {
        this.ui = ui;
    }

    public Command parse(String input) {
        String category = FieldParser.extractField(input, "category/", "bin/");

        if (category == null || category.trim().isEmpty()) {
            ui.showInvalidInput(
                    "Missing category. Use: add item/ITEM category/CATEGORY bin/BIN qty/QTY");
            return null;
        }

        category = category.trim();
        AddItemCommandParser parser = new AddItemCommandParser();

        try {
            switch (category.toLowerCase()) {
                case "fruits":
                    return parser.handleFruit(input);
                case "snacks":
                    return parser.handleSnack(input);
                case "toiletries":
                    return parser.handleToiletries(input);
                case "vegetables":
                    return parser.handleVegetables(input);
                default:
                    ui.showUnknownCategory(category);
                    return null;
            }
        } catch (DukeException e) {
            ui.showError(e.getMessage());
            return null;
        }
    }
}