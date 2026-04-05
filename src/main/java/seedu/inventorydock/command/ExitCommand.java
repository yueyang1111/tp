package seedu.inventorydock.command;

import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.ui.UI;

public class ExitCommand extends Command {
    @Override
    public void execute(Inventory inventory, UI ui) {
        // Exit handled by isExit() in main loop
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
