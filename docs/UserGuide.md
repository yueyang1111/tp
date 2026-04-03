# User Guide

## Introduction

InventoryDock helps store managers keep track of inventory by category, quantity, expiry date, and bin location.
This guide covers the commands needed to add items, list all stored items, and search by category, expiry date, bin, or quantity.

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of the application jar.
3. Open a terminal in the project folder.
4. Run `java -jar duke.jar`.
5. Type a command and press Enter.

## Command Format

Notes about command syntax:

* Command words are not case-sensitive. For example, `LIST` and `list` are treated the same.
* Category matching is case-insensitive. For example, `fruits` and `FRUITS` refer to the same category.
* Field names are case-sensitive. Type them exactly as shown, such as `category/`, `item/`, `qty/`, and `expiryDate/`.
* For `add`, fields must be entered in the correct order for the selected category.
* Boolean fields only accept `true` or `false`.
* Quantities must be positive integers.
* Dates must use `yyyy-M-d`, for example `2026-3-9` or `2026-12-31`.
* Bin searches accept `LETTER-NUMBER`, `LETTER`, or `NUMBER`, such as `A-10`, `A`, or `10`.
* Quantity searches return items whose quantity is less than or equal to the specified positive integer.
* `update` only supports changing common item fields. Category-specific fields cannot be updated.
* `sort` only supports sorting by name, expiry date and quantity.

## Data Storage

InventoryDock stores data in `data/inventory.txt`.

* Data is saved automatically after every successfully executed command.
* Data is also saved when you exit the app with `bye`.
* If the storage file contains corrupted lines, the app skips those lines and continues loading the rest of the inventory.

## Features

Notes about the command format:

* Words in `UPPER_CASE` are placeholders you should replace with your own values.
* Item and category values are matched case-insensitively by the app.
* For `add`, fields must appear in the correct order.

### View help: `help`
Shows the list of available commands and the link to the published user guide.

Format:

`help`

### Adding an item: `add`
Adds a new item to an existing category.

Format:

`add category/CATEGORY item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE ...`

Common required fields:

* `category/` specifies the item category.
* `item/` specifies the item name.
* `bin/` specifies the bin location.
* `qty/` specifies the quantity as a positive integer.
* `expiryDate/` specifies the expiry date.

Supported categories and extra fields:

* Fruits
  `add category/fruits item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE size/SIZE isRipe/BOOLEAN`
* Vegetables
  `add category/vegetables item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE isLeafy/BOOLEAN`
* Toiletries
  `add category/toiletries item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE brand/BRAND isLiquid/BOOLEAN`
* Snacks
  `add category/snacks item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE brand/BRAND isCrunchy/BOOLEAN`
* Drinks
  `add category/drinks item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE brand/BRAND flavour/FLAVOUR isCarbonated/BOOLEAN`
* Ice cream
  `add category/icecream item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE flavour/FLAVOUR isDairyFree/BOOLEAN`
* Sweets
  `add category/sweets item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE brand/BRAND sweetnessLevel/LEVEL isChewy/BOOLEAN`
* Burger
  `add category/burger item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE isSpicy/BOOLEAN pattyType/TYPE`
* Set meal
  `add category/setmeal item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE mealType/TYPE foodSize/SIZE hasDrinks/BOOLEAN`
* Seafood
  `add category/seafood item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE seafoodType/TYPE origin/ORIGIN isFrozen/BOOLEAN`
* Meat
  `add category/meat item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE meatType/TYPE origin/ORIGIN isFrozen/BOOLEAN`
* Pet food
  `add category/petfood item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE petType/TYPE brand/BRAND isDryFood/BOOLEAN`
* Accessories
  `add category/accessories item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE type/TYPE material/MATERIAL isFragile/BOOLEAN`

Examples:

* `add category/fruits item/apple bin/A-10 qty/40 expiryDate/2026-10-3 size/big isRipe/true`
* `add category/snacks item/potato chips bin/D-5 qty/50 expiryDate/2026-8-12 brand/Lays isCrunchy/true`
* `add category/drinks item/apple_juice bin/F-1 qty/24 expiryDate/2026-10-3 brand/Marigold flavour/Apple isCarbonated/false`
* `add category/setmeal item/chicken_rice_set bin/G-3 qty/12 expiryDate/2026-4-1 mealType/lunch foodSize/large hasDrinks/true`

Expected result:

* The item is added to the specified category.
* The app confirms the item name, quantity, category, and bin location.

### Listing all items: `list`
Lists the entire inventory grouped by category.

Format: `list`

Example:

`list`

Expected result:

* All categories are shown in numbered order.
* Items under each category are listed with their details.
* If the inventory is empty, the app shows `Inventory is empty.`

### Sorting items: `sort` (Coming soon)
Lists the full inventory grouped by category, with the items inside each category sorted by the chosen field.

Format: `sort SORT_TYPE`

Valid `SORT_TYPE` values:
* `name`: Sorts items alphabetically by item name, ignoring letter case
* `expirydate` : Sort items by expiry date, from earliest to latest
* `qty` : Sorts items by quantity, from highest to lowest

Examples:

* `sort name`
* `sort expirydate`
* `sort qty`

Expected result:

* Items in each category are sorted according to the chosen sort type.
* Category order remains unchanged.
* If the inventory is empty, the app shows Inventory is empty.

### Find items by keyword: `find keyword/...`
Finds items whose names contain the given keyword.

Format:

`find keyword/KEYWORD`

Notes:

* Matching is case-insensitive.
* Partial matches work. For example, `apple` matches `apple`, `pineapple`, and `apple_juice`.

