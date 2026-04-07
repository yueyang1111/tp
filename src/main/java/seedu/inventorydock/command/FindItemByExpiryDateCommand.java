package seedu.inventorydock.command;

import seedu.inventorydock.exception.InvalidDateException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.parser.DateParser;
import seedu.inventorydock.ui.UI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Finds items whose expiry dates are on or before a specified cutoff date.
 * A <code>FindItemByExpiryDateCommand</code> object scans all categories in the
 * inventory and lists the items that expire by the given date.
 */
public class FindItemByExpiryDateCommand extends Command {
    private static final Logger logger = Logger.getLogger(
            FindItemByExpiryDateCommand.class.getName());

    private final String expiryDateInput;

    /**
     * Creates a command that searches for items expiring by the specified date.
     *
     * @param expiryDateInput Cutoff expiry date supplied by the user.
     */
    public FindItemByExpiryDateCommand(String expiryDateInput) {
        this.expiryDateInput = expiryDateInput;
    }

    /**
     * Executes the expiry-date search on the specified inventory.
     * Parses the cutoff date, scans all items, and displays those whose expiry
     * dates are on or before the specified date.
     *
     * @param inventory Inventory containing the items to search.
     * @param ui User interface used to display search results.
     * @throws InvalidDateException If the supplied expiry date cannot be parsed.
     */
    @Override
    public void execute(Inventory inventory, UI ui) throws InvalidDateException {
        assert inventory != null : "FindItemByExpiryDateCommand received null inventory.";
        assert ui != null : "FindItemByExpiryDateCommand received null UI.";
        assert expiryDateInput != null : "FindItemByExpiryDateCommand received null expiry date.";
        LocalDate cutoffDate = DateParser.parseDate(expiryDateInput);
        List<String> matches = new ArrayList<>();

        List<Category> categories = inventory.getCategories();

        for (Category category : categories) {
            for (Item item : category.getItems()) {
                String itemExpiryDate = item.getExpiryDate();
                if (itemExpiryDate == null) {
                    continue;
                }

                LocalDate itemDate = DateParser.parseDate(itemExpiryDate);
                if (!itemDate.isAfter(cutoffDate)) {
                    matches.add(category.getName() + ": " + item);
                }
            }
        }

        if (matches.isEmpty()) {
            String noItemsMessage = ui.formatNoItemsFoundMessage("expiring by " + expiryDateInput);
            logger.log(Level.INFO, noItemsMessage);
            ui.showMessage(noItemsMessage);
            return;
        }

        logger.log(Level.INFO, ui.formatFoundItemsMessage(matches.size(), "expiring by " + expiryDateInput));

        ui.showDivider();
        ui.showMessage(ui.formatFindResultsHeader("expiring by " + expiryDateInput + ":"));
        ui.showNumberedList(matches);
        ui.showDivider();
    }
}
