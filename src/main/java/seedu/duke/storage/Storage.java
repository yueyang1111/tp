package seedu.duke.storage;

import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.model.items.Fruit;
import seedu.duke.model.items.Snack;
import seedu.duke.model.items.Toiletries;
import seedu.duke.model.items.Vegetable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final File dataFile;

    public Storage(String filePath) throws IOException {
        this.dataFile = new File(filePath);
        createFile();
    }

    public void createFile() throws IOException {
        if (dataFile.exists()) {
            return;
        }
        File parent = dataFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        dataFile.createNewFile();
    }

    public void load(Inventory inventory) throws DukeException {
        try {
            List<String> lines = Files.readAllLines(dataFile.toPath());

            for (String line : lines) {
                try {
                    parseLine(line, inventory);
                } catch (DukeException | NumberFormatException e) {
                    continue;
                }
            }
        } catch (IOException e) {
            throw new DukeException("Unable to read storage file.");
        }
    }

    private void parseLine(String line, Inventory inventory) throws DukeException {
        String[] parts = line.split("\\|");

        StorageCommonFields common = StorageCommonFields.parse(parts);

        Category category = inventory.findCategoryByName(common.categoryName);
        if (category == null) {
            throw new DukeException("Category not found.");
        }

        Item item;

        switch (common.categoryName) {
        case "fruits":
            if (parts.length != 7) {
                throw new DukeException("Invalid fruit line.");
            }
            item = new Fruit(common.itemName, common.quantity, common.bin,
                    parts[4].trim(), parts[5].trim(),
                    Boolean.parseBoolean(parts[6].trim()));
            break;
        case "snacks":
            if (parts.length != 6) {
                throw new DukeException("Invalid snack line.");
            }
            item = new Snack(common.itemName, common.quantity, common.bin,
                    parts[4].trim(), parts[5].trim());
            break;
        case "toiletries":
            if (parts.length != 7) {
                throw new DukeException("Invalid toiletries line.");
            }
            item = new Toiletries(common.itemName, common.quantity, common.bin,
                    parts[4].trim(), Boolean.parseBoolean(parts[5].trim()),
                    parts[6].trim());
            break;
        case "vegetables":
            if (parts.length != 6) {
                throw new DukeException("Invalid vegetable line.");
            }
            item = new Vegetable(common.itemName, common.quantity, common.bin,
                    parts[4].trim(), Boolean.parseBoolean(parts[5].trim()));
            break;
        default:
            throw new DukeException("Unknown category.");
        }

        category.addItem(item);
    }

    public void save(Inventory inventory) throws DukeException {
        List<String> lines = new ArrayList<>();

        for (Category category : inventory.getCategories()) {
            for (Item item : category.getItems()) {
                lines.add(formatItem(item, category.getName()));
            }
        }

        try {
            Files.write(dataFile.toPath(), lines);
        } catch (IOException e) {
            throw new DukeException("Unable to save inventory.");
        }
    }

    private String formatItem(Item item, String categoryName) {
        String base = item.getName() + " | "
                + categoryName + " | "
                + item.getBinLocation() + " | "
                + item.getQuantity();

        if (item instanceof Fruit) {
            Fruit fruit = (Fruit) item;
            return base + " | "
                    + item.getExpiryDate() + " | "
                    + fruit.getSize() + " | "
                    + fruit.isRipe();
        }

        if (item instanceof Snack) {
            Snack snack = (Snack) item;
            return base + " | "
                    + snack.getBrand() + " | "
                    + item.getExpiryDate();
        }

        if (item instanceof Toiletries) {
            Toiletries toiletries = (Toiletries) item;
            return base + " | "
                    + toiletries.getBrand() + " | "
                    + toiletries.isLiquid() + " | "
                    + item.getExpiryDate();
        }

        if (item instanceof Vegetable) {
            Vegetable vegetable = (Vegetable) item;
            return base + " | "
                    + item.getExpiryDate() + " | "
                    + vegetable.isLeafy();
        }

        return base;
    }
}
