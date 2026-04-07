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
* If the inventory is empty, the app shows `Inventory is empty.`

### Sorting items: `sort`
Lists the full inventory grouped by category, with the items inside each category sorted by the chosen field.

Format: `sort SORT_TYPE`

Valid `SORT_TYPE` values:
* `name`: Sorts items alphabetically by item name, ignoring letter case
* `expirydate` : Sort items by expiry date, from earliest to latest
* `qty` : Sorts items by quantity, from highest to lowest