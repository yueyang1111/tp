# User Guide

## Introduction

InventoryDock helps store managers keep track of inventory by category, quantity, expiry date, and bin location.
This guide covers the commands needed to add items, list all stored items, and search by category, expiry date, bin, or quantity.

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of the application jar.
3. Open a terminal in the project folder.
4. Run `java -jar tp.jar`.
5. Type a command and press Enter.

## Command Format

Notes about command syntax:

* Command words are not case-sensitive. For example, `LIST` and `list` are treated the same.
* Category matching is case-insensitive. For example, `fruits` and `FRUITS` refer to the same category.
* Field names are case-sensitive. Type them exactly as shown, such as `category/`, `item/`, `qty/`, and `expiryDate/`.
* For `add`, fields must be entered in the correct order for the selected category.
* Item names can contain spaces. For example, `item/potato chips` and `newItem/sour cream chips` are valid.
* Boolean fields only accept `true` or `false`.
* Quantities must be positive integers.
* Dates must use `yyyy-M-d`, for example `2026-3-9` or `2026-12-31`.
* Bin locations for `add` and `update` must use the exact `LETTER-NUMBER` format, for example `A-10`.
* Bin searches accept `LETTER-NUMBER`, `LETTER`, or `NUMBER`, such as `A-10`, `A`, or `10`.
* Quantity searches return items whose quantity is less than or equal to the specified positive integer.
* `update` supports both common item fields and the category-specific boolean field for the item's category.
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

### Adding an item: `add`
Adds a new item to an existing built-in category.

Format:

`add category/CATEGORY item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE ...`

Common required fields:

* `category/` specifies the item category.
* `item/` specifies the item name.
* `bin/` specifies the bin location.
* `qty/` specifies the quantity as a positive integer.
* `expiryDate/` specifies the expiry date.

Supported categories and their boolean field:

* Fruits
  `add category/fruits item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE isRipe/BOOLEAN`
* Vegetables
  `add category/vegetables item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE isLeafy/BOOLEAN`
* Toiletries
  `add category/toiletries item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE isLiquid/BOOLEAN`
* Snacks
  `add category/snacks item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE isCrunchy/BOOLEAN`
* Drinks
  `add category/drinks item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE isCarbonated/BOOLEAN`
* Meat
  `add category/meat item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE isFrozen/BOOLEAN`
* Accessories
  `add category/accessories item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE isFragile/BOOLEAN`

Examples:

* `add category/fruits item/apple bin/A-10 qty/40 expiryDate/2026-10-3 isRipe/true`
* `add category/snacks item/potato chips bin/D-5 qty/50 expiryDate/2026-8-12 isCrunchy/true`
* `add category/drinks item/apple_juice bin/F-1 qty/24 expiryDate/2026-10-3 isCarbonated/true`
* `add category/vegetables item/spinach bin/B-2 qty/30 expiryDate/2026-6-1 isLeafy/true`

Expected result:

* The item is added to the specified category.
* The app confirms the item name, quantity, category, and bin location after the add command has been parsed and executed successfully.
* Each category uses the same overall add-command structure, with a different category-specific boolean field such as `isRipe/`, `isLeafy/`, or `isCarbonated/`.
* Duplicate-batch rule for `add`:
  * Duplicate checking ignores only `qty/` and `bin/`.
  * The logical batch identity still includes `category/`, `item/`, `expiryDate/`, and the category-specific boolean field.
  * If another item in the same category has the same logical batch identity, the command is rejected with `Duplicate item found for category/CATEGORY item/ITEM.`

Example (`fruits`: `isRipe/`):
* Existing add command:
  * `add category/fruits item/apple bin/A-10 qty/10 expiryDate/2026-6-5 isRipe/true`
* This command causes the duplicate exception (only `bin/` and `qty/` changed):
  * `add category/fruits item/apple bin/B-20 qty/99 expiryDate/2026-6-5 isRipe/true`
  Result: `Duplicate item found for category/fruits item/apple.`
* This command is allowed as a new batch of items (`expiryDate/` changed):
  * `add category/fruits item/apple bin/B-20 qty/99 expiryDate/2026-6-6 isRipe/true`

### Find items by keyword: `find keyword/...`
Finds items whose names contain the given keyword or phrase.

Format:

`find keyword/KEYWORD_OR_PHRASE`

Notes:

* Matching is case-insensitive.
* Partial matches work. For example, `apple` matches `apple`, `pineapple`, and `apple_juice`.
* Multi-word phrases are supported. For example, `potato chips` matches `potato chips`.

Examples:

* `find keyword/apple`
* `find keyword/chip`
* `find keyword/potato chips`

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
* If the category does not exist, the app shows `[Error] Not found: Category 'CATEGORY' does not exist.`

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

### Viewing inventory summary: `summary`
Displays a category based summary of the inventory.

Format:
* `summary`
* `summary stock`
* `summary expirydate`

