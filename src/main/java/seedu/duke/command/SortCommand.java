package seedu.duke.command;

import seedu.duke.exception.DukeException;
import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;
import seedu.duke.parser.DateParser;
import seedu.duke.ui.UI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Displays the full inventory grouped by category with items sorted within each category.
 */
public class SortCommand extends Command {
    private static final Logger logger = Logger.getLogger(SortCommand.class.getName());
    private final String sortType;

    /**
     * Constructs a SortCommand with the specified sort type.
     *
     * @param sortType The type of sorting to apply: "name", "expirydate", or "qty".
     */
    public SortCommand(String sortType) {
        this.sortType = sortType;
    }

    /**
     * Executes the sort command by displaying the inventory with items sorted within each category.
     *
     * @param inventory The inventory to display.
     * @param ui The UI to display results to the user.
     * @throws DukeException If an error occurs during execution.
     */
    @Override
    public void execute(Inventory inventory, UI ui) throws DukeException {
        assert inventory != null : "SortCommand received null inventory.";
        assert ui != null : "SortCommand received null UI.";
        assert sortType != null : "SortCommand received null sort type.";

        logger.log(Level.INFO, "Sorting inventory by " + sortType);
        ui.showSortedInventory(inventory, getSortedItemsByCategory(inventory), formatSortLabel());
    }

    private List<List<Item>> getSortedItemsByCategory(Inventory inventory) {
        List<List<Item>> sortedItemsByCategory = new ArrayList<>();

        for (Category category : inventory.getCategories()) {
            List<Item> sortedItems = new ArrayList<>(category.getItems());
            sortedItems.sort(getComparator());
            sortedItemsByCategory.add(sortedItems);
        }

        return sortedItemsByCategory;
    }

    private String formatSortLabel() {
        return switch (sortType.toLowerCase()) {
            case "name" -> "name";
            case "qty" -> "quantity";
            case "expirydate" -> "expiry date";
            default -> sortType;
        };
    }

    private Comparator<Item> getComparator() {
        return switch (sortType.toLowerCase()) {
            case "qty" -> Comparator.comparing(Item::getQuantity).reversed();
            case "expirydate" -> Comparator.comparing(item -> parseExpiryDate(item.getExpiryDate()));
            default -> Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER);
        };
    }

    private LocalDate parseExpiryDate(String expiryDate) {
        try {
            return DateParser.parseDate(expiryDate);
        } catch (DukeException e) {
            return LocalDate.MAX;
        }
    }
}
