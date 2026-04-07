package seedu.inventorydock.exception;

/**
 * Signals that storage or file operations failed.
 */
public class StorageException extends InventoryDockException {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
