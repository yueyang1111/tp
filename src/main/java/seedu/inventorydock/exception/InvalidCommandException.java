package seedu.inventorydock.exception;

/**
 * Signals that a command or command token is invalid.
 */
public class InvalidCommandException extends InventoryDockException {
    @Override
    public String getErrorCategory() {
        return "Invalid input";
    }

    /**
     * Creates an exception with the specified message.
     *
     * @param message Message describing the error.
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}
