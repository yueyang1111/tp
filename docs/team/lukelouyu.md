# Luke Louyu - Project Portfolio Page

## Overview

InventoryDock is a Java-based command-line inventory management application designed for users who prefer fast and structured keyboard input. The application allows users to organise inventory by category, record key item details such as quantity, bin location, and expiry date, and manage stored items through commands such as `add`, `update`, and `find`.


### Summary of Contributions

My main contributions focused on extending the project’s category-based inventory support and strengthening the command-processing flow for newly added item types. In particular, I implemented and refined category-specific item classes, built and integrated category-specific parsers for structured user input, and contributed to the supporting inventory model through `Category` and `Inventory`. I also contributed test code, JavaDocs, and style-compliance fixes, helping make the codebase more modular, more robust against invalid input, and easier to maintain.

### Code contributed ###

- [Code contribution dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=w09&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=lukelouyu&tabRepo=AY2526S2-CS2113-W09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)




### Extended the item model with category-specific item classes

I implemented the item subclasses under `src/main/java/seedu/duke/model/items` to support different inventory categories:

- `Accessories.java`
- `Burger.java`
- `Drinks.java`
- `Fruit.java`
- `IceCream.java`
- `Meat.java`
- `PetFood.java`
- `Seafood.java`
- `SetMeal.java`
- `Snack.java`
- `Sweets.java`
- `Toiletries.java`
- `Vegetable.java`

These classes extend the base `Item` class and allow each category to store its own category-specific attributes. Each subclass includes constructors, getters, and setters, as well as methods for converting objects into user-friendly display strings and storage-friendly string formats. This improves the expressiveness of the inventory system by allowing different item types to be represented more meaningfully instead of relying on a single generic item structure.

### Implemented the core inventory model structure

I contributed to the implementation of the core inventory model through `Category.java` and `Inventory.java`.

`Category.java` represents a single item category in the system. It stores the category name and a list of items, and provides methods to add, remove, search, count, and display items within that category.

`Inventory.java` acts as the top-level manager for the full inventory. It stores the list of categories and provides methods to add, retrieve, search, count, and display categories. Together, these classes establish the hierarchical structure of the inventory system:

`Inventory -> Category -> Item`

This improves the organisation of the data model and supports a clearer separation of responsibilities across the codebase.

### Implemented category-specific parsers for structured item input

I implemented the category-specific parsers under `src/main/java/seedu/duke/parser/category` to support parsing and validation of item-type-specific fields:

- `AccessoriesParser.java`
- `BurgerParser.java`
- `DrinksParser.java`
- `FruitParser.java`
- `IceCreamParser.java`
- `MeatParser.java`
- `PetFoodParser.java`
- `SeafoodParser.java`
- `SnackParser.java`
- `SweetsParser.java`
- `ToiletriesParser.java`
- `VegetableParser.java`

These parser classes are responsible for extracting category-specific fields from raw user input, validating required values, detecting malformed input, and returning structured parsed results for item creation. This modularises the parsing logic by keeping category-specific validation separate from the main command parser, which improves maintainability and makes the application easier to extend with new inventory item types in the future.

### Updated `AddItemCommandParser` to support the new categories

I updated `AddItemCommandParser.java` to integrate the category-specific parsers into the item-creation workflow. This included adding category-handling methods such as:

- `handleDrinks(String input)`
- `handleIceCream(String input)`
- `handleSweets(String input)`
- `handleSetMeal(String input)`
- `handleSeafood(String input)`
- `handleMeat(String input)`
- `handlePetFood(String input)`
- `handleAccessories(String input)`
- `handleBurger(String input)`

These methods allow the system to correctly parse category-dependent input and construct the corresponding item objects. This improves the extensibility of the add-command flow and ensures that category-specific item creation is handled in a structured and consistent way.

### Improved code quality and maintainability

In addition to implementation work, I contributed to improving the overall quality of the codebase through testing, JavaDocs, and style-related fixes. This helped ensure that the new inventory features were easier to understand, easier to maintain, and better aligned with project coding standards.

