package seedu.inventorydock.exception;

/**
 * Represents a custom exception used for our inventory.
 */
public class InventoryDockException extends Exception {
    /**
     * Constructs a InventoryDockException with the specified message.
     *
     * @param message The message describing the error.
     */
    public InventoryDockException(String message) {
        super(message);
    }

    /**
     * Constructs a InventoryDockException with the specified message and cause.
     *
     * @param message The message describing the error.
     * @param cause The underlying cause of the exception.
     */
    public InventoryDockException(String message, Throwable cause) {
        super(message, cause);
    }
}
