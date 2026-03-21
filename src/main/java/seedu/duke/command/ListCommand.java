package seedu.duke.command;

import seedu.duke.model.Inventory;
import seedu.duke.ui.UI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListCommand extends Command {
    private static final Logger logger = Logger.getLogger(ListCommand.class.getName());

    @Override
    public void execute(Inventory inventory, UI ui) {
        assert inventory != null : "ListCommand received null inventory.";
        assert ui != null : "ListCommand received null UI.";
        logger.log(Level.INFO, "Listing inventory.");
        ui.showInventory(inventory);
    }
}
