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
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Updates an existing item in a category using fields provided by the user.
 */
public class UpdateItemCommand extends Command {
    private static final Logger logger = Logger.getLogger(UpdateItemCommand.class.getName());
    private static final Map<Class<?>, CategoryFieldHandler> CATEGORY_HANDLERS = Map.of(
            Fruit.class, new CategoryFieldHandler("isRipe", item -> ((Fruit) item).isRipe(),
                    (item, value) -> ((Fruit) item).setRipe(value), "fruits"),
            Vegetable.class, new CategoryFieldHandler("isLeafy", item -> ((Vegetable) item).isLeafy(),
                    (item, value) -> ((Vegetable) item).setLeafy(value), "vegetables"),
            Toiletries.class, new CategoryFieldHandler("isLiquid", item -> ((Toiletries) item).isLiquid(),
                    (item, value) -> ((Toiletries) item).setLiquid(value), "toiletries"),
            Snack.class, new CategoryFieldHandler("isCrunchy", item -> ((Snack) item).isCrunchy(),
                    (item, value) -> ((Snack) item).setCrunchy(value), "snacks"),
            Drinks.class, new CategoryFieldHandler("isCarbonated", item -> ((Drinks) item).isCarbonated(),
                    (item, value) -> ((Drinks) item).setCarbonated(value), "drinks"),
            Meat.class, new CategoryFieldHandler("isFrozen", item -> ((Meat) item).isFrozen(),
                    (item, value) -> ((Meat) item).setFrozen(value), "meat"),
            Accessories.class, new CategoryFieldHandler("isFragile", item -> ((Accessories) item).isFragile(),
                    (item, value) -> ((Accessories) item).setFragile(value), "accessories")
    );

    private final String categoryName;
    private final int itemIndex;
    private final Map<String, String> updates;

    public UpdateItemCommand(String categoryName, int itemIndex, Map<String, String> updates) {
        assert categoryName != null : "UpdateItemCommand received null category name.";
        assert updates != null : "UpdateItemCommand received null updates map.";
        this.categoryName = categoryName;
        this.itemIndex = itemIndex;
        this.updates = updates;
    }

