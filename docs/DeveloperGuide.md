# Developer Guide

## Table of Contents

1. [Acknowledgements](#acknowledgements)
2. [Design and Implementation](#design--implementation)
   - [Exception Hierarchy](#exception-hierarchy)
   - [Add Item Feature](#add-item-feature)
   - [Find Feature](#find-feature)
     - [Find By Expiry Date](#find-by-expiry-date)
     - [Find By Category](#find-by-category)
     - [Find By Bin](#find-by-bin)
     - [Find By Quantity](#find-by-quantity)
     - [Find By Keyword](#find-by-keyword)
   - [Update Item Feature](#update-item-feature)
   - [List Feature](#list-feature)
   - [Sort Feature](#sort-feature)
   - [Storage Feature](#storage-feature)
   - [Delete Item Feature](#delete-item-feature)
   - [Delete Category Feature](#delete-category-feature)
   - [Help Feature](#help-feature)
3. [Product Scope](#product-scope)
   - [Target User Profile](#target-user-profile)
   - [Value Proposition](#value-proposition)
4. [User Stories](#user-stories)
5. [Non-Functional Requirements](#non-functional-requirements)
6. [Glossary](#glossary)
7. [Instruction for Manual Testing](#instructions-for-manual-testing)
   - [Add Item](#testing-add-item)
   - [List Item](#testing-list-command)
   - [Find by Bin](#testing-find-by-bin)
   - [Find by Quantity](#testing-find-by-quantity)
   - [Find by Category](#testing-find-by-category)
   - [Find by Expiry Date](#testing-find-by-expiry-date)
   - [Update Item](#testing-update-feature)
   - [Sort Command](#testing-sort-command)
   - [Storage Feature](#testing-storage)
   - [Delete Category](#testing-delete-category)
   - [Find by Keyword](#testing-find-by-keyword)

---


## Acknowledgements

This project is developed based on the concepts taught in CS2113. The overall architecture were inspired by
the [SE EDU AddressBook Level 3 project](https://se-education.org/addressbook-level3/).

## Design & implementation

This section describes the overall design of the application and how its main components interact.

The application follows a command-based architecture, where user input is parsed into commands that operate on the 
underlying data model. The system is structured into several key components:

- Parser: Interprets user input and constructs the appropriate command objects.
- Command: Encapsulates the logic to execute specific user operations.
- Model: Stores the inventory data, including categories and items.
- Storage: Handles reading from and writing to the storage file.
- UI: Manages all user's input and output.
  
When a user enters a command, the `Parser` interprets the input and returns a corresponding `Command` object. 
The `Command` is then executed, modifying the `Model` if necessary and delegating output to the `UI`. 
After execution, the updated state of the `Model` is saved by the `Storage` component.

This design enforces separation of concerns:
- Parsing logic is separated from execution logic.
- Data persistence is handled independently by the storage layer.

This modular structure improves maintainability and allows new features to be added with minimal impact on 
existing components.

The overall architecture of the application is shown below.

![ArchitectureDiagram](diagrams/ArchitectureDiagram.png)

### Exception Hierarchy

The application uses a shared custom exception base class, `InventoryDockException`, so the main
runtime can catch command, parsing, and storage failures through a single type while still preserving
more specific subclasses for clearer intent.

The hierarchy is now split into an overview diagram plus focused diagrams for each exception group.
This keeps the top-level inheritance structure visible without forcing every detail into a single figure.

- Overview: `ExceptionHierarchyClassDiagram.puml`
- Parser and input exceptions: `ExceptionHierarchyParserClassDiagram.puml`
- Inventory lookup exceptions: `ExceptionHierarchyInventoryClassDiagram.puml`
- Storage exceptions: `ExceptionHierarchyStorageClassDiagram.puml`

### Add Item Feature

Another core feature of the product is the ability to add an item into an existing category using
the `add` command.

This feature is necessary because the application is fundamentally an inventory manager. Users need
to record newly stocked products together with shared fields such as name, quantity, bin location,
and expiry date, while also capturing category-specific attributes such as fruit size or drink
volume. The add-item flow solves this by routing the same high-level command through specialised
parsers based on the category provided by the user.

For example, if the user enters
`add category/fruits item/apple bin/A1 qty/10 expiryDate/2026-4-01 size/medium isRipe/true`,
the system validates the common and category-specific fields, constructs the correct `Item`
subclass, and adds it into the matching category.

#### High-level design

At a high level, this enhancement also fits into the existing command-based architecture of the
application. The feature follows this flow:

1. The user enters an `add` command.
2. `Parser` recognises the `add` command word and delegates the remaining input to `AddCommandParser`.
3. `AddCommandParser` validates the required shared fields and determines the target category.
4. `AddItemCommandParser` dispatches to the category-specific parsing method such as `FruitParser` and constructs the
   correct `Item` subtype.
5. An `AddItemCommand` is created and executed with access to the current `Inventory` and `UI`.
6. The command finds the target category, inserts the item, and shows a confirmation message.

The interaction for this flow is split into focused sequence diagrams below.

1. Parse routing and category dispatch.

![AddItemCommandParseRoutingFlow](diagrams/sequence/AddItemCommandParseRoutingFlow.png)

2. Fruit parsing and command creation.

![AddItemCommandFruitParsingFlow](diagrams/sequence/AddItemCommandFruitParsingFlow.png)


3. Execution and result display.

![AddItemCommandExecutionDisplayFlow](diagrams/sequence/AddItemCommandExecutionDisplayFlow.png)

The main structural relationships for this feature are shown below.

![AddItemCommandClassDiagram](diagrams/class/AddItemCommandClassDiagram.png)

A representative object snapshot for this feature is shown below.

![AddItemCommandObjectDiagram](diagrams/object/AddItemCommandObjectDiagram.png)

This design was chosen because it preserves the same separation of responsibilities used elsewhere
in the codebase:

- `Parser` and parser helpers interpret user input.
- `AddItemCommand` performs the inventory mutation.
- Model classes such as `Inventory`, `Category`, and `Item` hold the application state.
- `UI` presents confirmation messages to the user.

As a result, adding a new item subtype does not require redesigning the command pipeline. The parser
layer can be extended category by category while the execution model remains unchanged.

#### Component-level implementation

The feature is mainly implemented using the following classes:

- `Parser`
- `AddCommandParser`
- `AddItemCommandParser`
- Category-specific parsers such as `FruitParser`
- `AddItemCommand`
- `Inventory`
- `Category`
- `Item`

The responsibilities of these classes are as follows:

- `Parser` identifies that the user wants to perform an add operation.
- `AddCommandParser` validates shared required fields and chooses the correct parsing branch based on
  `category/`.
- `AddItemCommandParser` coordinates common-field parsing and category-specific parsing.
- Category-specific parsers construct the extra fields required by each concrete `Item` subtype.
- `AddItemCommand` performs the actual insertion into the inventory.
- `Inventory` finds the matching category by name.
- `Category` stores the added item.
- `Item` and its subclasses represent the domain object being created.

This design intentionally separates shared parsing from category-specific parsing. Common fields such
as `item/`, `bin/`, `qty/`, and `expiryDate/` can be handled consistently, while subtype-specific
fields remain encapsulated in the relevant parser and model class.

#### Command execution flow

When the user enters an add command, the implementation performs the following sequence:

1. `Parser.parse()` splits the command word from the arguments.
2. `Parser` calls `AddCommandParser.parse(arguments)`.
3. `AddCommandParser` checks that mandatory fields such as `item/` and `category/` are present.
4. `AddCommandParser` extracts the category and dispatches to the corresponding method in
   `AddItemCommandParser`.
5. `AddItemCommandParser` validates the input, parses common fields, and invokes the category-specific
   parser.
6. `AddItemCommandParser` creates an `Item` subtype and wraps it in an `AddItemCommand`.
7. `InventoryDock` executes `AddItemCommand.execute(inventory, ui)`.
8. `AddItemCommand` calls `inventory.findCategoryByName(categoryName)`.
9. If the category exists, `AddItemCommand` calls `category.addItem(item)`.
10. `UI.showItemAdded(...)` displays the confirmation to the user.

The execution logic in `AddItemCommand` is intentionally small:

```java
Category category = inventory.findCategoryByName(categoryName);
category.addItem(item);
ui.showItemAdded(item.getName(), item.getQuantity(),
        category.getName(), item.getBinLocation());
```

This keeps construction concerns in the parser layer and mutation concerns in the command layer.

#### Why the feature is implemented this way

The main design choice is the use of category-based dispatch in `AddCommandParser` and
`AddItemCommandParser` instead of one very large parser or category-agnostic item builder.

This was chosen for three reasons.

First, different item types do not share the same attributes. Separating parsers by category keeps
validation rules close to the subtype that needs them.

Second, it improves maintainability. Adding support for a new category mostly requires introducing a
new parser branch and item subtype rather than modifying one monolithic parsing method with many
special cases.

Third, it keeps command execution simple. By the time `AddItemCommand` runs, all parsing and object
construction work has already been completed. The command only needs to find the category and append
the item.

Another deliberate design choice is that the command adds only into an existing category rather than
creating a missing category automatically. This keeps category creation rules explicit and avoids
silently introducing unintended categories due to typing errors.

#### Error handling and validation

Validation is split across the parser layer.

`AddCommandParser` rejects missing shared fields such as `item/` and `category/` before dispatching
to a category-specific parser. These failures are reported using `MissingArgumentException`, while an
unsupported category is reported using `InvalidCommandException`.

`AddItemCommandParser` and the specialised parsers validate category-specific input. If required
fields are missing or malformed, they throw `InventoryDockException` before an `AddItemCommand` is created.

`AddItemCommand` also performs execution-time checks. If `inventory.findCategoryByName(categoryName)`
returns `null`, the command throws a `CategoryNotFoundException` with the message
`Category not found: <categoryName>`. If the parsed item is unexpectedly `null`, it throws a
`MissingArgumentException` with the message `Item cannot be null.`

This layered approach ensures invalid input is rejected as early as possible, while still protecting
the command layer from invalid state.

#### Alternatives considered

Several alternatives were considered when implementing this feature.

Alternative 1: Use a single generic item parser for every category.

This would reduce the number of parser classes, but it was rejected because different categories have
different required fields. A single parser would become difficult to understand and maintain.

Alternative 2: Let `AddItemCommand` parse the raw command string itself.

This was rejected because it mixes input interpretation with business logic. The current design keeps
commands focused on behaviour and leaves parsing to the parser layer.

Alternative 3: Create missing categories automatically during item addition.

This was rejected because it can hide user mistakes. Requiring the target category to exist makes the
inventory structure more predictable and prevents accidental category creation due to typos.

### Find Feature

The product provides a family of `find` commands that share a common flow:

1. `FindItemParser` reads the find prefix and value.
2. The parser constructs a find-specific command.
3. The command executes against `Inventory`.
4. Results are displayed through `UI`.

This section consolidates all find subfeatures and highlights only each subfeature's unique matching logic.

Contributor acknowledgement for this Find section:
- Find by category and find by bin: Wang Chuhao.
- Find by quantity: Luke Louyu.
- Find by keyword: KOIiiii07.
- Find by expiry date: Yeo Si Zhao.

#### Find By Expiry Date

Command format: `find expiryDate/DATE`

Contributed by: Yeo Si Zhao.

Sequence diagrams:

1. Parse and command creation.

![FindItemByExpiryDateCommandParseFlow](diagrams/sequence/FindItemByExpiryDateCommandParseFlow-Sequence_Diagram_for_FindItemByExpiryDateCommand__Parse_and_Command_Creation_.png)

2. Date parsing and matching.

![FindItemByExpiryDateCommandMatchingFlow](diagrams/sequence/FindItemByExpiryDateCommandMatchingFlow-Sequence_Diagram_for_FindItemByExpiryDateCommand__Date_Parsing_and_Matching_.png)

3. Result display.

![FindItemByExpiryDateCommandDisplayFlow](diagrams/sequence/FindItemByExpiryDateCommandDisplayFlow-Sequence_Diagram_for_FindItemByExpiryDateCommand__Result_Display_.png)

Class and object diagrams:

![FindItemByExpiryDateCommandClassDiagram](diagrams/class/FindItemByExpiryDateCommandClassDiagram.png)

![FindItemByExpiryDateCommandObjectDiagram](diagrams/object/FindItemByExpiryDateCommandObjectDiagram.png)

#### Find By Category

Command format: `find category/CATEGORY`

Contributed by: Wang Chuhao.

Sequence diagrams:

1. Parse and command creation.

![FindItemByCategoryCommandParseFlow](diagrams/sequence/FindItemByCategoryCommandParseFlow.png)

2. Category lookup.

![FindItemByCategoryCommandMatchingFlow](diagrams/sequence/FindItemByCategoryCommandMatchingFlow.png)

3. Result display.

![FindItemByCategoryCommandDisplayFlow](diagrams/sequence/FindItemByCategoryCommandDisplayFlow.png)

Class and object diagrams:

![FindItemByCategoryCommandClassDiagram](diagrams/class/FindItemByCategoryCommandClassDiagram.png)

![FindItemByCategoryCommandObjectDiagram](diagrams/object/FindItemByCategoryCommandObjectDiagram.png)

#### Find By Bin

Command format: `find bin/BIN`

Contributed by: Wang Chuhao.

Sequence diagrams:

1. Parse and command creation.

![FindItemByBinCommandParseFlow](diagrams/sequence/FindItemByBinCommandParseFlow.png)

2. Inventory scan and bin matching.

![FindItemByBinCommandMatchingFlow](diagrams/sequence/FindItemByBinCommandMatchingFlow.png)

3. Result display.

![FindItemByBinCommandDisplayFlow](diagrams/sequence/FindItemByBinCommandDisplayFlow.png)

Class and object diagrams:

![FindItemByBinCommandClassDiagram](diagrams/class/FindItemByBinCommandClassDiagram.png)

![FindItemByBinCommandObjectDiagram](diagrams/object/FindItemByBinCommandObjectDiagram.png)

#### Find By Quantity

Command format: `find qty/QUANTITY`

Contributed by: Luke Louyu.

Sequence diagrams:

1. Parse and command creation.

![FindItemByQtyCommandParseFlow](diagrams/sequence/FindItemByQtyCommandParseFlow-Sequence_Diagram_for_FindItemByQtyCommand__Parse_and_Command_Creation_.png)

2. Inventory scan and quantity matching.

![FindItemByQtyCommandMatchingFlow](diagrams/sequence/FindItemByQtyCommandMatchingFlow-Sequence_Diagram_for_FindItemByQtyCommand__Inventory_Scan_and_Quantity_Matching_.png)

3. Result display.

![FindItemByQtyCommandDisplayFlow](diagrams/sequence/FindItemByQtyCommandDisplayFlow-Sequence_Diagram_for_FindItemByQtyCommand__Result_Display_.png)

Class and object diagrams:

![FindItemByQtyCommandClassDiagram](diagrams/class/FindItemByQtyCommandClassDiagram-Class_Diagram_for_FindItemByQtyCommand_Feature.png)

![FindItemByQtyCommandObjectDiagram](diagrams/object/FindItemByQtyCommandObjectDiagram-Object_Diagram_for_one_execution_of_FindItemByQtyCommand.png)

#### Find By Keyword

Command format: `find keyword/KEYWORD`

Contributed by: KOIiiii07.

Sequence diagrams:

1. Parse and command creation.

![FindItemByKeywordCommandParseFlow](diagrams/sequence/FindItemByKeywordCommandParseFlow.png)

2. Inventory scan and keyword matching.

![FindItemByKeywordCommandMatchingFlow](diagrams/sequence/FindItemByKeywordCommandMatchingFlow.png)

3. Result display.

![FindItemByKeywordCommandDisplayFlow](diagrams/sequence/FindItemByKeywordCommandDisplayFlow.png)

Class and object diagrams:

![FindItemByKeywordCommandClassDiagram](diagrams/class/FindItemByKeywordCommandClassDiagram.png)

![FindItemByKeywordCommandObjectDiagram](diagrams/object/FindItemByKeywordCommandObjectDiagram.png)

### Update Item Feature

Another core feature of the product is the ability to update an existing item in a category using the
`update` command.

This feature is necessary because inventory records may change over time. A user may need to correct an
item name, adjust its quantity, move it to a different bin location, or revise its expiry date. Without
an update operation, the user would have to delete the item and recreate it manually, which is less
efficient and more error-prone. The update-item command solves this by allowing selected fields of an
existing item to be modified directly.

For example, if the user enters  
`update category/fruits index/1 qty/20 expiryDate/2026-4-15`,  
the system locates the first item in the fruits category and updates its quantity and expiry date  
while leaving its other fields unchanged.

#### High-level design

At a high level, this feature fits into the same command-based architecture used throughout the
application. The flow is as follows:

* The user enters an `update` command.
* Parser recognises the `update` command word and delegates the remaining input to `UpdateCommandParser`.
* `UpdateCommandParser` extracts the category, item index, and the fields to be updated.
* The parser creates an `UpdateItemCommand`.
* The command is executed with access to the current `Inventory` and `UI`.
* The command locates the target category and item, applies the requested updates, and shows a
  confirmation message.

This design was chosen because it preserves the same separation of concerns already used by the rest
of the application:

* Parsers interpret user input.
* Command classes implement behaviour.
* Model classes store inventory data.
* UI displays the final result.

As a result, the update feature integrates cleanly into the existing command pipeline without requiring
a separate editing subsystem.

The main interaction for this flow is illustrated below.

![UpdateItemCommandMainFlow](diagrams/sequence/UpdateItemCommandMainFlow.png)

The main structural relationships for this feature are shown below.

![UpdateItemCommandClassDiagram](diagrams/class/UpdateItemCommandClassDiagram.png)

A representative object snapshot for this feature is shown below.

![UpdateItemCommandObjectDiagram](diagrams/object/UpdateItemCommandObjectDiagram.png)

#### Component-level implementation

The feature is mainly implemented using the following classes:

* `Parser`
* `UpdateCommandParser`
* `UpdateItemCommand`
* `Inventory`
* `Category`
* `Item`
* `CommonFieldParser`
* `UI`

The responsibilities of these classes are as follows:

* `Parser` detects the update command word and delegates to `UpdateCommandParser`.
* `UpdateCommandParser` tokenises the input, validates `category/` and `index/`, collects the updated
  fields into a `Map<String, String>`, and constructs an `UpdateItemCommand`.
* `UpdateItemCommand` locates the item and applies the requested changes.
* `Inventory` provides category lookup using `findCategoryByName(...)`.
* `Category` provides indexed item access through `getItem(...)`.
* `Item` provides setter methods such as `setName(...)`, `setQuantity(...)`, `setBinLocation(...)`,
  and `setExpiryDate(...)`.
* `CommonFieldParser` is reused for quantity and expiry-date validation so that update validation stays
  consistent with add-command validation.
* `UI` displays the result after the update is completed.

This design intentionally separates parsing from mutation. The parser determines what should be updated,
while the command is responsible for locating the correct item and applying the changes.

#### Command execution flow

When `UpdateItemCommand.execute()` is called, the implementation performs the following sequence:

* Assert that `inventory` and `ui` are not null.
* Call `inventory.findCategoryByName(categoryName)` to locate the target category.
* If the category is not found, throw a `CategoryNotFoundException`.
* Validate that the provided `itemIndex` is within the valid range for that category.
* Retrieve the target item using `category.getItem(itemIndex - 1)`.
* Store the original item name for display purposes.
* Call `applyUpdates(item)`.
* Iterate through each entry in the updates map.
* Match each field name using a switch statement.
* Apply the corresponding update to the item.
* After all updates are applied, call `ui.showItemUpdated(...)`.

The central logic is:

```java
Category category = inventory.findCategoryByName(categoryName);
if (category == null) {
   throw new CategoryNotFoundException("Category not found: " + categoryName);
}

if (itemIndex < 1 || itemIndex > category.getItemCount()) {
   throw new ItemNotFoundException(
           "Item at index " + itemIndex + " not found in category '" + categoryName + "'.");
}

Item item = category.getItem(itemIndex - 1);
String originalName = item.getName();
applyUpdates(item);
ui.showItemUpdated(originalName, item.getName(), category.getName());
```

#### Why the feature is implemented this way

The most important design choice in this feature is that the parser stores updates in a  
`Map<String, String>` rather than creating a different command class for every possible update  
combination.

This was chosen for three reasons.

* First, it keeps the parsing logic flexible. A user may update one field or several fields in a single  
  command, and a map allows the parser to capture all requested updates without requiring a separate  
  representation for every case.

* Second, it keeps the command extensible. New updatable fields can be added by extending the switch  
  statement inside `applyUpdates(...)` instead of redesigning the overall feature.

* Third, it avoids unnecessary duplication. The same update mechanism can handle name, quantity, bin, and  
  expiry date changes in one place.

Another deliberate design choice is reusing existing validation helpers such as  
`CommonFieldParser.parseQuantity(...)` and `CommonFieldParser.validateExpiryDate(...)`. This ensures  
that update commands follow the same validation rules as add commands, which improves consistency across  
the application.

#### Error handling and validation

Validation is split across the parser layer and the command layer.

`UpdateCommandParser` handles syntax-level validation. It rejects:

* empty update input  
* malformed tokens without a valid `/` separator  
* missing `category/`  
* missing `index/`  
* non-integer item indices  
* non-positive item indices  
* update commands that do not specify any fields to change  

`UpdateItemCommand` handles execution-time validation. It rejects:

* missing categories  
* invalid item indices for the chosen category  
* unsupported update fields  
* empty updated names or empty bin locations  
* invalid quantities  
* invalid expiry-date values  

This layered design ensures invalid input is rejected early, while still protecting the command layer  
from invalid runtime state.

#### Alternatives considered

Several alternatives were considered when implementing this feature.

* Alternative 1: Delete and re-add the item instead of supporting update.  
  This was rejected because it is less convenient for the user and makes small corrections unnecessarily  
  verbose.

* Alternative 2: Create a separate command for each field, such as `UpdateQuantityCommand` or  
  `UpdateExpiryDateCommand`.  
  This was rejected because it would significantly increase the number of command classes and make the  
  design more fragmented.

* Alternative 3: Allow the parser to mutate the item directly.  
  This was rejected because it breaks the separation between parsing and execution. Parsers should  
  interpret input, while commands should perform behaviour.

---

### List Feature

The product also supports displaying the current inventory using the `list` command.

This feature is important because users need a quick way to inspect the complete inventory after
adding, updating, deleting, or loading items from storage. Unlike targeted search commands, the list
operation provides a full snapshot of the current inventory state grouped by category.

For example, after a sequence of inventory changes, the user can enter `list` to review all categories
and their stored items in one output.

#### High-level design

At a high level, the feature is intentionally minimal and fits directly into the existing command
architecture:

1. The user enters a `list` command.
2. `Parser` recognises the command word and constructs a `ListCommand`.
3. `InventoryDock` executes the command with the current `Inventory` and `UI`.
4. `ListCommand` delegates rendering to `UI.showInventory(inventory)`.
5. `UI` iterates through the inventory and prints the formatted listing to the user.

The interaction for this flow is split into focused sequence diagrams below.

1. Parse routing and category dispatch.

![AddItemCommandParseRoutingFlow](diagrams/sequence/AddItemCommandParseRoutingFlow.png)

2. Fruit parsing and command creation.

![AddItemCommandFruitParsingFlow](diagrams/sequence/AddItemCommandFruitParsingFlow.png)


3. Execution and result display.

![AddItemCommandExecutionDisplayFlow](diagrams/sequence/AddItemCommandExecutionDisplayFlow.png)

The main structural relationships for this feature are shown below.

![ListCommandClassDiagram](diagrams/class/ListCommandClassDiagram.png)

A representative object snapshot for this feature is shown below.

![ListCommandObjectDiagram](diagrams/object/ListCommandObjectDiagram.png)

This design was chosen because listing inventory does not require separate parsing logic beyond
recognising the command word. The command object acts mainly as a bridge between the parser and the UI.

#### Component-level implementation

The feature is mainly implemented using the following classes:

- `Parser`
- `ListCommand`
- `Inventory`
- `Category`
- `UI`

The responsibilities of these classes are as follows:

- `Parser` detects the `list` command and returns a new `ListCommand`.
- `ListCommand` represents the list operation and triggers the display behaviour.
- `Inventory` provides access to the stored categories.
- `Category` provides the items and summary information for each category.
- `UI` formats and prints the inventory contents.

This design keeps the command itself lightweight. Since listing is a read-only operation, most of the
formatting logic appropriately lives in the UI layer instead of the command layer.

#### Command execution flow

When `ListCommand.execute()` is called, the implementation performs the following sequence:

1. Assert that `inventory` and `ui` are not `null`.
2. Log that the inventory listing is being requested.
3. Call `ui.showInventory(inventory)`.
4. Inside the UI layer, retrieve all categories from the `Inventory`.
5. Iterate through each `Category`.
6. For each category, display its name, item count, and items.
7. Print the combined inventory listing to the user.

The command logic is intentionally short:

```java
logger.log(Level.INFO, "Listing inventory.");
ui.showInventory(inventory);
```

This reflects the design decision that `ListCommand` should trigger the operation, while formatting and
presentation remain the responsibility of the UI.

#### Why the feature is implemented this way

The most important design choice here is that `ListCommand` delegates almost all work to the UI layer
instead of assembling formatted output by itself.

This was chosen for two reasons.

First, it preserves separation of concerns. The command layer decides what action should happen, while
the UI layer decides how the result should be shown.

Second, it keeps the read-only command easy to maintain. Since `list` does not modify state, there is
no need for extra model logic or intermediate data transformation in the command itself.

This also makes the command consistent with other parts of the application where `UI` is responsible
for user-facing output.

#### Error handling and validation

The `list` command has minimal input validation because it takes no arguments.

`Parser` handles recognition of the command word. Once a `ListCommand` is created, the main runtime
checks are the assertions in `ListCommand.execute()` that ensure `inventory` and `ui` are not `null`.

Because the command is read-only and does not parse additional user arguments, there are fewer failure
modes compared with commands such as `add` or `find`.

#### Alternatives considered

Several alternatives were considered when implementing this feature.

Alternative 1: Let `Parser` call `UI.showInventory(inventory)` directly without creating a command
object.

This was rejected because it breaks the existing command architecture. Keeping `ListCommand` preserves
a consistent parse-then-execute pipeline across user actions.

Alternative 2: Let `ListCommand` build a formatted string instead of delegating to `UI`.

This was rejected because presentation logic belongs more naturally in the UI layer. Mixing display
formatting into the command would weaken separation of concerns.

Alternative 3: Add filtering arguments directly to `list`.

This was rejected for now because filtered retrieval is already covered by specialised `find`
commands. Keeping `list` simple makes its behaviour predictable.

### Sort Feature

The product also supports displaying the current inventory with items sorted within each category using 
the `sort` command.

This feature is useful because users may want to inspect the inventory from a different perspective without changing 
the actual stored order of items. For example, if the user enters`sort expirydate`, the system displays all categories 
as usual, but the items inside each category are shown in ascending expiry date order, allowing the user to quickly 
identify items that are expiring soon.

#### High-level design

This feature extends the existing command-based architecture used by the product. The flow is as follows:

1. The user enters a `sort` command followed by a sort type.
2. `Parser` recognises the sort command word and delegates the argument to `SortCommandParser`.
3. `SortCommandParser` validates the sort type and creates a `SortCommand`.
4. `InventoryDock` executes the command with access to the current `Inventory` and `UI`.
5. `SortCommand` prepares the sorted view of the inventory.
6. `UI` displays the sorted inventory while preserving the group by category structure.

This design was chosen because it keeps sorting behaviour within the normal parse then execute command pipeline
used throughout the application. It also allows the feature to reuse the existing inventory display format instead of
introducing a completely separate output style.

#### Component-level implementation

The feature is mainly implemented using the following classes:

- `Parser`
- `SortCommandParser`
- `SortCommand`
- `Inventory`
- `Category`
- `Item`
- `UI`

The responsibilities these classes are as follows:

- `Parser` detects the `sort` command and delegates argument parsing to `SortCommandParser`.
- `SortCommandParser` validates the user supplied sort type and constructs the corresponding command.
- `SortCommand` performs the sorting preparation and triggers the display behaviour.
- `Inventory` provides access to the currently stored categories.
- `Category` provides access to the items stored under each category.
- `Item` provides the fields used for comparison such as name, expiry date and quantity.
- `UI` formats and prints the sorted inventory view.

This design keeps parsing, sorting and presentation responsibilities separate, The parser only interprets
the command, the command prepares the sorted result and the UI remains responsible for rendering it.

The main structural relationships for this feature are shown below.

![SortingClassDiagram](diagrams/class/SortingClassDiagram.png)

#### Command execution flow

When `SortCommand.execute()` is called the implementation performs the following sequence:

1. Assert that `inventory`, `ui` and `sortType` are not `null`.
2. Retrieve all categories from the `Inventory`.
3. For each category, make a copy of items item list.
4. Sort the copied list using the comparator that matches the requested sort type.
5. Store the sorted lists in the same category order as the original inventory.
6. Call `ui.showSortedInventory(inventory, sortedItemsByCategory, sortLabel)`.
7. UI displays the categories in their original order and prints each category's sorted item list.

This means the command does not directly modify the order of items stored inside the actual inventory. Instead, it
prepares a sorted view for display.

The sort feature can be understood as three smaller interactions: parsing the command, preparing the sorted view, 
and displaying the sorted inventory.

The parsing and command creation flow is shown below.

![SortCommandParseFlow](diagrams/sequence/SortCommandParseFlow.png)

The sorting preparation flow is shown below.

![SortCommandSortingFlow](diagrams/sequence/SortCommandSortingFlow.png)

The sorted inventory display flow is shown below.

![SortCommandDisplayFlow](diagrams/sequence/SortCommandDisplayFlow.png)

A representative object snapshot for this feature is shown below.

![SortingObjectDiagram](diagrams/object/SortingObjectDiagram.png)

#### Sorting logic

The sorting behaviour depends on the user provided sort type.

- `name`: Sorts items alphabetically by item name, ignoring letter case.
- `expirydate`: Sorts items by expiry date in ascending order, so earlier expiry dates appear first.
- `qty`: Sorts items by quantity in descending order, so larger quantities appear first.

For expiry-date sorting, the command relies on date parsing rather than raw string comparison. This is important
because proper date parsing ensures dates are compared logically rather than lexicographically.

A simplified version of the sorting approach is:

```java
List<Item> sortedItems = new ArrayList<>(category.getItems());
sortedItems.sort(getComparator());
```

The sorted item lists are then passed to the UI for display.

#### Why the feature is implemented this way

The most important design choice in this feature is that sorting is performed on copied item lists instead of
changing the order of items inside the actual inventory.

This was chosen for three reasons.

First, the feature is intended to provide an alternative view of the inventory rather than mutate the underlying data.
A user who runs `sort name` is usually asking to inspect the data in a different order, not to permanently reorder the
stored inventory.

Second, it avoids unintended side effects. If the underlying item order were modified directly, later commands that
depend on the original ordering, such as deletion or updating by index, could behave differently in ways the user
did not expect.

Third, it keeps the implementation simple and safe. By sorting copies of the item lists, the command can generate the
desired output without changing the model state.

Another deliberate design choice is that the category order is preserved. Only the items within each category are
sorted. This keeps the output structure familiar and consistent with the normal `list` command.

#### Error handling and validation

Input validation is handled mainly by `SortCommandParser`.

If the user enters `sort` without providing a sort type, the parser throws a `MissingArgumentException` indicating 
that a valid sort type is required.

If the user provides an unsupported sort type, the parser throws a `InvalidCommandException` listing the valid options,
such as `name`, `expirydate`, and `qty`.

At execution time, the command handles an empty inventory gracefully. The UI displays the appropriate empty inventory
message instead of failing.

#### Alternatives considered

Several alternatives were considered when implementing this feature.

Alternative 1: Permanently reorder items inside each category.

This was rejected because the sort command is intended as a display oriented feature rather than a data mutation
feature. Permanently changing the stored order could make other index based commands less predictable.

Alternative 2: Extend the `list` command to accept optional sorting arguments.

This was rejected because it would complicate the behaviour of `list`, which is currently simple and predictable.
Keeping `sort` as a separate command makes each command’s purpose clearer.

Alternative 3: Sort both categories and items.

This was rejected because the primary user need is to inspect items within each category more easily. Reordering
categories as well would make the output less consistent with the rest of the application.

### Storage feature

This product includes a storage component that is responsible for persisting inventory data
between application runs.

This component is necessary because the inventory should not be lost when the program exits, as
users expect their items to remain available the next time they launch the application.

The storage feature solves this by writing the current inventory to a file and reconstructing it
when the application starts again.

#### Implementation

The storage mechanism is facilitated by `Storage`, which is responsible for saving the current `Inventory`
to file and loading it back into memory.

It supports two main operations:

- `save(Inventory inventory)` which writes the current inventory to the storage file.
- `load(Inventory inventory, UI ui)` which reads the storage file and reconstructs the inventory state.

The save format is text-based. Each item is stored on a separate line together with its common fields and any
additional fields required by its specific category. This allows all supported item types to be saved in a
single format while preserving the extra data needed for each subtype.

The main structural relationships for the storage feature are shown below.

![StorageClassDiagram](diagrams/class/StorageClassDiagram.png)

#### Saving execution flow

When the application saves, `Storage` performs the following sequence:
1. Open the data file for writing.
2. Retrieve all categories from the inventory.
3. Iterate through each `Category`.
4. Within each category, iterate through each `Item`.
5. Convert each item into a formatted text line.
6. Write the formatted line into the file.
7. Repeat until all items have been written.
8. Close the file.

The formatting logic is delegated to the `Item` class via polymorphism, instead of being centralized
in the `Storage` class.

A simplified example of the base formatting logic is:
```java
public String toStorageString(String categoryName) {
    return "category/" + categoryName
            + " item/" + name
            + " bin/" + binLocation
            + " qty/" + quantity
            + " expiryDate/" + expiryDate;
}
```

Subclasses extend this behaviour by appending their own fields. For example, the `Fruit` class
adds additional attributes:
```java
public String toStorageString(String categoryName) {
    return super.toStorageString(categoryName)
            + " size/" + size
            + " isRipe/" + isRipe;
}
```

This design ensures that each subclass is responsible for serializing its own data,
while the `Storage` class remains independent of specific item types.

The main interaction for this flow is illustrated below.

![StorageSavingMainFlow](diagrams/sequence/StorageSavingMainFlow.png)

A representative object snapshot for the storage loading workflow is shown below.

![StorageSavingObjectDiagram](diagrams/object/StorageSavingObjectDiagram.png)

#### Loading execution flow

When the application loads data from file, `Storage` performs the following sequence:
1. Ensure the storage file exists. If not, it is created automatically.
2. Read the file line by line.
3. Extract the category from the line.
4. Use the category to determine the appropriate parsing method.
5. Convert the line into a `Command` using `AddItemCommandParser`.
6. Execute the command to reconstruct the item in the inventory.
7. Skip malformed lines where appropriate.
8. Continue until the entire file has been processed.

This design allows the application to reconstruct the same logical inventory state from the saved text data. 
This approach reuses existing parsing logic, ensuring consistency between user input handling and
stored data reconstruction.

The main interaction for this flow is illustrated below.

![StorageLoadingMainFlow](diagrams/sequence/StorageLoadingMainFlow.png)

A representative object snapshot for the storage loading workflow is shown below.

![StorageLoadingObjectDiagram](diagrams/object/StorageLoadingObjectDiagram.png)

#### Error handling and validation

The storage component also handles cases where the save file is missing, or contains invalid data.

If the file does not exist, the application can start with an empty inventory instead of
crashing. This is because the absence of a save file may simply mean that the program is being
run for the first time.

If a line is malformed, the exception is caught and the line is skipped. The user is informed via the UI, detailing 
the line that was skipped and the reason for skipping.

#### Why the storage component is implemented this way

The simple text-based format is chosen instead of a more complex format such as JSON or database
for several reasons.

First, this keeps the implementation lightweight. The project does not require any external
libraries or database setup, which makes the application easier to develop and test.

Second, the saved data is readable which is useful during debugging because we can inspect the
contents of the file directly and verify whether items are being written correctly.

#### Alternatives considered

Alternative 1: Store each category in a separate file.

This could improve file organization, especially if categories become large. It was rejected because it would 
make file management more complicated and require coordinating multiple saved files instead of just one.

Alternative 2: Use JSON format.

This would make the file structure more standardized. However, it was rejected because it would
introduce additional complexity which is unnecessary for our project scope.

### Delete Feature

The product provides two `delete` commands that share a common parser flow:

1. `Parser` recognises the `delete` command word and delegates to `DeleteCommandParser`.
2. `DeleteCommandParser` extracts the `category/` field and checks for an `index/` field.
3. If `index/` is present, a `DeleteItemCommand` is created. Otherwise, a `DeleteCategoryCommand` is created.
4. The command executes against `Inventory` and displays results through `UI`.

This section covers both subfeatures and highlights their unique behaviour.

#### Delete Item

Command format: `delete category/CATEGORY index/INDEX`

Removes a single item at the given 1-based index from the specified category. The command looks up the category via `inventory.findCategoryByName(...)`, validates the index range, retrieves the item with `category.getItem(itemIndex - 1)`, removes it with `category.removeItem(...)`, and shows a confirmation message.

Validation is layered: `DeleteCommandParser` rejects missing fields, non-integer indices, and non-positive integers at parse time. `DeleteItemCommand` catches non-existent categories and out-of-range indices at execution time.

Sequence diagram:

![DeleteItemCommandMainFlow](diagrams/sequence/DeleteItemCommandMainFlow.png)

Class and object diagrams:

![DeleteItemCommandClassDiagram](diagrams/class/DeleteItemCommandClassDiagram.png)

![DeleteItemCommandObjectDiagram](diagrams/object/DeleteItemCommandObjectDiagram.png)

#### Delete Category

Command format: `delete category/CATEGORY`

Clears all items within the specified category. Because this is a higher-risk operation, the command prompts the user for confirmation (must type `yes`, case-insensitive) before proceeding. If the category is already empty, the prompt is skipped.

The core confirmation logic:

```java
if (!category.isEmpty()) {
    ui.showDeleteCategoryConfirmation(categoryName, category.getItemCount());
    String response = ui.readCommand();

    if (response == null || !response.trim().equalsIgnoreCase("yes")) {
        ui.showDeleteCategoryCancelled(categoryName);
        return;
    }

    category.getItems().clear();
    ui.showCategoryItemsCleared(categoryName);
}
```

Sequence diagram:

![DeleteCategoryCommandMainFlow](diagrams/sequence/DeleteCategoryCommandMainFlow.png)

Class and object diagrams:

![DeleteCategoryCommandClassDiagram](diagrams/class/DeleteCategoryCommandClassDiagram.png)

![DeleteCategoryCommandObjectDiagram](diagrams/object/DeleteCategoryCommandObjectDiagram.png)

#### Design decisions

- **Index-based deletion** was chosen over name-based deletion because multiple items can share the same name.
- **Confirmation prompt** is used only for category clearing (high-risk), not for single-item deletion (low-risk, easily reversible).
- **Category clearing** removes items but preserves the category object, since categories are predefined.
- The `delete` command word is reused for both operations; the parser distinguishes them by the presence of `index/`.

### Help Feature

Command format: `help`

Displays a summary of available commands and a link to the User Guide. The command is intentionally minimal: `HelpCommand.execute()` delegates entirely to `ui.showHelp()`, which prints the command list and URL.

This design keeps help output concise and avoids duplicating detailed usage that already exists in the User Guide. The command summary is currently hard-coded in `UI.showHelp()`.

Sequence diagram:

![HelpCommandMainFlow](diagrams/sequence/HelpCommandMainFlow-Sequence_Diagram_for_HelpCommand__Main_Control_Flow_Only_.png)

Class and object diagrams:

![HelpCommandClassDiagram](diagrams/class/HelpCommandClassDiagram.png)

![HelpCommandObjectDiagram](diagrams/object/HelpCommandObjectDiagram.png)

A possible future improvement is supporting `help COMMAND` to show detailed usage for a specific command.

## Product scope
### Target user profile

- Users who need to manage categorized inventory items
- Users who are comfortable using a Command Line Interface (CLI)
- Users who want to track item details such as quantity, location, and expiry date
- Store managers or individuals managing physical storage systems

### Value proposition

InventoryDock is a CLI-based inventory management system that allows users to efficiently manage categorized items 
with attributes such as bin location, quantity, and expiry date. It provides fast command-based operations for adding, 
updating, deleting, and searching items, enabling users to quickly locate and manage inventory without navigating 
complex interfaces.

## User Stories

| Version | As a ... | I want to ...             | So that I can ...                                           |
|---------|----------|---------------------------|-------------------------------------------------------------|
| v1.0    | new user | see usage instructions    | refer to them when I forget how to use the application      |
| v1.0    | user     | add items                 | track inventory                                             |
| v1.0    | user     | delete items              | remove outdated or incorrect entries                        |
| v1.0    | user     | list items                | view all inventory at once                                  |
| v2.0    | user     | find items by keyword     | locate an item without having to go through the entire list |
| v2.0    | user     | find items by category    | find items in a particular category                         |
| v2.0    | user     | find items by bin         | find items based on storage location                        |
| v2.0    | user     | find items by expiry date | identify items expiring soon                                |
| v2.0    | user     | update items              | correct or modify item details                              |

## Non-Functional Requirements

1. The application should run on any system with Java 17 or above installed.
2. The system should handle small to moderate inventory sizes efficiently using linear scans.
3. The application should persist data between sessions using file storage.
4. The system should provide clear error messages for invalid user inputs.
5. The application should be usable via a Command Line Interface.
6. The system should not crash when encountering malformed storage data, and should handle such cases.

## Glossary

* *Item* - A unit stored in the inventory with attributes such as name, quantity, and expiry date.
* *Category* - A grouping of items within the inventory.
* *Bin* - A physical storage location identifier (e.g., A-10).
* *Inventory* - The overall collection of categories and items managed by the system.
* *Command* - A user input instruction that triggers an operation in the application.

## Instructions for manual testing

This section provides instructions for manually testing the application.

### Launching the application

1. Ensure that Java 17 or above is installed on your system.
2. Compile and run the `InventoryDock` class.
3. Verify that the application starts successfully and displays the welcome message.

### Adding sample data

1. Use the `add` command to insert sample items into different categories.
2. Example:
    - `add category/fruits item/apple bin/A1 qty/10 expiryDate/2026-4-01 size/medium isRipe/true`
    - `add category/drinks item/cola bin/B2 qty/5 expiryDate/2026-6-01 brand/coke flavour/original isCarbonated/true`
3. Run `list` to verify that the items are correctly added.

After setting up the application, proceed to the individual test cases below.

### Testing add item

1. Ensure the target category already exists in the inventory, for example `fruits`.
2. Run `add category/fruits item/apple bin/A1 qty/10 expiryDate/2026-4-01 size/medium isRipe/true`.
3. Verify that the application shows a confirmation message for the added item.
4. Run `list`.
5. Verify that `apple` appears under the `fruits` category with the entered values.
6. Run `add category/unknown item/apple bin/A1 qty/10 expiryDate/2026-4-01 size/medium isRipe/true`.
7. Verify that the application shows `Category not found: unknown` or the corresponding category error.
8. Run an add command with a missing required field, for example `add category/fruits bin/A1 qty/10 expiryDate/2026-4-01 size/medium isRipe/true`.
9. Verify that the application shows the appropriate validation error for the missing field.

### Testing list command

1. Start the application with at least one category containing items.
2. Run `list`.
3. Verify that the application displays the full inventory grouped by category.
4. Start the application with an empty inventory.
5. Run `list`.
6. Verify that the application still handles the command successfully and shows the inventory view for the empty state.

### Testing find by bin

1. Add items with bin locations such as `A-1`, `A-10`, and `B-10`.
2. Run `find bin/A-1`.
3. Verify that only items in bin `A-1` are shown, and items in `A-10` are not included.
4. Run `find bin/A`.
5. Verify that all items with matching bin letter `A` are shown.
6. Run `find bin/10`.
7. Verify that items in bins such as `A-10` and `B-10` are shown.
8. Run `find bin/Z`.
9. Verify that the application shows `No items found in bin location: z.` or the corresponding no-match message.

### Testing find by quantity

1. Add items with different quantities, for example `5`, `10`, `15`, and `30`.
2. Run `find qty/10`.
3. Verify that only items with quantity `10` or lower are shown.
4. Run `find qty/15`.
5. Verify that items with quantity `15` and lower are shown, and items above `15` are excluded.
6. Run `find qty/abc`.
7. Verify that the application shows the invalid quantity error.
8. Run `find qty/5` when no item has quantity `5` or lower.
9. Verify that the application shows `No items found with quantity: 5.` or the corresponding no-match message.

### Testing find by category

1. Ensure the inventory contains a non-empty category such as `fruits` and an empty category if available.
2. Run `find category/fruits`.
3. Verify that the application shows the items stored in `fruits`.
4. Run `find category/FRUITS`.
5. Verify that the application still returns the items in `fruits`.
6. Run `find category/snacks` for an existing but empty category.
7. Verify that the application shows `No items found in category: snacks.` or the corresponding empty-category message.
8. Run `find category/toiletries` for a category that does not exist.
9. Verify that the application shows the category-not-found message.

### Testing find by expiry date

1. Add items with different expiry dates.
2. Run `find expiryDate/2026-3-25`.
3. Verify that only items expiring on or before `2026-3-25` are shown.
4. Run `find expiryDate/2026-3-01`.
5. Verify that the application shows `No items found expiring by 2026-3-01.` when there are no matches.
6. Run `find expiryDate/2026/3/25`.
7. Verify that the application shows the invalid date format error.
8. Run `find expiryDate/`.
9. Verify that the application shows the missing expiry date error.

### Testing update feature

1. Run `update category/fruits index/1 newItem/green_apple bin/A2 expiryDate/2026-5-01`
2. Verify that:
  * The item name is updated to green_apple  
  * The bin location is updated to A2  
  * The expiry date is updated to 2026-5-01  
3. Run `list`.
4. Verify that all updated fields are reflected correctly.
5. Run `update category/unknown index/1 qty/10`
6. Verify that the application shows  `Category not found: unknown`.
7. Run `update category/fruits index/100 qty/10`
8. Verify that the application shows an error indicating the index is out of range.
9. Run `update category/fruits index/abc qty/10`
10. Verify that the application shows `Item index must be an integer.`
11. Run `update category/fruits index/1`
12. Verify that the application shows `Provide at least one field to update.`
13. Run `update index/1 qty/10`
14. Verify that the application shows `Missing category.`
15. Run `update category/fruits qty/10`
16. Verify that the application shows `Missing item index.`
17. Run `update category/fruits index/1 qty/-5`
18. Verify that the application shows `Quantity must be a positive integer.`
19. Run `update category/fruits index/1 expiryDate/2026/05/01`
20. Verify that the application shows `Invalid date. Please use yyyy-M-d.`
21. Run `update category/fruits index/1 bin/`
22. Verify that the application shows `Invalid update token: bin/`.
23. Run `update category/fruits index/1 size/large`
24. Verify that the application shows `Only newItem/, bin/, qty/, and expiryDate/ can be updated.`

### Testing Sort Command
1. Add several items into at least one category with different names, expiry dates, and quantities.
2. Run `sort name`.
3. Verify that items within each category are shown in alphabetical order by name.
4. Run `sort expirydate`.
5. Verify that items within each category are shown from earliest to latest expiry date.
6. Run `sort qty`.
7. Verify that items within each category are shown from highest to lowest quantity.
8. Run `sort invalidType`.
9. Verify that the application shows the appropriate invalid sort type error message.
10. Run `sort` with no argument.
11. Verify that the application shows the missing sort type error message.
12. Run the command on an empty inventory.
13. Verify that the application handles it without crashing and displays the appropriate empty state.

### Testing storage

1. Add several items to the inventory.
2. Exit the application using the `bye` command.
3. Reopen the application.
4. Run `list`.
5. Verify that all items are restored with their correct category specific fields.
6. Exit the application using the `bye` command.
7. Modify the storage file so that a line is missing the `category/` field.
8. Reopen the application.
9. Verify that the application skips the malformed line and displays the appropriate error message.
10. Run `list`.
11. Verify that the remaining valid items are loaded correctly.
12. Exit the application using the `bye` command.
13. Delete the storage file before launching the application.
14. Verify that the application recreates the file automatically and starts without crashing.

### Testing delete category

1. Ensure the inventory contains a non-empty category such as `fruits`.
2. Run `delete category/fruits`.
3. Verify that the application shows a confirmation prompt with the item count.
4. Type `yes` and press enter.
5. Verify that the application shows a confirmation message indicating the category was cleared.
6. Run `list`.
7. Verify that the `fruits` category is now empty.
8. Run `delete category/unknown`.
9. Verify that the application shows the category-not-found error.
10. Add items back to `fruits`, then run `delete category/fruits`.
11. Type `no` and press enter.
12. Verify that the category is not cleared.

### Testing find by keyword

1. Add items with overlapping names such as `apple`, `pineapple`, and `apple_juice` across
   different categories.
2. Run `find keyword/apple`.
3. Verify that all items containing `apple` are shown, regardless of category.
4. Run `find keyword/APPLE`.
5. Verify that the search is case-insensitive and returns the same results.
6. Run `find keyword/chip`.
7. Verify that partial matches such as `chips` are returned.
8. Run `find keyword/mango`.
9. Verify that the application shows `No items found matching keyword: mango.` when there are no
   matches.
