package seedu.inventorydock.command;

import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.ui.UI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Displays the full inventory grouped by category.
 */
public class ListCommand extends Command {
    private static final Logger logger = Logger.getLogger(ListCommand.class.getName());

    /**
     * Sends the current inventory to the UI for rendering.
     *
     * @param inventory inventory to display.
     * @param ui user interface used to render the inventory listing.
     */
    @Override
    public void execute(Inventory inventory, UI ui) {
        assert inventory != null : "ListCommand received null inventory.";
        assert ui != null : "ListCommand received null UI.";
        logger.log(Level.INFO, "Listing inventory.");
        ui.showInventory(inventory);
    }
}
