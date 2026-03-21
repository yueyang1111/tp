package seedu.duke.ui;

import seedu.duke.model.Category;
import seedu.duke.model.Inventory;
import seedu.duke.model.Item;

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

    public void showError(String message) {
        System.out.println("[Error] " + message);
    }

    public void showUnknownCommand() {
        showError("Unknown command. "
                + "Type 'help' to see available commands.");
    }

    public void showUnknownCategory(String category) {
        showError("Unknown category: " + category);
    }

    public void showCategoryNotFound(String categoryName) {
        showError("Category not found: " + categoryName);
    }

    public void showUnsupportedCategory(String categoryName) {
        showError("Unsupported category: " + categoryName);
    }

    public void showInvalidInput(String details) {
        showError("Invalid input. " + details);
    }

    public void showEmptyInput() {
        showError("Input cannot be empty. "
                + "Type 'help' to see available commands.");
    }

    public void showItemAdded(String itemName, int quantity,
                              String categoryName, String bin) {
        showDivider();
        System.out.println("Added item: " + itemName
                + " (qty: " + quantity + ")"
                + " to category: " + categoryName
                + " at bin: " + bin);
        showDivider();
    }

    public void showCategoryAdded(String categoryName) {
        showDivider();
        System.out.println("Added new category: " + categoryName);
        showDivider();
    }

    public void showItemDeleted(String itemName,
                                String categoryName) {
        showDivider();
        System.out.println("Deleted item: " + itemName
                + " from category: " + categoryName);
        showDivider();
    }

    public void showCategoryDeleted(String categoryName) {
        showDivider();
        System.out.println("Deleted category: " + categoryName);
        showDivider();
    }

    public void showItemNotFound(String itemName) {
        showError("Item not found: " + itemName);
    }

    public void showDeleteCategoryConfirmation(
            String categoryName, int itemCount) {
        showDivider();
        System.out.println("Category '" + categoryName
                + "' still has " + itemCount + " item(s).");
        System.out.println("Are you sure you want to delete "
                + "all items and remove this category?");
        System.out.print("Type 'yes' to confirm: ");
    }

    public void showDeleteCategoryCancelled(
            String categoryName) {
        System.out.println("Cancelled. Category '"
                + categoryName + "' was not deleted.");
    }

    public void showCategoryItemsCleared(String categoryName) {
        System.out.println("Cleared all items from category: "
                + categoryName);
    }

    public void showQuantityUpdated(String itemName,
                                    int oldQty, int newQty) {
        showDivider();
        System.out.println("Updated " + itemName
                + ": " + oldQty + " -> " + newQty);
        showDivider();
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
            System.out.println((i + 1) + ". " + cat.getName()
                    + " (" + cat.getItemCount() + " items)");
            List<Item> items = cat.getItems();
            for (int j = 0; j < items.size(); j++) {
                System.out.println("   " + (j + 1)
                        + ". " + items.get(j));
            }
        }
        showDivider();
    }

    public void showHelp() {
        showDivider();
        System.out.println("Available commands:");
        System.out.println();
        System.out.println("  help");
        System.out.println("    - Shows this help message");
        System.out.println();
        System.out.println("  --- Adding Items ---");
        System.out.println();
        System.out.println("  Add Fruit:");
        System.out.println("    add item/ITEM "
                + "category/fruits bin/BIN qty/QTY");
        System.out.println("        "
                + "expiryDate/DATE size/SIZE isRipe/BOOL");
        System.out.println("    e.g. add item/apple "
                + "category/fruits bin/A-10 qty/40");
        System.out.println("         "
                + "expiryDate/2026-10-03 size/big "
                + "isRipe/true");
        System.out.println();
        System.out.println("  Add Vegetable:");
        System.out.println("    add item/ITEM "
                + "category/vegetables bin/BIN qty/QTY");
        System.out.println("        "
                + "expiryDate/DATE isLeafy/BOOL");
        System.out.println("    e.g. add item/spinach "
                + "category/vegetables bin/C-01 qty/20");
        System.out.println("         "
                + "expiryDate/2026-10-03 isLeafy/true");
        System.out.println();
        System.out.println("  Add Snack:");
        System.out.println("    add item/ITEM "
                + "category/snacks bin/BIN qty/QTY");
        System.out.println("        "
                + "brand/BRAND expiryDate/DATE");
        System.out.println("    e.g. add item/chips "
                + "category/snacks bin/D-05 qty/50");
        System.out.println("         "
                + "brand/Lays expiryDate/2026-10-03");
        System.out.println();
        System.out.println("  Add Toiletries:");
        System.out.println("    add item/ITEM "
                + "category/toiletries bin/BIN qty/QTY");
        System.out.println("        "
                + "brand/BRAND isLiquid/BOOL expiryDate/DATE");
        System.out.println("    e.g. add item/shampoo "
                + "category/toiletries bin/E-02 qty/15");
        System.out.println("         "
                + "brand/Dove isLiquid/true expiryDate/2026-10-03");
        System.out.println();
        System.out.println("  --- Other Commands ---");
        System.out.println();
        System.out.println("  delete item/ITEM");
        System.out.println("    - Deletes an item");
        System.out.println("    e.g. delete item/banana");
        System.out.println();
        System.out.println("  delete category/CATEGORY");
        System.out.println("    - Deletes a category "
                + "(prompts for confirmation)");
        System.out.println("    e.g. delete category/fruits");
        System.out.println();
        System.out.println("  list");
        System.out.println("    - Lists all categories and items");
        System.out.println();
        System.out.println("  find expiryDate/DATE");
        System.out.println("    - Lists all items expiring on or before DATE");
        System.out.println("    e.g. find expiryDate/2026-10-03");
        System.out.println();
        System.out.println("  bye");
        System.out.println("    - Exits the program");
        showDivider();
    }

    public void close() {
        scanner.close();
    }
}
