package seedu.inventorydock.exception;

/**
 * Signals that a required argument is missing from the command.
 */
public class MissingArgumentException extends InventoryDockException {
    @Override
    public String getErrorCategory() {
        return "Missing input";
    }

    /**
     * Creates an exception with the specified message.
     *
     * @param message Message describing the error.
     */
    public MissingArgumentException(String message) {
        super(message);
    }

    /**
     * Creates an exception with the specified message and cause.
     *
     * @param message Message describing the error.
     * @param cause Underlying cause of the exception.
     */
    public MissingArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