Expected result:
* `summary` shows each category with its item count, tied lowest stock items, and tied earliest expiry items.
* `summary stock` shows each category with its item count and tied lowest stock items only.
* `summary expirydate` shows each category with its item count and tied earliest expiry items only.
* Item indices shown are category local indices, matching the numbering used in `list`.
* If a category has no matching items to display, the app shows `N/A`.

Examples:
* `summary`
* `summary stock`
* `summary expirydate`

### View help: `help`
Shows the list of available commands and the link to the published user guide.

Format:

`help`

### Listing all items: `list`
Lists the entire inventory grouped by category.

Format: `list`

Example:

`list`

Expected result:

* All categories are shown in numbered order.
* Items under each category are listed with their details.
* If the inventory is empty, the app will show the empty inventory.

### Sorting items: `sort`
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
* If the inventory is empty, the app shows the empty inventory.

### Update an item: `update`

Updates an existing item in a category by its item index.

Format:

`update category/CATEGORY index/INDEX [newItem/NEW_NAME] [bin/NEW_BIN] [qty/NEW_QUANTITY] [expiryDate/NEW_DATE]`

Updatable fields:

* `newItem/` changes the item name
* `bin/` changes the bin location
* `qty/` changes the quantity
* `expiryDate/` changes the expiry date
* The category-specific boolean field can also be updated for the matching category, for example `isRipe/false`

Notes:

* `INDEX` is the item number within that category, using 1-based indexing.
* You must provide at least one field to update.
* Category-specific boolean fields such as `isRipe/`, `isCarbonated/`, or `isFrozen/` can also be updated, but only for items in the matching category.
* `newItem/` supports names with spaces, for example `newItem/sour cream chips`.
* `bin/` must use the exact `LETTER-NUMBER` format, for example `A-2`.
* `update` uses the same duplicate-batch rule as `add`: only `qty/` and `bin/` are ignored when checking for duplicates.
Examples:

* `update category/fruits index/1 qty/25`
* `update category/fruits index/1 newItem/green_apple bin/A-2 expiryDate/2026-4-1`
* `update category/snacks index/1 newItem/salted chips`

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

### Clear a category: `clear category/...`

Clears all items in a category.

Format:

`clear category/CATEGORY`

Important:

* This command does not remove the category itself from InventoryDock.
* If the category is not empty, the app asks for confirmation.
* Type `yes` to continue.
* Any other reply cancels the operation.

Example:

`clear category/fruits`

Expected result:

* If confirmed, all items in the category are cleared.
* The predefined category remains available for future items.
* The app confirms the category was cleared. The category object itself is preserved.
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
* duplicate logical batch during add or update
* unknown categories or commands

When an error occurs, the app prints an error message and waits for the next command.

Error messages use a consistent format such as `[Error] Invalid input: ...`, `[Error] Missing input: ...`,
`[Error] Not found: ...`, `[Error] Conflict: ...`, or `[Error] Storage error: ...`.

## FAQ

**Q**: How do I transfer my data to another computer?

**A**: Copy the application's data file to the same location on the other computer before starting the app there.

**Q:** Where is my inventory data stored?

**A:** In `data/inventory.txt`.

**Q:** What happens if `inventory.txt` is corrupted?

**A:** InventoryDock skips corrupted lines, loads the remaining valid items, and shows which lines were skipped.

**Q:** Does the app create new categories?

**A:** No. InventoryDock works with a fixed set of built-in categories.

**Q:** Can I update category-specific boolean fields such as `isRipe/` or `isFrozen/`?

**A:** Yes. You can update the category-specific boolean field that belongs to the item's category, in addition to `newItem/`, `bin/`, `qty/`, and `expiryDate/`.

**Q:** Can item names contain spaces?

**A:** Yes. Item names can contain spaces in both `add` and `update`, and `find keyword/...` also supports multi-word phrases.

**Q:** What date format should I use?

**A:** Use `yyyy-M-d`, for example `2026-3-21`.

**Q:** What happens if I clear a category?

**A**: All items are removed, but the category itself remains in the inventory.

## Command Summary
* View help:
  `help`
* Exit the app:
  `bye`
* Add item:
  `add category/CATEGORY item/ITEM bin/BIN qty/QUANTITY expiryDate/DATE ...`
* List all items:
  `list`
* Sort items:
  `sort SORT_TYPE`
* Find items by keyword:
  `find keyword/KEYWORD_OR_PHRASE`
* Find items by category:
  `find category/CATEGORY`
* Find items by expiry date:
  `find expiryDate/DATE`
* Find items by bin:
  `find bin/BIN_INPUT`
* Find items by quantity:
  `find qty/QUANTITY`
* View inventory summary:
    `summary`
* View inventory summary by lowest stock:
  `summary stock`
* View inventory summary by earliest expiry:
  `summary expirydate`
* Update an item:
  `update category/CATEGORY index/INDEX [newItem/NEW_NAME] [bin/NEW_BIN] [qty/NEW_QUANTITY] [expiryDate/NEW_DATE]`
* Delete an item
  `delete category/CATEGORY index/INDEX`
* Clear a category
  `clear category/CATEGORY`