Examples:

* `find keyword/apple`
* `find keyword/chip`

Expected result:

* Matching items are listed together with their category names.
* If nothing matches, the app shows `No items found matching keyword: KEYWORD.`

### Finding items by category: `find category/...`
Shows all items stored in a specified category.

Format: `find category/CATEGORY`

Example:

`find category/fruits`

Expected result:

* If the category exists and contains items, the app lists all items in that category.
* If the category exists but has no items, the app shows `No items found in category: CATEGORY.`
* If the category does not exist, the app shows an error that the category was not found.

### Find items by expiry date: `find expiryDate/...`

Shows all items whose expiry date is on or before the specified date.

Format:

`find expiryDate/DATE`

Examples:

* `find expiryDate/2026-3-21`
* `find expiryDate/2026-12-31`

Expected result:

* Items expiring on the given date are included.
* Items expiring before the given date are also included.
* If nothing matches, the app shows `No items found expiring by DATE.`

### Finding items by bin: `find bin/...`
Shows all items that match a bin search.

Format: `find bin/BIN_INPUT`

Valid `BIN_INPUT` values:

* Full bin location, for example `A-10`
* Bin letter only, for example `A`
* Bin number only, for example `10`

Examples:

`find bin/A-10`

`find bin/A`

`find bin/10`

Expected result:

* A full bin location matches only that exact bin.
* A bin letter matches all items in bins with that letter.
* A bin number matches all items in bins with that number.
* If no items match, the app shows `No items found in bin location: BIN_INPUT.`

### Finding items by quantity: `find qty/...`
Shows all items whose quantity is less than or equal to the specified quantity.

Format: `find qty/QUANTITY`

Notes:

* `QUANTITY` must be a positive integer.
* This search is inclusive. For example, `find qty/15` includes items with quantity `15` and items with smaller quantities.

Examples:

* `find qty/10`
* `find qty/15`

Expected result:

* Items with quantity equal to the given value are included.
* Items with quantity lower than the given value are also included.
* If nothing matches, the app shows `No items found with quantity: QUANTITY.`

### Update an item: `update`

Updates an existing item in a category by its item index.

Format:

`update category/CATEGORY index/INDEX [newItem/NEW_NAME] [bin/NEW_BIN] [qty/NEW_QUANTITY] [expiryDate/NEW_DATE]`

Updatable fields:

* `newItem/` changes the item name
* `bin/` changes the bin location
* `qty/` changes the quantity
* `expiryDate/` changes the expiry date

Notes:

* `INDEX` is the item number within that category, using 1-based indexing.
* You must provide at least one field to update.
* Category-specific fields such as `brand/`, `isRipe/`, or `flavour/` cannot be updated with this command.

Examples:

* `update category/fruits index/1 qty/25`
* `update category/fruits index/1 newItem/green_apple bin/A-2 expiryDate/2026-4-1`

Expected result:

* The app updates the selected item.
* The app confirms which item was updated.
* If the item name changed, the new item name is shown as well.

### Delete an item: `delete category/... index/...`

Deletes one item from a category by its item index.

Format:

`delete category/CATEGORY index/INDEX`

Notes:

* `INDEX` is the item number within that category, using 1-based indexing.

Example:

`delete category/fruits index/2`

Expected result:

* The selected item is removed from the category.
* The app confirms the deleted item name and category.

### Clear a category: `delete category/...`

Clears all items in a category.

Format:

`delete category/CATEGORY`

Important:

* This command does not remove the category itself from InventoryDock.
* If the category is not empty, the app asks for confirmation.
* Type `yes` to continue.
* Any other reply cancels the operation.

Example:

`delete category/fruits`

Expected result:

* If confirmed, all items in the category are cleared.
* The category remains available for future items.
* If cancelled, no items are removed.

## Error Handling

Common reasons a command may fail:

* missing required fields
* fields entered in the wrong order for `add`
* invalid quantity values
* invalid dates
* invalid bin search format
* invalid item index
* unsupported update fields
* unknown categories or commands

When an error occurs, the app prints an error message and waits for the next command.

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Copy the application's data file to the same location on the other computer before starting the app there.

**Q:** Where is my inventory data stored?

**A:** In `data/inventory.txt`.

**Q:** Does the app create new categories?

**A:** No. InventoryDock works with a fixed set of built-in categories.

**Q:** Can I update category-specific fields such as `brand/` or `isFrozen/`?

**A:** No. The `update` command only supports `newItem/`, `bin/`, `qty/`, and `expiryDate/`.

**Q:** What date format should I use?

**A:** Use `yyyy-M-d`, for example `2026-3-21`.

**Q:** What happens if I delete a category?

**A**: The category is cleared, but the category itself remains in the inventory.

## Command Summary
* View help:
  `help`
* Add item:
  `add category/CATEGORY item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE ...`
* List all items:
  `list`
* Sort items:
  `sort SORT_TYPE`
* Find items by keyword:
  `find keyword/KEYWORD`
* Find items by category:
  `find category/CATEGORY`
* Find items by expiry date:
  `find expiryDate/DATE`
* Find items by bin:
  `find bin/BIN_INPUT`
* Find items by quantity:
  `find qty/QUANTITY`
* Update an item:
  `update category/CATEGORY index/INDEX [newItem/NEW_NAME] [bin/NEW_BIN] [qty/NEW_QUANTITY] [expiryDate/NEW_DATE]`
* Delete an item
  `delete category/CATEGORY index/INDEX`
* Clear a category
  `delete category/CATEGORY`

