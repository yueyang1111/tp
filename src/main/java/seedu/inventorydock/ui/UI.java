package seedu.inventorydock.ui;

import seedu.inventorydock.command.SummaryCommand;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.model.Item;

import java.util.List;
import java.util.Scanner;

/**
 * Handles all user-facing input and output.
 * All messages displayed to the user go through this class.
 */
public class UI {

    private static final String DIVIDER =
            "_________________________________________"
                    + "________________________________"
                    + "________________________________";
    private static final String LOGO =
            " _____       _____\n"
                    + "|_   _|     |  __ \\\n"
                    + "  | |       | |  | |\n"
                    + "  | |       | |  | |\n"
                    + "  | |   _   | |__| |\n"
                    + "|_____||_|  |_____/\n";

    private final Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    private String capitalise(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase()
                + text.substring(1);
    }

    public String readCommand() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null;
    }

    public void showWelcome() {
        showDivider();
        System.out.println("Hello from\n" + LOGO);
        System.out.println("Hello! Welcome to InventoryDock!");
        System.out.println("What can I do for you?");
        showDivider();
    }

    public void showGoodbye() {
        System.out.println("Goodbye! See you next time!");
        showDivider();
    }

    public void showDivider() {
        System.out.println(DIVIDER);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public String formatFoundItemsMessage(int matchCount, String searchDescription) {
        return "Found " + matchCount + " item(s) " + searchDescription + ".";
    }

    public String formatNoItemsFoundMessage(String searchDescription) {
        return "No items found " + searchDescription + ".";
    }

    public String formatFindResultsHeader(String resultsDescription) {
        return "Items " + resultsDescription;
    }

    private String formatItemCount(int itemCount) {
        return itemCount + (itemCount == 1 ? " item" : " items");
    }

    public void showNumberedList(List<?> items) {
        for (int i = 0; i < items.size(); i++) {
            showMessage((i + 1) + ". " + items.get(i));
        }
    }

    public void showError(String message) {
        System.out.println("[Error] " + message);
        showDivider();
    }

    public void showError(String category, String message) {
        showError(category + ": " + message);
    }

    public void showError(InventoryDockException exception) {
        showError(exception.getErrorCategory(), exception.getMessage());
    }

    public void showCategoryNotFound(String categoryName) {
        showError("Not found", "Category '" + categoryName + "' does not exist.");
    }

    public void showItemAdded(String itemName, int quantity,
                              String categoryName, String bin) {
        showDivider();
        System.out.println("Added item: " + itemName + " (qty: " + quantity + ")" + " to category: " + categoryName
                + " at bin: " + bin);
        showDivider();
    }

    public void showItemDeleted(String itemName,
                                String categoryName) {
        showDivider();
        System.out.println("Deleted item: " + itemName + " from category: " + categoryName);
        showDivider();
    }

    public void showItemUpdated(String oldItemName,
                                String newItemName,
                                String categoryName) {
        showDivider();
        System.out.println("Updated item: " + oldItemName + " in category: " + categoryName);
        if (!oldItemName.equals(newItemName)) {
            System.out.println("New item name: " + newItemName);
        }
        showDivider();
    }

    public void showCategoryCleared(String categoryName) {
        showDivider();
        System.out.println("Cleared category: " + categoryName);
        showDivider();
    }

    public void showClearCategoryConfirmation(
            String categoryName, int itemCount) {
        showDivider();
        System.out.println("Category '" + categoryName + "' still has " + formatItemCount(itemCount) + ".");
        System.out.println("Are you sure you want to clear all items from this category?");
        System.out.print("Type 'yes' to confirm: ");
    }

    public void showClearCategoryCancelled(
            String categoryName) {
        System.out.println("Cancelled. Category '" + categoryName + "' was not cleared.");
        showDivider();
    }

    public void showCategoryAlreadyEmpty(String categoryName) {
        showDivider();
        System.out.println("Category '" + categoryName + "' is already empty. Nothing to clear.");
        showDivider();
    }

    public void showCategoryItemsCleared(String categoryName) {
        System.out.println("Cleared all items from category: " + categoryName);
    }

    public void showInventory(Inventory inventory) {
        List<Category> categories = inventory.getCategories();
        if (categories.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        showDivider();
        System.out.println("Current Inventory:");
        System.out.println();
        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            System.out.println((i + 1) + ". " + capitalise(cat.getName())
                    + " (" + formatItemCount(cat.getItemCount()) + ")");
            List<Item> items = cat.getItems();
            for (int j = 0; j < items.size(); j++) {
                System.out.println("   " + (j + 1) + ". " + items.get(j));
            }
        }
        showDivider();
    }

    public void showSortedInventory(Inventory inventory, List<List<Item>> sortedItemsByCategory, String sortLabel) {
        List<Category> categories = inventory.getCategories();
        if (categories.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        showDivider();
        System.out.println("Current Inventory (sorted by " + sortLabel + "):");
        System.out.println("Items remain grouped by category.");
        System.out.println();
        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            System.out.println((i + 1) + ". " + capitalise(cat.getName())
                    + " (" + formatItemCount(cat.getItemCount()) + ")");
            List<Item> items = sortedItemsByCategory.get(i);
            for (int j = 0; j < items.size(); j++) {
                System.out.println("   " + (j + 1) + ". " + items.get(j));
            }
        }
        showDivider();
    }

    public void showInventorySummary(List<SummaryCommand.CategorySummary> summaries,
                                     SummaryCommand.SummaryType summaryType) {
        if (summaries.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        showDivider();
        System.out.println(formatSummaryHeader(summaryType));
        System.out.println();

        for (int i = 0; i < summaries.size(); i++) {
            SummaryCommand.CategorySummary summary = summaries.get(i);

            System.out.println((i + 1) + ". " + capitalise(summary.getCategoryName())
                    + " (" + formatItemCount(summary.getItemCount()) + ")");

            if (summaryType == SummaryCommand.SummaryType.ALL
                    || summaryType == SummaryCommand.SummaryType.STOCK) {
                System.out.println("   Lowest stock:");
                printIndexedItems(summary.getLowestStockItems());
            }

            if (summaryType == SummaryCommand.SummaryType.ALL
                    || summaryType == SummaryCommand.SummaryType.EXPIRYDATE) {
                System.out.println("   Earliest expiry:");
                printIndexedItems(summary.getEarliestExpiryItems());
            }

            System.out.println();
        }

        showDivider();
    }

    private String formatSummaryHeader(SummaryCommand.SummaryType summaryType) {
        switch (summaryType) {
        case STOCK:
            return "Inventory Summary (lowest stock):";
        case EXPIRYDATE:
            return "Inventory Summary (earliest expiry):";
        default:
            return "Inventory Summary:";
        }
    }

    private void printIndexedItems(List<SummaryCommand.IndexedItem> indexedItems) {
        if (indexedItems.isEmpty()) {
            System.out.println("   N/A");
            return;
        }

        for (SummaryCommand.IndexedItem indexedItem : indexedItems) {
            System.out.println("   [Item " + indexedItem.getIndex() + "] " + indexedItem.getItem());
        }
    }

    public void showSkippedLine(String line, String reason) {
        showDivider();
        System.out.println("Skip corrupted line: " + line);
        System.out.println("Reason: " + reason);
        showDivider();
    }

    public void showHelp() {
        showDivider();
        System.out.println("Available commands: " + "add, delete, update, find, sort, summary, list, help, bye");
        System.out.println();
        System.out.println("For detailed usage and examples, " + "refer to our User Guide:");
        System.out.println("https://ay2425s2-cs2113-w09-2.github.io/tp/UserGuide.html");
        showDivider();
    }

    public void close() {
        scanner.close();
    }
}
