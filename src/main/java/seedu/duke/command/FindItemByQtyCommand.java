package seedu.duke.command;

import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.ui.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Finds and displays items whose quantity matches a target quantity.
 */
public class FindItemByQtyCommand extends Command {
    private static final Logger logger = Logger.getLogger(FindItemByQtyCommand.class.getName());
    private final int qtyInput;

    /**
     * Creates a command that searches inventory items by exact quantity.
     *
     * @param qtyInput validated quantity query supplied by the parser.
     */
    public FindItemByQtyCommand(int qtyInput) {
        this.qtyInput = qtyInput;
    }

    /**
     * Scans every item in the inventory and displays those whose quantity matches the query.
     *
     * @param inventory inventory to search.
     * @param ui user interface used to display search results.
     * @throws DukeException included for command interface compatibility.
     */
    @Override
    public void execute(Inventory inventory, UI ui) throws DukeException {
        assert inventory != null : "FindItemByQtyCommand received null inventory.";
        assert ui != null : "FindItemByQtyCommand received null UI.";
        assert qtyInput >= 0 : "FindItemByQtyCommand received non-positive quantity input.";

        List<Item> matches = new ArrayList<>();
        List<Category> categories = inventory.getCategories();

        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getQuantity() <= qtyInput) {
                    matches.add(item);
                }
            }
        }

        if (matches.isEmpty()) {
            String noItemsMessage = ui.formatNoItemsFoundMessage("with quantity: " + qtyInput);
            logger.log(Level.INFO, noItemsMessage);
            ui.showMessage(noItemsMessage);
            return;
        }

        logger.log(Level.INFO, ui.formatFoundItemsMessage(matches.size(), "with quantity '" + qtyInput + "'"));

        ui.showDivider();
        ui.showMessage(ui.formatFindResultsHeader("with quantity: " + qtyInput));
        ui.showNumberedList(matches);
        ui.showDivider();
    }
}
