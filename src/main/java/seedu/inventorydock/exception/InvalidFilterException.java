package seedu.inventorydock.exception;

/**
 * Signals that a filter or search mode is invalid.
 */
public class InvalidFilterException extends InventoryDockException {
    public InvalidFilterException(String message) {
        super(message);
    }
}
