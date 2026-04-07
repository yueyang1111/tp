package seedu.inventorydock.command;

import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.MissingArgumentException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.ui.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Finds and displays items whose quantity is at or below a target threshold.
 */
public class FindItemByQtyCommand extends Command {
    private static final Logger logger = Logger.getLogger(FindItemByQtyCommand.class.getName());
    private final int qtyInput;

    /**
     * Creates a command that searches inventory items whose quantity is at or below a threshold.
     *
     * @param qtyInput validated quantity query supplied by the parser.
     */
    public FindItemByQtyCommand(int qtyInput) {
        this.qtyInput = qtyInput;
    }

    /**
     * Parses and validates a quantity threshold supplied to the qty find command.
     *
     * @param qtyInput raw quantity input.
     * @return parsed positive quantity.
     * @throws MissingArgumentException if the quantity input is empty.
     * @throws InvalidCommandException if the quantity is not an integer or is not positive.
     */
    public static int parseQtyInput(String qtyInput) throws MissingArgumentException, InvalidCommandException {
        if (qtyInput == null || qtyInput.trim().isEmpty()) {
            logger.log(Level.WARNING, "Empty quantity input received.");
            throw new MissingArgumentException("Quantity is missing.");
        }

        int parsedQty;
        try {
            parsedQty = Integer.parseInt(qtyInput.trim());
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid quantity format: " + qtyInput);
            throw new InvalidCommandException("Quantity must be an integer.");
        }

        if (parsedQty <= 0) {
            logger.log(Level.WARNING, "Non-positive quantity input: " + parsedQty);
            throw new InvalidCommandException("Quantity must be a positive integer.");
        }
        assert parsedQty >= 0 : "FindItemByQtyCommand parsed negative quantity input.";

        return parsedQty;
    }

    /**
     * Scans every item in the inventory and displays those whose quantity is at or below the query.
     *
     * @param inventory inventory to search.
     * @param ui user interface used to display search results.
     */
    @Override
    public void execute(Inventory inventory, UI ui) {
        assert inventory != null : "FindItemByQtyCommand received null inventory.";
        assert ui != null : "FindItemByQtyCommand received null UI.";

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
