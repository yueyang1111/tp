package seedu.inventorydock.exception;

/**
 * Signals that an index argument is invalid or out of range.
 */
public class InvalidIndexException extends InventoryDockException {
    @Override
    public String getErrorCategory() {
        return "Invalid input";
    }

    /**
     * Creates an exception with the specified message.
     *
     * @param message Message describing the error.
     */
    public InvalidIndexException(String message) {
        super(message);
    }

    /**
     * Creates an exception with the specified message and cause.
     *
     * @param message Message describing the error.
     * @param cause Underlying cause of the exception.
     */
    public InvalidIndexException(String message, Throwable cause) {
        super(message, cause);
    }
}
