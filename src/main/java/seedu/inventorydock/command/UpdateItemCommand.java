package seedu.inventorydock.command;

import seedu.inventorydock.exception.CategoryNotFoundException;
import seedu.inventorydock.exception.DuplicateItemException;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.ItemNotFoundException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.model.items.Accessories;
import seedu.inventorydock.model.items.Drinks;
import seedu.inventorydock.model.items.Fruit;
import seedu.inventorydock.model.items.Meat;
import seedu.inventorydock.model.items.Snack;
import seedu.inventorydock.model.items.Toiletries;
import seedu.inventorydock.model.items.Vegetable;
import seedu.inventorydock.parser.DuplicateIdentityParser;
import seedu.inventorydock.parser.category.CommonFieldParser;
import seedu.inventorydock.ui.UI;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Updates an existing item in a category using fields provided by the user.
 * An <code>UpdateItemCommand</code> object locates the target item and applies
 * supported updates such as name, bin location, quantity, and expiry date.
 */
public class UpdateItemCommand extends Command {
    private static final Logger logger = Logger.getLogger(UpdateItemCommand.class.getName());

    private final String categoryName;
    private final int itemIndex;
    private final Map<String, String> updates;

    /**
     * Creates an update command for the specified category, item index, and fields.
     *
     * @param categoryName Name of the category containing the item to update.
     * @param itemIndex One-based index of the item within the category.
     * @param updates Fields and values to apply to the item.
     */
    public UpdateItemCommand(String categoryName, int itemIndex,
                             Map<String, String> updates) {
        assert categoryName != null : "UpdateItemCommand received null category name.";
        assert updates != null : "UpdateItemCommand received null updates map.";
        this.categoryName = categoryName;
        this.itemIndex = itemIndex;
        this.updates = updates;
    }

    /**
     * Executes the update command on the specified inventory.
     * Finds the target category and item, applies the requested updates, and
     * reports the result through the user interface.
     *
     * @param inventory Inventory containing the item to update.
     * @param ui User interface used to display update results.
     * @throws InventoryDockException If the category, item index, or update values are invalid.
     */
    @Override
    public void execute(Inventory inventory, UI ui) throws InventoryDockException {
        assert inventory != null : "UpdateItemCommand received null inventory.";
        assert ui != null : "UpdateItemCommand received null UI.";

        Category category = inventory.findCategoryByName(categoryName);
        if (category == null) {
            logger.log(Level.WARNING, "Category not found while updating item: " + categoryName);
            throw new CategoryNotFoundException("Category not found: " + categoryName);
        }

        if (itemIndex < 1 || itemIndex > category.getItemCount()) {
            logger.log(Level.WARNING, "Invalid item index while updating item: " + itemIndex);
            throw new ItemNotFoundException(
                "Item at index " + itemIndex +
                " not found in category '" + categoryName + "'.");
        }

        Item item = category.getItem(itemIndex - 1);
        String originalName = item.getName();
        ItemSnapshot snapshot = ItemSnapshot.from(item);

        try {
            applyUpdates(item);
        } catch (InventoryDockException e) {
            restoreOriginalValues(item, snapshot);
            throw e;
        }

        Item duplicateItem = findDuplicateItem(category, item);
        if (duplicateItem != null && duplicateItem != item) {
            String attemptedName = item.getName();
            restoreOriginalValues(item, snapshot);
            logger.log(Level.WARNING, "Duplicate item detected while updating category '"
                    + category.getName() + "' and name '" + attemptedName + "'.");
            throw new DuplicateItemException("Duplicate item found for category/" + category.getName()
                    + " item/" + attemptedName + ".");
        }

        logger.log(Level.INFO, "Updated item '" + originalName
                + "' in category '" + category.getName()
                + "' to '" + item.getName() + "'.");
        ui.showItemUpdated(originalName, item.getName(), category.getName());
    }

