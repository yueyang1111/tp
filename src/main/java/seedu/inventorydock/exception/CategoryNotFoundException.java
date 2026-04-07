package seedu.inventorydock.exception;

/**
 * Signals that a requested category does not exist in the inventory.
 */
public class CategoryNotFoundException extends InventoryDockException {

    /**
     * Constructs a CategoryNotFoundException with a custom message.
     *
     * @param message The error message.
     */
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
