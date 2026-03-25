package seedu.duke;

import seedu.duke.command.Command;
import seedu.duke.exception.DukeException;
import seedu.duke.logging.LoggerConfig;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.parser.Parser;
import seedu.duke.storage.Storage;
import seedu.duke.ui.UI;

public class Duke {
    private final Inventory inventory;
    private final UI ui;
    private final Parser parser;
    private final Storage storage;

    public Duke() throws DukeException {
        ui = new UI();
        inventory = new Inventory();
        parser = new Parser(ui);
        storage = new Storage("./data/inventory.txt");

        String[] categoryNames = {
            "fruits",
            "vegetables",
            "toiletries",
            "snacks",
            "drinks",
            "icecream",
            "sweets",
            "burger",
            "setmeal",
            "seafood",
            "meat",
            "petfood",
            "accessories"
        };

        for (String categoryName : categoryNames) {
            inventory.addCategories(new Category(categoryName));
        }

        storage.load(inventory, ui);
    }

    public static void main(String[] args) throws DukeException {
        LoggerConfig logger = new LoggerConfig("./logs/logger.txt");
        logger.setup();
        new Duke().run();
    }

    public void run() {
        ui.showWelcome();

        String input;
        while ((input = ui.readCommand()) != null) {
            try {
                Command command = parser.parse(input);

                if (command == null) {
                    continue;
                }

                if (command.isExit()) {
                    break;
                }

                command.execute(inventory, ui);
                storage.save(inventory);
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.showGoodbye();
        ui.close();
    }
}
