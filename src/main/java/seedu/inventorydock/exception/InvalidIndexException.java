package seedu.inventorydock.exception;

/**
 * Signals that an index argument is invalid or out of range.
 */
public class InvalidIndexException extends InventoryDockException {
    public InvalidIndexException(String message) {
        super(message);
    }

    public InvalidIndexException(String message, Throwable cause) {
        super(message, cause);
    }
}
