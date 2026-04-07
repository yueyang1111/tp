package seedu.inventorydock.command;

import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.ui.UI;

/**
 * Represent all executable command within the InventoryDock application.
 * All specific command types (e.g. AddCommand, DeleteCommand) must inherit from this class.
 */
public abstract class Command {
    /**
     * Executes the specific logic associated with the command.
     *
     * @param inventory The inventory data structure to be modified or accessed.
     * @param ui The user interface used to display feedback or errors to the user.
     * @throws InventoryDockException If an error occurs during the execution of the command.
     */
    public abstract void execute(Inventory inventory, UI ui) throws InventoryDockException;

    /**
     * Indicates whether this command should terminate the application.
     * By default, command do not exit the program.
     *
     * @return {@code true} if the command is an exit command, {@code false} otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
