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

    public void showError(String message) {
        System.out.println("[Error] " + message);
        showDivider();
    }

    public void showUnknownCommand() {
        showError("Unknown command. "
                + "Type 'help' to see available commands.");
    }

    public void showCategoryNotFound(String categoryName) {
        showError("Category not found: " + categoryName);
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
                    + " (" + cat.getItemCount() + " items)");
            List<Item> items = cat.getItems();
            for (int j = 0; j < items.size(); j++) {
                System.out.println("   " + (j + 1)
                        + ". " + items.get(j));
            }
        }
        showDivider();
    }

    public void showSkippedLine(String line, String reason) {
        showDivider();
        System.out.println("Skip corrupted line: " + line);
        System.out.println("Reason: " + reason);
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
                + "brand/BRAND isLiquid/BOOL "
                + "expiryDate/DATE");
        System.out.println("    e.g. add item/shampoo "
                + "category/toiletries bin/E-02 qty/15");
        System.out.println("         "
                + "brand/Dove isLiquid/true "
                + "expiryDate/2026-10-03");
        System.out.println();
        System.out.println("  Add Drinks:");
        System.out.println("    add item/ITEM "
                + "category/drinks bin/BIN qty/QTY");
        System.out.println("        "
                + "expiryDate/DATE brand/BRAND "
                + "flavour/FLAVOUR");
        System.out.println("    e.g. add item/coke "
                + "category/drinks bin/F-01 qty/24");
        System.out.println("         "
                + "expiryDate/2026-10-03 brand/CocaCola "
                + "flavour/Cola");
        System.out.println();
        System.out.println("  Add Ice Cream:");
        System.out.println("    add item/ITEM "
                + "category/icecream bin/BIN qty/QTY");
        System.out.println("        "
                + "expiryDate/DATE flavour/FLAVOUR "
                + "isDairyFree/BOOL");
        System.out.println("    e.g. add item/vanilla_cup "
                + "category/icecream bin/G-03 qty/10");
        System.out.println("         "
                + "expiryDate/2026-10-03 "
                + "flavour/Vanilla isDairyFree/false");
        System.out.println();
        System.out.println("  Add Sweets:");
        System.out.println("    add item/ITEM "
                + "category/sweets bin/BIN qty/QTY");
        System.out.println("        "
                + "expiryDate/DATE brand/BRAND "
                + "sweetnessLevel/LEVEL");
        System.out.println("    e.g. add item/gummy_bears "
                + "category/sweets bin/H-02 qty/30");
        System.out.println("         "
                + "expiryDate/2026-10-03 brand/Haribo "
                + "sweetnessLevel/High");
        System.out.println();
        System.out.println("  Add Burger:");
        System.out.println("    add item/ITEM "
                + "category/burger bin/BIN qty/QTY");
        System.out.println("        "
                + "expiryDate/DATE isSpicy/BOOL "
                + "pattyType/TYPE");
        System.out.println("    e.g. add item/zinger "
                + "category/burger bin/I-01 qty/6");
        System.out.println("         "
                + "expiryDate/2026-10-03 isSpicy/true "
                + "pattyType/Chicken");
        System.out.println();
        System.out.println("  Add Set Meal:");
        System.out.println("    add item/ITEM "
                + "category/setmeal bin/BIN qty/QTY");
        System.out.println("        "
                + "expiryDate/DATE mealType/TYPE "
                + "foodSize/SIZE");
        System.out.println("    e.g. add item/chicken_set "
                + "category/setmeal bin/J-01 qty/7");
        System.out.println("         "
                + "expiryDate/2026-10-03 "
                + "mealType/Lunch foodSize/Large");
        System.out.println();
        System.out.println("  Add Seafood:");
        System.out.println("    add item/ITEM "
                + "category/seafood bin/BIN qty/QTY");
        System.out.println("        "
                + "expiryDate/DATE seafoodType/TYPE "
                + "origin/ORIGIN");
        System.out.println("    e.g. add item/salmon "
                + "category/seafood bin/K-02 qty/12");
        System.out.println("         "
                + "expiryDate/2026-10-03 "
                + "seafoodType/Fish origin/Norway");
        System.out.println();
        System.out.println("  Add Meat:");
        System.out.println("    add item/ITEM "
                + "category/meat bin/BIN qty/QTY");
        System.out.println("        "
                + "expiryDate/DATE meatType/TYPE "
                + "origin/ORIGIN");
        System.out.println("    e.g. add item/wagyu "
                + "category/meat bin/L-01 qty/8");
        System.out.println("         "
                + "expiryDate/2026-10-03 "
                + "meatType/Beef origin/Japan");
        System.out.println();
        System.out.println("  Add Pet Food:");
        System.out.println("    add item/ITEM "
                + "category/petfood bin/BIN qty/QTY");
        System.out.println("        "
                + "expiryDate/DATE petType/TYPE "
                + "brand/BRAND");
        System.out.println("    e.g. add item/kibble "
                + "category/petfood bin/M-01 qty/15");
        System.out.println("         "
                + "expiryDate/2026-10-03 "
                + "petType/Dog brand/Pedigree");
        System.out.println();
        System.out.println("  Add Accessories:");
        System.out.println("    add item/ITEM "
                + "category/accessories bin/BIN qty/QTY");
        System.out.println("        "
                + "expiryDate/DATE type/TYPE "
                + "material/MATERIAL");
        System.out.println("    e.g. add item/watch "
                + "category/accessories bin/N-01 qty/4");
        System.out.println("         "
                + "expiryDate/2026-10-03 "
                + "type/Wearable material/Leather");
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
        System.out.println("    - Lists all categories "
                + "and items");
        System.out.println();
        System.out.println("  find keyword/KEYWORD");
        System.out.println("    - Lists all items matching "
                + "the keyword");
        System.out.println("    e.g. find keyword/apple");
        System.out.println();
        System.out.println("  find category/CATEGORY");
        System.out.println("    - Lists all items in "
                + "a category");
        System.out.println("    e.g. find category/fruits");
        System.out.println();
        System.out.println("  find expiryDate/DATE");
        System.out.println("    - Lists all items expiring "
                + "on or before DATE");
        System.out.println("    e.g. find "
                + "expiryDate/2026-10-03");
        System.out.println();
        System.out.println("  bye");
        System.out.println("    - Exits the program");
        showDivider();
    }

    public void close() {
        scanner.close();
    }
}
