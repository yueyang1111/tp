package seedu.inventorydock.command;

import seedu.inventorydock.exception.CategoryNotFoundException;
import seedu.inventorydock.exception.DuplicateItemException;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.parser.DateParser;
import seedu.inventorydock.parser.DuplicateIdentityParser;
import seedu.inventorydock.ui.UI;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adds a parsed item into the requested category and reports the result through the UI.
 */
public class AddItemCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddItemCommand.class.getName());

    private final String categoryName;
    private final Item item;

    public AddItemCommand(String categoryName, Item item) {
        assert categoryName != null : "AddItemCommand received null category name.";
        this.categoryName = categoryName;
        this.item = item;
    }

    @Override
    public void execute(Inventory inventory, UI ui) throws InventoryDockException {
        assert inventory != null : "AddItemCommand received null inventory.";
        Category category = inventory.findCategoryByName(categoryName);

        if (category == null) {
            logger.log(Level.WARNING, "Category not found while adding item: " + categoryName);
            throw new CategoryNotFoundException("Category not found: " + categoryName);
        }

        if (item == null) {
            logger.log(Level.WARNING, "Null item supplied to AddItemCommand.");
            throw new MissingArgumentException("Item cannot be null.");
        }

        Item duplicateItem = findDuplicateItem(category, item);
        if (duplicateItem != null) {
            logger.log(Level.WARNING, "Duplicate item detected for category '" + category.getName()
                    + "' and name '" + item.getName() + "'.");
            throw new DuplicateItemException("Duplicate item found for category/" + category.getName()
                    + " item/" + item.getName() + ".");
        }

        category.addItem(item);
        logger.log(Level.INFO, "Added item '" + item.getName()
                + "' to category '" + category.getName() + "'.");
        if (ui != null) {
            ui.showItemAdded(item.getName(), item.getQuantity(),
                    category.getName(), item.getBinLocation());

            LocalDate expiryDate = DateParser.parseDate(item.getExpiryDate());
            if (expiryDate.isBefore(LocalDate.now())) {
                ui.showMessage("Note: The expiry date " + item.getExpiryDate()
                        + " is already in the past. Please double check and update it if needed.");
            }
        }
    }

    /**
     * Finds an existing item in the category that has the same duplicate identity as the candidate item.
     * The duplicate identity ignores qty and bin, and compares the remaining stored fields.
     *
     * @param category Category to scan.
     * @param candidate Item being added.
     * @return Matching duplicate item, or {@code null} if no duplicate exists.
     */
    private Item findDuplicateItem(Category category, Item candidate) {
        assert category != null : "Category cannot be null while checking duplicates.";
        assert candidate != null : "Candidate item cannot be null while checking duplicates.";

        String candidateIdentity = DuplicateIdentityParser.buildBatchIdentityKey(category.getName(), candidate);
        for (Item existing : category.getItems()) {
            String existingIdentity = DuplicateIdentityParser.buildBatchIdentityKey(category.getName(), existing);
            if (existingIdentity.equals(candidateIdentity)) {
                return existing;
            }
        }
        return null;
    }

}

