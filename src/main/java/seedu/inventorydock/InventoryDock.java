package seedu.inventorydock;

import seedu.inventorydock.command.Command;
import seedu.inventorydock.exception.DukeException;
import seedu.inventorydock.logging.LoggerConfig;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.parser.Parser;
import seedu.inventorydock.storage.Storage;
import seedu.inventorydock.ui.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the main entry point and application loop for Duke.
 * A <code>Duke</code> object initializes the core components, loads stored data,
 * and processes user commands until exit.
 */
public class InventoryDock {
    private static final Logger logger = Logger.getLogger(InventoryDock.class.getName());
    private final Inventory inventory;
    private final UI ui;
    private final Parser parser;
    private final Storage storage;

    /**
     * Creates a Duke application instance with default categories and storage.
     *
     * @throws DukeException If stored inventory data cannot be loaded.
     */
    public InventoryDock() throws DukeException {
        ui = new UI();
        inventory = new Inventory();
        parser = new Parser();
        storage = new Storage("./data/inventory.txt");
        logger.log(Level.INFO, "Initializing Duke application.");

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
            inventory.addCategory(new Category(categoryName));
        }

        storage.load(inventory, ui);
        logger.log(Level.INFO, "Loaded inventory with " + inventory.getCategoryCount() + " categories.");
    }

    /**
     * Starts the Duke application.
     *
     * @param args Command-line arguments.
     * @throws DukeException If startup initialization fails.
     */
    public static void main(String[] args) throws DukeException {
        LoggerConfig logger = new LoggerConfig("./logs/logger.txt");
        logger.setup();
        new InventoryDock().run();
    }

    /**
     * Runs the main command loop until the user exits the application.
     * Successful commands are persisted after execution, and exit also triggers a save.
     */
    public void run() {
        logger.log(Level.INFO, "Starting Duke command loop.");
        ui.showWelcome();

        String input;
        while ((input = ui.readCommand()) != null) {
            try {
                Command command = parser.parse(input);

                if (command == null) {
                    continue;
                }

                if (command.isExit()) {
                    logger.log(Level.INFO, "Exit command received. Saving inventory and shutting down.");
                    storage.save(inventory);
                    break;
                }

                logger.log(Level.INFO, "Executing command: " + command.getClass().getSimpleName());
                command.execute(inventory, ui);
                storage.save(inventory);
            } catch (DukeException e) {
                logger.log(Level.WARNING, "Command processing failed: " + e.getMessage());
                ui.showError(e.getMessage());
            }
        }

        logger.log(Level.INFO, "Closing Duke application.");
        ui.showGoodbye();
        ui.close();
    }
}
