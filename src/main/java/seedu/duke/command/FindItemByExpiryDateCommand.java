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
    private static final Logger logger = Logger.getLogger(
            FindItemByExpiryDateCommand.class.getName());

    private final String expiryDateInput;

    public FindItemByExpiryDateCommand(String expiryDateInput) {
        this.expiryDateInput = expiryDateInput;
    }

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
