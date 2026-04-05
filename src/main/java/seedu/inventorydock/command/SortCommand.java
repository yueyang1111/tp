package seedu.inventorydock.command;

import seedu.inventorydock.exception.DukeException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.parser.DateParser;
import seedu.inventorydock.ui.UI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Displays the inventory grouped by category with items sorted within each category.
 */
public class SortCommand extends Command {
    private static final Logger logger = Logger.getLogger(SortCommand.class.getName());
    private final String sortType;

    /**
     * Creates a sort command with the specified sort type.
     *
     * @param sortType Type of sorting to apply: "name", "expirydate", or "qty".
     */
    public SortCommand(String sortType) {
        this.sortType = sortType;
    }

    /**
     * Executes the sort command on the specified inventory.
     * Displays the inventory with items sorted within each category.
     *
     * @param inventory Inventory to display.
     * @param ui User interface used to display the sorted inventory.
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

    /**
     * Returns sorted item lists for each category in the inventory.
     *
     * @param inventory Inventory containing categories to sort.
     * @return Item lists sorted according to the selected sort type.
     */
    private List<List<Item>> getSortedItemsByCategory(Inventory inventory) {
        List<List<Item>> sortedItemsByCategory = new ArrayList<>();

        for (Category category : inventory.getCategories()) {
            List<Item> sortedItems = new ArrayList<>(category.getItems());
            sortedItems.sort(getComparator());
            sortedItemsByCategory.add(sortedItems);
        }

        return sortedItemsByCategory;
    }

    /**
     * Returns a user-friendly label for the selected sort type.
     *
     * @return Display label for the current sort type.
     */
    private String formatSortLabel() {
        return switch (sortType.toLowerCase()) {
        case "name" -> "name";
        case "qty" -> "quantity";
        case "expirydate" -> "expiry date";
        default -> sortType;
        };
    }

    /**
     * Returns the comparator used to sort items.
     *
     * @return Comparator for the current sort type.
     */
    private Comparator<Item> getComparator() {
        return switch (sortType.toLowerCase()) {
        case "qty" -> Comparator.comparing(Item::getQuantity).reversed();
        case "expirydate" -> Comparator.comparing(item -> parseExpiryDate(item.getExpiryDate()));
        default -> Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER);
        };
    }

    /**
     * Parses the specified expiry date string.
     * Returns {@code LocalDate.MAX} if the date cannot be parsed.
     *
     * @param expiryDate Expiry date string to parse.
     * @return Parsed expiry date, or {@code LocalDate.MAX} if parsing fails.
     */
    private LocalDate parseExpiryDate(String expiryDate) {
        try {
            return DateParser.parseDate(expiryDate);
        } catch (DukeException e) {
            return LocalDate.MAX;
        }
    }
}
