package seedu.inventorydock.exception;

/**
 * Signals that a required argument is missing from the command.
 */
public class MissingArgumentException extends InventoryDockException {
    public MissingArgumentException(String message) {
        super(message);
    }

    public MissingArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