    @Override
    public void execute(Inventory inventory, UI ui) throws InventoryDockException {
        assert inventory != null : "UpdateItemCommand received null inventory.";
        assert ui != null : "UpdateItemCommand received null UI.";

        Category category = inventory.findCategoryByName(categoryName);
        if (category == null) {
            logger.log(Level.WARNING, "Category not found while updating item: " + categoryName);
            throw new CategoryNotFoundException("Category '" + categoryName + "' does not exist.");
        }

        if (itemIndex < 1 || itemIndex > category.getItemCount()) {
            logger.log(Level.WARNING, "Invalid item index while updating item: " + itemIndex);
            throw new ItemNotFoundException(
                    "Item at index " + itemIndex + " not found in category '" + categoryName + "'.");
        }

        Item item = category.getItem(itemIndex - 1);
        String originalName = item.getName();
        ItemSnapshot snapshot = ItemSnapshot.from(item);
        validateNameUpdateDoesNotDuplicate(category, item);

        try {
            applyUpdates(item);
        } catch (InventoryDockException e) {
            restoreFromSnapshot(item, snapshot);
            throw e;
        }

        Item duplicate = DuplicateIdentityParser.findDuplicateItem(category, item);
        if (duplicate != null && duplicate != item) {
            String attemptedName = item.getName();
            restoreFromSnapshot(item, snapshot);
            logger.log(Level.WARNING, "Duplicate item detected while updating category '"
                    + category.getName() + "' and name '" + attemptedName + "'.");
            throw new DuplicateItemException("Duplicate item found for category/" + category.getName()
                    + " item/" + attemptedName + ".");
        }

        logger.log(Level.INFO, "Updated item '" + originalName
                + "' in category '" + category.getName() + "' to '" + item.getName() + "'.");
        ui.showItemUpdated(originalName, item.getName(), category.getName());
    }

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
            default:
                updateCategorySpecificField(item, field, value);
            }
        }
    }

    private void validateNameUpdateDoesNotDuplicate(Category category, Item item) throws InventoryDockException {
        if (!updates.containsKey("newItem")) {
            return;
        }

        String updatedName = updates.get("newItem");
        validateNonEmpty(updatedName, "New item name cannot be empty.");
        Item duplicateNameItem = findDuplicateNameItem(category, item, updatedName.trim());
        if (duplicateNameItem != null) {
            logger.log(Level.WARNING, "Duplicate item name detected while updating category '"
                    + category.getName() + "' to '" + updatedName.trim() + "'.");
            throw new DuplicateItemException("Duplicate item found for category/" + category.getName()
                    + " item/" + updatedName.trim() + ".");
        }
    }

    private void updateCategorySpecificField(Item item, String field,
                                             String value) throws InventoryDockException {
        CategoryFieldHandler fieldHandler = findHandlerByField(field);
        if (fieldHandler == null) {
            throw new InvalidCommandException("Unsupported update field: " + field + "/.");
        }

        CategoryFieldHandler itemHandler = findCategoryHandler(item);
        if (itemHandler == null || itemHandler != fieldHandler) {
            throw new InvalidCommandException(field + "/ can only be updated for " + fieldHandler.itemType() + ".");
        }
        fieldHandler.setter().accept(item, parseBooleanValue(value, field + "/"));
    }

    private void restoreFromSnapshot(Item item, ItemSnapshot snapshot) {
        item.setName(snapshot.name);
        item.setBinLocation(snapshot.bin);
        item.setQuantity(snapshot.quantity);
        item.setExpiryDate(snapshot.expiryDate);

        if (snapshot.handler != null) {
            snapshot.handler.setter().accept(item, snapshot.categorySpecificValue);
        }
    }

    private Item findDuplicateNameItem(Category category, Item currentItem, String candidateName) {
        assert category != null : "Category cannot be null while checking duplicate names.";
        assert currentItem != null : "Current item cannot be null while checking duplicate names.";
        assert candidateName != null : "Candidate name cannot be null while checking duplicate names.";

        return category.getItems().stream()
                .filter(existing -> existing != currentItem)
                .filter(existing -> existing.getName().equalsIgnoreCase(candidateName))
                .findFirst()
                .orElse(null);
    }

    private static CategoryFieldHandler findCategoryHandler(Item item) {
        for (Map.Entry<Class<?>, CategoryFieldHandler> entry : CATEGORY_HANDLERS.entrySet()) {
            if (entry.getKey().isInstance(item)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private static CategoryFieldHandler findHandlerByField(String field) {
        for (CategoryFieldHandler handler : CATEGORY_HANDLERS.values()) {
            if (handler.fieldName().equals(field)) {
                return handler;
            }
        }
        return null;
    }

    private void validateNonEmpty(String value, String message) throws InventoryDockException {
        if (value == null || value.trim().isEmpty()) {
            throw new MissingArgumentException(message);
        }
    }

    private boolean parseBooleanValue(String value, String fieldName) throws InventoryDockException {
        validateNonEmpty(value, "Missing value for " + fieldName);
        String trimmed = value.trim();
        if (!trimmed.equalsIgnoreCase("true") && !trimmed.equalsIgnoreCase("false")) {
            throw new InvalidCommandException(fieldName + " must be true or false.");
        }
        return Boolean.parseBoolean(trimmed);
    }

    private static class ItemSnapshot {
        private final String name;
        private final String bin;
        private final int quantity;
        private final String expiryDate;
        private final CategoryFieldHandler handler;
        private final Boolean categorySpecificValue;

        private ItemSnapshot(String name, String bin, int quantity, String expiryDate,
                             CategoryFieldHandler handler, Boolean categorySpecificValue) {
            this.name = name;
            this.bin = bin;
            this.quantity = quantity;
            this.expiryDate = expiryDate;
            this.handler = handler;
            this.categorySpecificValue = categorySpecificValue;
        }

        private static ItemSnapshot from(Item item) {
            CategoryFieldHandler handler = findCategoryHandler(item);
            if (handler != null) {
                return new ItemSnapshot(item.getName(), item.getBinLocation(),
                        item.getQuantity(), item.getExpiryDate(),
                        handler, handler.getter().apply(item));
            }
            return new ItemSnapshot(item.getName(), item.getBinLocation(),
                    item.getQuantity(), item.getExpiryDate(), null, null);
        }
    }

    private record CategoryFieldHandler(String fieldName, Function<Item, Boolean> getter,
                                        BiConsumer<Item, Boolean> setter, String itemType) {
    }
}
