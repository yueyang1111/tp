package seedu.inventorydock.exception;

/**
 * Signals that a command or command token is invalid.
 */
public class InvalidCommandException extends InventoryDockException {
    public InvalidCommandException(String message) {
        super(message);
    }
}