    /**
     * Applies all requested field updates to the specified item.
     * Only supported update prefixes are accepted.
     *
     * @param item Item to be updated.
     * @throws InventoryDockException If an update field is unsupported or contains an invalid value.
     */
    private void applyUpdates(Item item) throws InventoryDockException {
        for (Map.Entry<String, String> entry : updates.entrySet()) {
            String field = entry.getKey();
            String value = entry.getValue();

            switch (field) {
            case "newItem":
                validateNonEmpty(value, "New item name cannot be empty.");
                item.setName(value.trim());
                break;
            case "bin":
                validateNonEmpty(value, "Bin location cannot be empty.");
                item.setBinLocation(value.trim());
                break;
            case "qty":
                item.setQuantity(CommonFieldParser.parseQuantity(value));
                break;
            case "expiryDate":
                CommonFieldParser.validateExpiryDate(value);
                item.setExpiryDate(value.trim());
                break;
            case "isRipe":
                updateFruitField(item, value);
                break;
            case "isLeafy":
                updateVegetableField(item, value);
                break;
            case "isLiquid":
                updateToiletriesField(item, value);
                break;
            case "isCrunchy":
                updateSnackField(item, value);
                break;
            case "isCarbonated":
                updateDrinkField(item, value);
                break;
            case "isFrozen":
                updateMeatField(item, value);
                break;
            case "isFragile":
                updateAccessoriesField(item, value);
                break;
            default:
                throw new InvalidCommandException("Unsupported update field: " + field + "/.");
            }
        }
    }

    /**
     * Validates that the specified value is not empty.
     * Throws an exception if the value is null or blank.
     *
     * @param value Value to validate.
     * @param message Error message to use when validation fails.
     * @throws InventoryDockException If the value is null or blank.
     */
    private void validateNonEmpty(String value,
                                  String message) throws InventoryDockException {
        if (value == null || value.trim().isEmpty()) {
            throw new MissingArgumentException(message);
        }
    }

    /**
     * Restores original item values when update validation fails after mutation.
     *
     * @param item Item to restore.
     * @param name Original name.
     * @param bin Original bin.
     * @param quantity Original quantity.
     * @param expiryDate Original expiry date.
     */
    private void restoreOriginalValues(Item item, ItemSnapshot snapshot) {
        item.setName(snapshot.name);
        item.setBinLocation(snapshot.bin);
        item.setQuantity(snapshot.quantity);
        item.setExpiryDate(snapshot.expiryDate);

        if (snapshot.categorySpecificField == null) {
            return;
        }

        applyCategorySpecificUpdate(item, snapshot.categorySpecificField, snapshot.categorySpecificValue);
    }

