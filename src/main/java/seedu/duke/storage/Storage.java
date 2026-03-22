package seedu.duke.storage;

import seedu.duke.command.Command;
import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.model.items.Fruit;
import seedu.duke.model.items.Snack;
import seedu.duke.model.items.Toiletries;
import seedu.duke.model.items.Vegetable;
import seedu.duke.parser.AddItemCommandParser;
import seedu.duke.ui.UI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {
    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    private final File dataFile;

    public Storage(String filePath) {
        assert filePath != null : "Storage received null file path.";
        this.dataFile = new File(filePath);
        logger.log(Level.INFO, "Storage initialized with file path: " + dataFile.getPath());
    }

    private void createFile() throws IOException {
        if (dataFile.exists()) {
            return;
        }
        File parent = dataFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        dataFile.createNewFile();
        logger.log(Level.INFO, "Created storage file: " + dataFile.getPath());
    }

    public void load(Inventory inventory, UI ui) throws DukeException {
        assert inventory != null : "Storage.load received null inventory.";
        assert ui != null : "Storage.load received null ui.";
        try {
            createFile();
            List<String> lines = Files.readAllLines(dataFile.toPath());
            AddItemCommandParser addItemCommandParser = new AddItemCommandParser();
            logger.log(Level.INFO, "Loading inventory from storage file: " + dataFile.getPath());

            for (String line : lines) {
                try {
                    Command command = parseStoredLine(line, addItemCommandParser);
                    command.execute(inventory, null);
                } catch (DukeException e) {
                    logger.log(Level.WARNING, "Skipped corrupted line: " + line
                            + " Reason: " + e.getMessage());
                    ui.showSkippedLine(line, e.getMessage());
                }
            }
            logger.log(Level.INFO, "Finished loading inventory.");
        } catch (IOException e) {
            throw new DukeException("Unable to read storage file.");
        }
    }

    private Command parseStoredLine(String line, AddItemCommandParser parser) throws DukeException {
        assert line != null : "Storage.parseStoredLine received null line.";
        assert parser != null : "Storage.parseStoredLine received null parser.";
        String category = extractCategory(line);

        switch (category) {
        case "fruits":
            return parser.handleFruit(line);
        case "snacks":
            return parser.handleSnack(line);
        case "toiletries":
            return parser.handleToiletries(line);
        case "vegetables":
            return parser.handleVegetables(line);
        default:
            throw new DukeException("Unknown category in storage.");
        }
    }

    private String extractCategory(String line) throws DukeException {
        assert line != null : "Storage.extractCategory received null line.";
        String[] tokens = line.trim().split(" ");

        for (String token : tokens) {
            if (token.startsWith("category/")) {
                return token.substring("category/".length()).trim().toLowerCase();
            }
        }

        throw new DukeException("Missing category in storage line.");
    }

    public void save(Inventory inventory) throws DukeException {
        assert inventory != null : "Storage.save received null inventory.";
        List<String> lines = new ArrayList<>();

        for (Category category : inventory.getCategories()) {
            for (Item item : category.getItems()) {
                lines.add(formatItem(item, category.getName()));
            }
        }

        try {
            createFile();
            Files.write(dataFile.toPath(), lines);
            logger.log(Level.INFO, "Saved inventory to storage file.");
        } catch (IOException e) {
            throw new DukeException("Unable to save inventory.");
        }
    }

    private String formatItem(Item item, String categoryName) {
        assert item != null : "Storage.formatItem received null item.";
        assert categoryName != null : "Storage.formatItem received null categoryName.";
        String base = "item/" + item.getName()
                + " category/" + categoryName
                + " bin/" + item.getBinLocation()
                + " qty/" + item.getQuantity();

        if (item instanceof Fruit) {
            Fruit fruit = (Fruit) item;
            return base + " expiryDate/" + item.getExpiryDate()
                    + " size/" + fruit.getSize()
                    + " isRipe/" + fruit.isRipe();
        }

        if (item instanceof Snack) {
            Snack snack = (Snack) item;
            return base + " brand/" + snack.getBrand()
                    + " expiryDate/" + item.getExpiryDate();
        }

        if (item instanceof Toiletries) {
            Toiletries toiletries = (Toiletries) item;
            return base + " brand/" + toiletries.getBrand()
                    + " isLiquid/" + toiletries.isLiquid()
                    + " expiryDate/" + item.getExpiryDate();
        }

        if (item instanceof Vegetable) {
            Vegetable vegetable = (Vegetable) item;
            return base + " expiryDate/" + item.getExpiryDate()
                    + " isLeafy/" + vegetable.isLeafy();
        }

        return base;
    }
}
