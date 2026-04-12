package seedu.inventorydock.exception;

/**
 * Signals that storage or file operations failed.
 */
public class StorageException extends InventoryDockException {
    @Override
    public String getErrorCategory() {
        return "Storage error";
    }

    /**
     * Creates an exception with the specified message.
     *
     * @param message Message describing the error.
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Creates an exception with the specified message and cause.
     *
     * @param message Message describing the error.
     * @param cause Underlying cause of the exception.
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
