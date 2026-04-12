package seedu.inventorydock.exception;

/**
 * Signals that a requested category does not exist in the inventory.
 */
public class CategoryNotFoundException extends InventoryDockException {
    @Override
    public String getErrorCategory() {
        return "Not found";
    }

    /**
     * Creates an exception with the specified message.
     *
     * @param message Message describing the error.
     */
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
