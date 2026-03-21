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

public class FindItemByExpiryDateCommand extends Command {
    private static Logger logger = Logger.getLogger(
            FindItemByExpiryDateCommand.class.getName());

    private final String expiryDateInput;

    public FindItemByExpiryDateCommand(String expiryDateInput) {
        this.expiryDateInput = expiryDateInput;
    }

    @Override
    public void execute(Inventory inventory, UI ui) throws DukeException {
        assert inventory != null : "FindItemCommand received null inventory.";
        assert ui != null : "FindItemCommand received null UI.";
        assert expiryDateInput != null : "FindItemCommand received null expiry date.";
        logger.log(Level.FINE, "Executing FindItemByExpiryDateCommand for cutoff: " + expiryDateInput);

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
        for (int i = 0; i < matches.size(); i++) {
            ui.showMessage((i + 1) + ". " + matches.get(i));
        }
        ui.showDivider();
    }
}
