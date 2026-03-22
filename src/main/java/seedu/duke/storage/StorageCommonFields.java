package seedu.duke.storage;

import seedu.duke.exception.DukeException;

public class StorageCommonFields {
    public final String itemName;
    public final String categoryName;
    public final String bin;
    public final int quantity;

    private StorageCommonFields(String itemName, String categoryName,
                                String bin, int quantity) {
        this.itemName = itemName;
        this.categoryName = categoryName;
        this.bin = bin;
        this.quantity = quantity;
    }

    public static StorageCommonFields parse(String[] parts) throws DukeException {
        if (parts.length < 4) {
            throw new DukeException("Corrupted line.");
        }

        String itemName = parts[0].trim();
        String categoryName = parts[1].trim().toLowerCase();
        String bin = parts[2].trim();

        int quantity;
        try {
            quantity = Integer.parseInt(parts[3].trim());
        } catch (NumberFormatException e) {
            throw new DukeException("Invalid quantity.");
        }

        return new StorageCommonFields(itemName, categoryName, bin, quantity);
    }
}
