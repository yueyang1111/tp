package seedu.inventorydock.exception;

/**
 * Signals that a date argument is invalid.
 */
public class InvalidDateException extends InventoryDockException {
    @Override
    public String getErrorCategory() {
        return "Invalid input";
    }

    /**
     * Creates an exception with the specified message.
     *
     * @param message Message describing the error.
     */
    public InvalidDateException(String message) {
        super(message);
    }
}
