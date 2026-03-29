package seedu.duke.command;

import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.parser.DateParser;
import seedu.duke.ui.UI;

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
     * Constructs a command that searches for items expiring by the specified date.
     *
     * @param expiryDateInput Cutoff expiry date supplied by the user.
     */
    public FindItemByExpiryDateCommand(String expiryDateInput) {
        this.expiryDateInput = expiryDateInput;
    }

    /**
     * Executes the expiry-date search on the specified inventory.
     * The method parses the cutoff date, scans all items, and displays those whose
     * expiry dates are on or before the specified date.
     *
     * @param inventory Inventory containing the items to search.
     * @param ui User interface used to display search results.
     * @throws DukeException If the supplied expiry date cannot be parsed.
     */
    @Override
    public void execute(Inventory inventory, UI ui) throws DukeException {
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
            logger.log(Level.INFO, "No items found expiring by " + expiryDateInput);
            ui.showMessage("No items found expiring by " + expiryDateInput + ".");
            return;
        }

        logger.log(Level.INFO, "Found " + matches.size()
                + " item(s) expiring by " + expiryDateInput + ".");

        ui.showDivider();
        ui.showMessage("Items expiring by " + expiryDateInput + ":");
        ui.showNumberedList(matches);
        ui.showDivider();
    }
}