    /**
     * Finds an existing item in the category that has the same duplicate identity as the candidate item.
     * The duplicate identity ignores qty and bin, and compares the remaining stored fields.
     *
     * @param category Category to scan.
     * @param candidate Item being updated.
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

    private void updateFruitField(Item item, String value) throws InventoryDockException {
        if (!(item instanceof Fruit fruit)) {
            throw new InvalidCommandException("isRipe/ can only be updated for fruits.");
        }
        fruit.setRipe(parseBooleanValue(value, "isRipe/"));
    }

    private void updateVegetableField(Item item, String value) throws InventoryDockException {
        if (!(item instanceof Vegetable vegetable)) {
            throw new InvalidCommandException("isLeafy/ can only be updated for vegetables.");
        }
        vegetable.setLeafy(parseBooleanValue(value, "isLeafy/"));
    }

    private void updateToiletriesField(Item item, String value) throws InventoryDockException {
        if (!(item instanceof Toiletries toiletries)) {
            throw new InvalidCommandException("isLiquid/ can only be updated for toiletries.");
        }
        toiletries.setLiquid(parseBooleanValue(value, "isLiquid/"));
    }

    private void updateSnackField(Item item, String value) throws InventoryDockException {
        if (!(item instanceof Snack snack)) {
            throw new InvalidCommandException("isCrunchy/ can only be updated for snacks.");
        }
        snack.setCrunchy(parseBooleanValue(value, "isCrunchy/"));
    }

    private void updateDrinkField(Item item, String value) throws InventoryDockException {
        if (!(item instanceof Drinks drinks)) {
            throw new InvalidCommandException("isCarbonated/ can only be updated for drinks.");
        }
        drinks.setCarbonated(parseBooleanValue(value, "isCarbonated/"));
    }

    private void updateMeatField(Item item, String value) throws InventoryDockException {
        if (!(item instanceof Meat meat)) {
            throw new InvalidCommandException("isFrozen/ can only be updated for meat.");
        }
        meat.setFrozen(parseBooleanValue(value, "isFrozen/"));
    }

    private void updateAccessoriesField(Item item, String value) throws InventoryDockException {
        if (!(item instanceof Accessories accessories)) {
            throw new InvalidCommandException("isFragile/ can only be updated for accessories.");
        }
        accessories.setFragile(parseBooleanValue(value, "isFragile/"));
    }

    private void applyCategorySpecificUpdate(Item item, String field, String value) {
        switch (field) {
        case "isRipe":
            ((Fruit) item).setRipe(Boolean.parseBoolean(value));
            break;
        case "isLeafy":
            ((Vegetable) item).setLeafy(Boolean.parseBoolean(value));
            break;
        case "isLiquid":
            ((Toiletries) item).setLiquid(Boolean.parseBoolean(value));
            break;
        case "isCrunchy":
            ((Snack) item).setCrunchy(Boolean.parseBoolean(value));
            break;
        case "isCarbonated":
            ((Drinks) item).setCarbonated(Boolean.parseBoolean(value));
            break;
        case "isFrozen":
            ((Meat) item).setFrozen(Boolean.parseBoolean(value));
            break;
        case "isFragile":
            ((Accessories) item).setFragile(Boolean.parseBoolean(value));
            break;
        default:
            throw new IllegalStateException("Unknown category-specific field: " + field);
        }
    }

    private boolean parseBooleanValue(String value, String fieldName) throws InventoryDockException {
        validateNonEmpty(value, "Missing value for " + fieldName);
        String trimmedValue = value.trim();
        if (!trimmedValue.equalsIgnoreCase("true") && !trimmedValue.equalsIgnoreCase("false")) {
            throw new InvalidCommandException(fieldName + " must be true or false.");
        }
        return Boolean.parseBoolean(trimmedValue);
    }

    private static class ItemSnapshot {
        private final String name;
        private final String bin;
        private final int quantity;
        private final String expiryDate;
        private final String categorySpecificField;
        private final String categorySpecificValue;

        private ItemSnapshot(String name, String bin, int quantity, String expiryDate,
                             String categorySpecificField, String categorySpecificValue) {
            this.name = name;
            this.bin = bin;
            this.quantity = quantity;
            this.expiryDate = expiryDate;
            this.categorySpecificField = categorySpecificField;
            this.categorySpecificValue = categorySpecificValue;
        }

        private static ItemSnapshot from(Item item) {
            if (item instanceof Fruit fruit) {
                return new ItemSnapshot(item.getName(), item.getBinLocation(), item.getQuantity(), item.getExpiryDate(),
                        "isRipe", String.valueOf(fruit.isRipe()));
            }
            if (item instanceof Vegetable vegetable) {
                return new ItemSnapshot(item.getName(), item.getBinLocation(), item.getQuantity(), item.getExpiryDate(),
                        "isLeafy", String.valueOf(vegetable.isLeafy()));
            }
            if (item instanceof Toiletries toiletries) {
                return new ItemSnapshot(item.getName(), item.getBinLocation(), item.getQuantity(), item.getExpiryDate(),
                        "isLiquid", String.valueOf(toiletries.isLiquid()));
            }
            if (item instanceof Snack snack) {
                return new ItemSnapshot(item.getName(), item.getBinLocation(), item.getQuantity(), item.getExpiryDate(),
                        "isCrunchy", String.valueOf(snack.isCrunchy()));
            }
            if (item instanceof Drinks drinks) {
                return new ItemSnapshot(item.getName(), item.getBinLocation(), item.getQuantity(), item.getExpiryDate(),
                        "isCarbonated", String.valueOf(drinks.isCarbonated()));
            }
            if (item instanceof Meat meat) {
                return new ItemSnapshot(item.getName(), item.getBinLocation(), item.getQuantity(), item.getExpiryDate(),
                        "isFrozen", String.valueOf(meat.isFrozen()));
            }
            if (item instanceof Accessories accessories) {
                return new ItemSnapshot(item.getName(), item.getBinLocation(), item.getQuantity(), item.getExpiryDate(),
                        "isFragile", String.valueOf(accessories.isFragile()));
            }
            return new ItemSnapshot(item.getName(), item.getBinLocation(), item.getQuantity(), item.getExpiryDate(),
                    null, null);
        }
    }
}

