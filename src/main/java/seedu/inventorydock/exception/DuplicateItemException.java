package seedu.inventorydock.exception;

/**
 * Signals that an item operation would create a duplicate batch in a category.
 */
public class DuplicateItemException extends InventoryDockException {
    @Override
    public String getErrorCategory() {
        return "Conflict";
    }

    /**
     * Creates an exception with the specified message.
     *
     * @param message Message describing the error.
     */
    public DuplicateItemException(String message) {
        super(message);
    }
}
