package seedu.inventorydock.command;

import seedu.inventorydock.exception.DukeException;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.ui.UI;

public abstract class Command {

    public abstract void execute(Inventory inventory, UI ui) throws DukeException;

    public boolean isExit() {
        return false;
    }
}
