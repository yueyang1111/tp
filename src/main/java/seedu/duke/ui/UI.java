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

    public void showItemUpdated(String oldItemName,
                                String newItemName,
                                String categoryName) {
        showDivider();
        System.out.println("Updated item: " + oldItemName
                + " in category: " + categoryName);
        if (!oldItemName.equals(newItemName)) {
            System.out.println("New item name: " + newItemName);
        }
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
        System.out.println("    add category/fruits "
                + "item/ITEM bin/BIN qty/QTY");
        System.out.println("        expiryDate/DATE "
                + "size/SIZE isRipe/BOOL");
        System.out.println("    e.g. add category/fruits "
                + "item/apple bin/A-10 qty/40");
        System.out.println("         expiryDate/2026-10-03 "
                + "size/big isRipe/true");
        System.out.println();
        System.out.println("  Add Vegetable:");
        System.out.println("    add category/vegetables "
                + "item/ITEM bin/BIN qty/QTY");
        System.out.println("        expiryDate/DATE "
                + "isLeafy/BOOL");
        System.out.println("    e.g. add category/vegetables "
                + "item/spinach bin/C-01 qty/20");
        System.out.println("         expiryDate/2026-10-03 "
                + "isLeafy/true");
        System.out.println();
        System.out.println("  Add Snack:");
        System.out.println("    add category/snacks "
                + "item/ITEM bin/BIN qty/QTY");
        System.out.println("        expiryDate/DATE "
                + "brand/BRAND");
        System.out.println("    e.g. add category/snacks "
                + "item/chips bin/D-05 qty/50");
        System.out.println("         expiryDate/2026-10-03 "
                + "brand/Lays");
        System.out.println();
        System.out.println("  Add Toiletries:");
        System.out.println("    add category/toiletries "
                + "item/ITEM bin/BIN qty/QTY");
        System.out.println("        expiryDate/DATE "
                + "brand/BRAND isLiquid/BOOL");
        System.out.println("    e.g. add category/toiletries "
                + "item/shampoo bin/E-02 qty/15");
        System.out.println("         expiryDate/2026-10-03 "
                + "brand/Dove isLiquid/true");
        System.out.println();
        System.out.println("  Add Drinks:");
        System.out.println("    add category/drinks "
                + "item/ITEM bin/BIN qty/QTY");
        System.out.println("        expiryDate/DATE brand/BRAND "
                + "flavour/FLAVOUR");
        System.out.println("    e.g. add category/drinks "
                + "item/coke bin/F-01 qty/24");
        System.out.println("         expiryDate/2026-10-03 brand/CocaCola "
                + "flavour/Cola");
        System.out.println();
        System.out.println("  Add Ice Cream:");
        System.out.println("    add category/icecream "
                + "item/ITEM bin/BIN qty/QTY");
        System.out.println("        expiryDate/DATE flavour/FLAVOUR "
                + "isDairyFree/BOOL");
        System.out.println("    e.g. add category/icecream "
                + "item/vanilla_cup bin/G-03 qty/10");
        System.out.println("         expiryDate/2026-10-03 "
                + "flavour/Vanilla isDairyFree/false");
        System.out.println();
        System.out.println("  Add Sweets:");
        System.out.println("    add category/sweets "
                + "item/ITEM bin/BIN qty/QTY");
        System.out.println("        expiryDate/DATE brand/BRAND "
                + "sweetnessLevel/LEVEL");
        System.out.println("    e.g. add category/sweets "
                + "item/gummy_bears bin/H-02 qty/30");
        System.out.println("         expiryDate/2026-10-03 brand/Haribo "
                + "sweetnessLevel/High");
        System.out.println();
        System.out.println("  Add Burger:");
        System.out.println("    add category/burger "
                + "item/ITEM bin/BIN qty/QTY");
        System.out.println("        expiryDate/DATE isSpicy/BOOL "
                + "pattyType/TYPE");
        System.out.println("    e.g. add category/burger "
                + "item/zinger bin/I-01 qty/6");
        System.out.println("         expiryDate/2026-10-03 isSpicy/true "
                + "pattyType/Chicken");
        System.out.println();
        System.out.println("  Add Set Meal:");
        System.out.println("    add category/setmeal "
                + "item/ITEM bin/BIN qty/QTY");
        System.out.println("        expiryDate/DATE mealType/TYPE "
                + "foodSize/SIZE");
        System.out.println("    e.g. add category/setmeal "
                + "item/chicken_set bin/J-01 qty/7");
        System.out.println("         expiryDate/2026-10-03 "
                + "mealType/Lunch foodSize/Large");
        System.out.println();
        System.out.println("  Add Seafood:");
        System.out.println("    add category/seafood "
                + "item/ITEM bin/BIN qty/QTY");
        System.out.println("        expiryDate/DATE seafoodType/TYPE "
                + "origin/ORIGIN");
        System.out.println("    e.g. add category/seafood "
                + "item/salmon bin/K-02 qty/12");
        System.out.println("         expiryDate/2026-10-03 "
                + "seafoodType/Fish origin/Norway");
        System.out.println();
        System.out.println("  Add Meat:");
        System.out.println("    add category/meat "
                + "item/ITEM bin/BIN qty/QTY");
        System.out.println("        expiryDate/DATE meatType/TYPE "
                + "origin/ORIGIN");
        System.out.println("    e.g. add category/meat "
                + "item/wagyu bin/L-01 qty/8");
        System.out.println("         expiryDate/2026-10-03 "
                + "meatType/Beef origin/Japan");
        System.out.println();
        System.out.println("  Add Pet Food:");
        System.out.println("    add category/petfood "
                + "item/ITEM bin/BIN qty/QTY");
        System.out.println("        expiryDate/DATE petType/TYPE "
                + "brand/BRAND");
        System.out.println("    e.g. add category/petfood "
                + "item/kibble bin/M-01 qty/15");
        System.out.println("         expiryDate/2026-10-03 "
                + "petType/Dog brand/Pedigree");
        System.out.println();
        System.out.println("  Add Accessories:");
        System.out.println("    add category/accessories "
                + "item/ITEM bin/BIN qty/QTY");
        System.out.println("        expiryDate/DATE type/TYPE "
                + "material/MATERIAL");
        System.out.println("    e.g. add category/accessories "
                + "item/watch bin/N-01 qty/4");
        System.out.println("         expiryDate/2026-10-03 "
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
        System.out.println("  update category/CATEGORY index/INDEX ...");
        System.out.println("    - Updates item name, bin, quantity, "
                + "or expiry date");
        System.out.println("    e.g. update category/fruits "
                + "index/1 newItem/green_apple bin/A-11");
        System.out.println("    e.g. update category/vegetables "
                + "index/2 qty/25 expiryDate/2026-12-01");
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
