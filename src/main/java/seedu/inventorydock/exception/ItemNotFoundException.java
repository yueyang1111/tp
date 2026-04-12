package seedu.inventorydock.exception;

/**
 * Signals that a requested item could not be found in the inventory.
 */
public class ItemNotFoundException extends InventoryDockException {
    @Override
    public String getErrorCategory() {
        return "Not found";
    }

    /**
     * Creates an exception with the specified message.
     *
     * @param message Message describing the error.
     */
    public ItemNotFoundException(String message) {
        super(message);
    }
}
