package seedu.inventorydock.command;

import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.ui.UI;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Finds and displays items whose bin locations match a bin query.
 */
public class FindItemByBinCommand extends Command {
    private static final Logger logger = Logger.getLogger(FindItemByBinCommand.class.getName());
    private final String binInput;

    /**
     * Creates a command that searches inventory items by bin input.
     *
     * @param binInput normalized bin query supplied by the parser.
     */
    public FindItemByBinCommand(String binInput) {
        this.binInput = binInput;
    }

    /**
     * Scans every item in the inventory and displays those whose bin location matches the query.
     *
     * @param inventory inventory to search.
     * @param ui user interface used to display search results.
     */
    @Override
    public void execute(Inventory inventory, UI ui) {
        assert inventory != null : "FindItemByBinCommand received null inventory.";
        assert ui != null : "FindItemByBinCommand received null UI.";
        assert binInput != null : "FindItemByBinCommand received null bin input.";

        List<Item> matches = new ArrayList<>();
        List<Category> categories = inventory.getCategories();

        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (isMatchingBin(item.getBinLocation(), binInput)) {
                    matches.add(item);
                }
            }
        }

        if (matches.isEmpty()) {
            String noItemsMessage = ui.formatNoItemsFoundMessage("in bin location: " + binInput);
            logger.log(Level.INFO, noItemsMessage);
            ui.showMessage(noItemsMessage);
            return;
        }

        logger.log(Level.INFO, ui.formatFoundItemsMessage(matches.size(),
                "in bin location '" + binInput.toUpperCase() + "'"));

        ui.showDivider();
        ui.showMessage(ui.formatFindResultsHeader("in bin location: " + binInput));
        ui.showNumberedList(matches);
        ui.showDivider();
    }

    /**
     * Matches a stored bin location against a normalized bin query.
     * An exact query such as {@code a-10} matches only that bin, a single-letter
     * query such as {@code a} matches all bins in that row, and a numeric query
     * such as {@code 10} matches all bins with that number.
     *
     * @param itemBinLocation stored item bin location.
     * @param binInput normalized bin query.
     * @return {@code true} if the item bin satisfies the query.
     */
    public static boolean isMatchingBin(String itemBinLocation, String binInput) {
        assert itemBinLocation != null : "BinLocationParser received null item bin location.";
        assert binInput != null : "BinLocationParser received null search input.";

        String normalizedBinLocation = itemBinLocation.trim().toLowerCase();
        String[] binParts = normalizedBinLocation.split("-", 2);

        assert binParts.length == 2 : "Stored bin location is not in LETTER-NUMBER format: " + itemBinLocation;

        String binLetter = binParts[0];
        String binNumber = binParts[1];

        if (binInput.contains("-")) {
            return normalizedBinLocation.equals(binInput);
        }

        if (Character.isLetter(binInput.charAt(0))) {
            return binLetter.equals(binInput);
        }

        return binNumber.equals(binInput);
    }
}
