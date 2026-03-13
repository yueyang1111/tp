package seedu.duke.parser;

import seedu.duke.command.Command;

public class AddCommandParser {

    public Command parse(String input) {
        String[] words = input.split(" ");
        String categoryToken = words[1];

        String[] categoryTokenParts = categoryToken.split("/", 2);
        String category = categoryTokenParts[1];

        AddItemCommandParser parser = new AddItemCommandParser();

        switch (category) {
        case "fruits":
            return parser.handleFruit(words);
        case "snacks":
            return parser.handleSnack(words);
        case "toiletries":
            return parser.handleToiletries(words);
        case "vegetables":
            return parser.handleVegetables(words);
        default:
            System.out.println("Unknown category: " + category);
            return null;
        }
    }
}
