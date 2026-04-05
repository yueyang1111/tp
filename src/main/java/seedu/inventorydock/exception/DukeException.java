package seedu.inventorydock.exception;

/**
 * Represents a custom exception used for our inventory.
 */
public class DukeException extends Exception {
    /**
     * Constructs a DukeException with the specified message.
     *
     * @param message The message describing the error.
     */
    public DukeException(String message) {
        super(message);
    }
}
