package seedu.inventorydock.exception;

/**
 * Signals that a date argument is invalid.
 */
public class InvalidDateException extends InventoryDockException {
    public InvalidDateException(String message) {
        super(message);
    }
}
