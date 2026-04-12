package seedu.inventorydock.exception;

/**
 * Signals that an inventory operation failed.
 */
public class InventoryDockException extends Exception {
    public String getErrorCategory() {
        return "Error";
    }

    /**
     * Creates an exception with the specified message.
     *
     * @param message Message describing the error.
     */
    public InventoryDockException(String message) {
        super(message);
    }

    /**
     * Creates an exception with the specified message and cause.
     *
     * @param message Message describing the error.
     * @param cause Underlying cause of the exception.
     */
    public InventoryDockException(String message, Throwable cause) {
        super(message, cause);
    }
}
