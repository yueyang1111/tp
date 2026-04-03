# Developer Guide

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

### Find Item By Expiry Date Feature

One enhancement added to the product is the ability to find items by expiry date using the command
`find expiryDate/DATE`.

This feature was introduced because the inventory is centered around storing physical goods, many of
which are perishable or time-sensitive. In this context, searching by name alone is not sufficient.
A user may know that some products are expiring soon, but may not remember the exact names or which
category they were placed under. The expiry-date search solves this by letting the user enter a cutoff
date and retrieve every item expiring on or before that date.

For example, if the user enters `find expiryDate/2026-3-25`, the system returns all items with expiry
dates earlier than `2026-3-25` as well as items expiring exactly on `2026-3-25`.

#### High-level design

At a high level, this enhancement reuses the existing command-based architecture of the application.
The feature fits naturally into the existing flow:

1. The user enters a `find` command.
2. `FindItemParser` inspects the prefix after `find`.
3. If the prefix is `expiryDate/`, the parser creates a `FindItemByExpiryDateCommand`.
4. The command is executed with access to the current `Inventory` and `UI`.
5. The command scans the inventory and prints the matching results.

This design was chosen because it follows the same separation of concerns already used in the project:

- Parsers are responsible for interpreting user input.
- Command classes are responsible for application behaviour.
- Model classes store inventory data.
- `UI` is responsible for displaying the result to the user.

As a result, the new enhancement could be added without changing the overall architecture of the system.
It is an extension of the existing command pipeline rather than a separate subsystem.

#### Component-level implementation

The feature is mainly implemented using the following classes:

- `FindItemParser`
- `FindItemByExpiryDateCommand`
- `DateParser`
- `Inventory`
- `Category`
- `Item`

The responsibilities of these classes are as follows:

- `FindItemParser` recognises that the user wants to perform a search based on expiry date.
- `FindItemByExpiryDateCommand` performs the actual search logic.
- `DateParser` ensures that the provided date is valid and converts it into a `LocalDate`.
- `Inventory` exposes the list of categories currently stored.
- `Category` exposes the list of items belonging to that category.
- `Item` provides each stored item's expiry date.

The parser logic is intentionally simple. After splitting the input around the first `/`, it checks the
left-hand side to determine what type of find command the user requested. If the type is `expirydate`,
it returns a new `FindItemByExpiryDateCommand`.

This means the parser does not perform the date comparison itself. Instead, it only constructs the
correct command object. This keeps parsing logic lightweight and pushes business logic into the command
layer, where it belongs.

#### Command execution flow

When `FindItemByExpiryDateCommand.execute()` is called, the implementation performs the following sequence:

1. Assert that `inventory`, `ui`, and `expiryDateInput` are not `null`.
2. Convert the user-supplied date into a `LocalDate` by calling `DateParser.parseDate(expiryDateInput)`.
3. Create an empty `List<String>` named `matches` to store formatted search results.
4. Retrieve all categories from the `Inventory`.
5. Iterate through each `Category`.
6. Within each category, iterate through each `Item`.
7. Read the item's expiry date using `item.getExpiryDate()`.
8. Skip that item if the expiry date is `null`.
9. Parse the item's expiry date into a `LocalDate`.
10. Compare the item's expiry date with the user-provided cutoff date.
11. If the item date is not after the cutoff date, add a formatted result string to `matches`.
12. After the scan is complete, either:
13. Display a "no items found" message if `matches` is empty, or
14. Print the list of matching items with numbering and dividers.

The key comparison is:

```java
if (!itemDate.isAfter(cutoffDate)) {
    matches.add(category.getName() + ": " + item);
}
```

This logic means the search is inclusive. In other words, if an item expires on the exact cutoff date,
it is still considered a match. This is the intended behaviour because users searching for items expiring
"by" a certain date usually expect items on that date to be included.

#### Why the feature is implemented this way

The most important design choice in this enhancement is that the command performs a full scan of the
inventory instead of relying on a precomputed data structure such as a sorted list, map, or expiry-date
index.

This was chosen for three reasons.

First, it keeps the implementation small and easy to reason about. The inventory is already organized by
category, and each category already stores its own list of items. Reusing that structure avoids adding
new state that must be updated every time an item is added, deleted, or edited.

Second, it reduces the risk of inconsistency. If an additional expiry-date index were introduced, the
application would need to ensure that every mutation to the inventory also updates the index correctly.
For a student project, the simpler design is more robust because there are fewer moving parts that can
fall out of sync.

Third, the expected inventory size is modest. A linear scan is acceptable for the current scale of the
application. The extra complexity of a specialised indexing structure is not justified unless the number
of items becomes large enough for search performance to become a real bottleneck.

Another deliberate design decision is the use of `LocalDate` instead of comparing raw strings.
Even though the input format looks sortable, relying on proper date parsing is safer and more maintainable.
It guarantees that invalid dates are rejected early and ensures that comparisons remain logically correct.

The implementation also ignores items whose expiry date is `null`. This was done to make the command
resilient when scanning heterogeneous inventory data. Some items may not have expiry dates populated, and
those items should not cause the search to fail. Instead, they are excluded from the result set because
they do not carry the attribute being searched on.

#### Error handling and validation

Input validation is handled mainly by `DateParser`.

If the user provides a missing or blank expiry date, `DateParser` throws a `DukeException` with the
message `Missing expiry date`.

If the user provides an invalid format such as `2026/3/25`, `DateParser` throws a `DukeException` with
the message `Invalid date. Please use yyyy-M-d.`

This design centralises date validation in one place instead of duplicating date checks across multiple
commands. That improves consistency and makes future maintenance easier. If the date format requirement
changes later, only `DateParser` needs to be updated.

The parser also handles malformed `find` commands before the command object is created. For example, if
the user enters `find expiryDate/` with no value after the slash, `FindItemParser` identifies the missing
argument and reports invalid input to the UI.

#### Alternatives considered

Several alternatives were considered when implementing this enhancement.

Alternative 1: Compare expiry dates as strings.

This approach would have been shorter to implement because it avoids converting strings into `LocalDate`
objects. However, it was rejected because string comparison is more fragile and tightly coupled to the
exact formatting of the stored dates. Using `LocalDate` gives stronger correctness guarantees and clearer
intent.

Alternative 2: Store items in a separate expiry-date index.

This approach could improve search performance by making lookups faster than a full scan, especially for
larger inventories. It was rejected for now because it adds extra complexity to the data model. Every
operation that modifies the inventory would also need to update the index, which increases implementation
effort and introduces more opportunities for bugs.

Alternative 3: Restrict expiry-date search to a single category at a time.

This approach could reduce the scope of each search and align with the current category-based inventory
organisation. It was rejected because it weakens the usefulness of the feature. Users usually care about
which items are expiring soon across the entire inventory, not within one specific category.

Alternative 4: Return only items expiring exactly on the given date.

This would have made the search condition simpler and more literal. It was rejected because the use case
is broader than exact-date matching. A cutoff-based search is more practical for inventory review and
waste prevention because it supports questions like "Which items expire by the end of this week?"

#### Current limitations

The current implementation has some limitations.

- It performs a full scan of the inventory each time the command is run.
- It returns formatted strings rather than a structured result object.
- It depends on expiry dates being stored in a valid format.
- It does not sort the output by earliest expiry date; results follow the current category and item order.

These limitations are acceptable for the current scope, but they identify possible directions for future
enhancement.

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

- Sort matching items by expiry date before displaying them.
- Highlight items that are already expired separately from items that are merely approaching expiry.
- Support date ranges such as `find expiryDate/2026-3-01 to/2026-3-25`.
- Add category filters so users can combine expiry search with category search.
- Introduce an internal index if inventory size grows enough to justify optimisation.

### Find Item By Category Feature

Another search feature in the product is the ability to find items by category using the command
`find category/CATEGORY`.

This feature is useful because the inventory is organised around categories, and users often want to
review all items stored within one category without scanning the entire inventory. In practice, this
supports tasks such as checking what is currently stored under `fruits`, confirming that a category is
empty, or verifying whether a category exists at all.

For example, if the user enters `find category/fruits`, the system locates the `fruits` category and
shows all items currently stored in it.

#### High-level design

At a high level, this feature reuses the existing command pipeline of the application. The flow is as
follows:

1. The user enters a `find` command.
2. `FindItemParser` inspects the prefix before the `/`.
3. If the prefix is `category`, the parser creates a `FindItemByCategoryCommand`.
4. The command is executed with access to the current `Inventory` and `UI`.
5. The command attempts to locate the matching category and displays either the items or an
   appropriate message.

The main interaction for this flow is illustrated in [FindItemByCategoryCommandMainFlow.png](diagrams/FindItemByCategoryCommandMainFlow-Sequence_Diagram_for_FindItemByCategoryCommand__Main_Control_Flow_Only_.png).

This design was chosen because it follows the same separation of concerns already used throughout the
project:

- Parsers interpret user input.
- Command classes implement the application behaviour.
- Model classes store inventory data.
- `UI` is responsible for displaying the result to the user.

As a result, the category-search feature integrates cleanly into the existing architecture instead of
requiring a separate retrieval subsystem.

#### Component-level implementation

The feature is mainly implemented using the following classes:

- `FindItemParser`
- `FindItemByCategoryCommand`
- `Inventory`
- `Category`
- `Item`

The responsibilities of these classes are as follows:

- `FindItemParser` recognises that the user wants to search by category.
- `FindItemByCategoryCommand` performs the lookup and prepares the result for display.
- `Inventory` exposes a category lookup through `findCategoryByName(...)`.
- `Category` exposes its stored items and name.
- `Item` provides the item data shown in the final output.

The parser logic remains intentionally small. It only determines the requested find type and creates
an appropriate command object. The actual lookup is performed inside the command layer.

#### Command execution flow

When `FindItemByCategoryCommand.execute()` is called, the implementation performs the following
sequence:

1. Assert that `inventory`, `ui`, and `categoryInput` are not `null`.
2. Call `inventory.findCategoryByName(categoryInput)`.
3. If no category is found, call `ui.showCategoryNotFound(categoryInput)` and return.
4. Retrieve the items from the matched category using `matched.getItems()`.
5. If the item list is empty, display a no-items-found message for that category.
6. Otherwise, display dividers, a heading, and the numbered item list.

The core lookup logic is:

```java
Category matched = inventory.findCategoryByName(categoryInput);

if (matched == null) {
    ui.showCategoryNotFound(categoryInput);
    return;
}
```

This keeps the command focused on one responsibility: resolve the category, then display the result
based on whether the category exists and whether it contains items.

#### Why the feature is implemented this way

The most important design choice in this feature is that category search is implemented as a direct
category lookup instead of a full scan that compares every item's category name individually.

This was chosen for three reasons.

First, category is already a first-class concept in the data model. The inventory stores items under
`Category` objects, so searching by category should begin from that structure rather than reconstruct
it indirectly from the items.

Second, it keeps the implementation small and readable. Once the category is found, the command can
immediately retrieve its item list and display it.

Third, it gives clearer user feedback. The command can distinguish between an existing category with no
items and a category that does not exist at all. That distinction is useful in practice and would be
harder to express cleanly if the implementation only scanned items globally.

Another deliberate design choice is that category matching is delegated to `Inventory.findCategoryByName(...)`.
This centralises the lookup logic in one place and avoids duplicating case-handling behaviour across
multiple commands.

#### Error handling and validation

Input validation is handled mainly by `FindItemParser`.

If the user enters `find` with no target, the parser throws a `DukeException` explaining the supported
find formats.

If the user enters `find category/` with no value after the slash, the parser throws a `DukeException`
for the missing name before any command object is created.

At execution time, `FindItemByCategoryCommand` handles two normal non-error outcomes explicitly:

- If the category does not exist, `UI.showCategoryNotFound(...)` is used.
- If the category exists but contains no items, the command shows `No items found in category: ...`.

This makes the feature robust without treating common user situations as fatal runtime failures.

#### Alternatives considered

Several alternatives were considered when implementing this feature.

Alternative 1: Scan every item in every category and collect items whose parent category name matches
the input.

This was rejected because the data model already stores items under categories directly. Reusing the
existing category lookup is simpler and more coherent.

Alternative 2: Return an error whenever the matched category is empty.

This was rejected because an empty category is still a valid category state. Reporting it separately as
"no items found" is more accurate and more helpful to the user.

Alternative 3: Make category search case-sensitive.

This was rejected because users should not need to remember the exact letter casing used internally.
Case-insensitive matching produces a more forgiving command experience.

#### Current limitations

The current implementation has some limitations.

- It returns items in the existing order stored in the category and does not apply sorting.
- It depends on `Inventory.findCategoryByName(...)` for lookup semantics.
- It only supports searching one category at a time.

These limitations are acceptable for the current scope, but they indicate possible future extensions.

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

- Support partial category-name matching or suggestions for close matches.
- Allow additional filtering within a category, such as by expiry date or bin location.
- Support sorted output within the category listing.

### Find Item By Bin Feature

Another search feature in the product is the ability to find items by bin location using the command
`find bin/BIN`.

This feature is useful because physical inventory retrieval often starts from storage location rather
than item name. A user may know that they need to inspect everything in bin `A-1`, or they may only
remember the bin letter or number. The bin search feature solves this by allowing exact bin searches
as well as searches by letter or number segment.

For example, if the user enters `find bin/A-1`, the system returns only items stored in bin `A-1`.
If the user enters `find bin/10`, the system returns items whose bin number is `10`, such as `A-10`
and `B-10`.

#### High-level design

At a high level, this feature also reuses the command-based architecture of the application. The flow
is as follows:

1. The user enters a `find` command.
2. `FindItemParser` inspects the prefix before the `/`.
3. If the prefix is `bin`, the parser normalises the bin input using `BinLocationParser` and creates a
   `FindItemByBinCommand`.
4. The command is executed with access to the current `Inventory` and `UI`.
5. The command scans the inventory, identifies matching bin locations, and displays the result.

The main interaction for this flow is illustrated in [FindItemByBinCommandMainFlow.png](diagrams/FindItemByBinCommandMainFlow-Sequence_Diagram_for_FindItemByBinCommand__Main_Control_Flow_Only_.png).

This design was chosen because it allows bin-specific input normalisation to remain in the parser
layer, while the matching and display behaviour stays in the command layer.

#### Component-level implementation

The feature is mainly implemented using the following classes:

- `FindItemParser`
- `BinLocationParser`
- `FindItemByBinCommand`
- `Inventory`
- `Category`
- `Item`

The responsibilities of these classes are as follows:

- `FindItemParser` recognises that the user wants to search by bin.
- `BinLocationParser` normalises and validates the user-provided bin search input.
- `FindItemByBinCommand` performs the inventory scan and matching logic.
- `Inventory` exposes the list of stored categories.
- `Category` exposes the items inside each category.
- `Item` provides the bin location used during matching.

The parser does not perform the inventory scan itself. Its responsibility is limited to interpreting the
user input and constructing the correct command object.

#### Command execution flow

When `FindItemByBinCommand.execute()` is called, the implementation performs the following sequence:

1. Assert that `inventory`, `ui`, and `binInput` are not `null`.
2. Create an empty `List<Item>` named `matches`.
3. Retrieve all categories from the `Inventory`.
4. Iterate through each `Category`.
5. Within each category, iterate through each `Item`.
6. Read each item's bin location using `item.getBinLocation()`.
7. Call `isMatchingBin(itemBinLocation, binInput)` to determine whether the item matches.
8. Add matching items to `matches`.
9. After the scan, either display a no-items-found message or show the numbered result list.

The key matching logic is:

```java
if (binInput.contains("-")) {
    return normalizedBinLocation.equals(binInput);
}

if (Character.isLetter(binInput.charAt(0))) {
    return binLetter.equals(binInput);
}

return binNumber.equals(binInput);
```

This design supports three search modes using a single command:

- Exact bin search such as `A-1`
- Bin-letter search such as `A`
- Bin-number search such as `10`

#### Why the feature is implemented this way

The most important design choice in this feature is the use of a full inventory scan combined with a
small dedicated matching function, `isMatchingBin(...)`.

This was chosen for three reasons.

First, bin location is stored as part of each item rather than as a separate index. Reusing the
existing inventory structure keeps the implementation simple.

Second, the matching rules are slightly richer than a plain string equality check. The command needs to
support exact-bin, letter-only, and number-only searches, so isolating the logic in `isMatchingBin(...)`
keeps the main execution flow readable.

Third, the expected inventory size is small enough that a linear scan is acceptable. Introducing a more
complex location index would increase maintenance cost without enough practical benefit for the current
project scope.

Another deliberate design choice is that exact bin searches do not overmatch prefixes. For example,
searching for `A-1` should not accidentally return items in `A-10`. This behaviour is important for
physical storage accuracy.

#### Error handling and validation

Input validation is split between `FindItemParser` and `BinLocationParser`.

`FindItemParser` rejects missing `find` targets and missing values after the `/`.

`BinLocationParser` is responsible for normalising the bin search input before the command is created.
This ensures that the command operates on a consistent representation of the search term.

At execution time, `FindItemByBinCommand` handles the no-match case gracefully by displaying
`No items found in bin location: ...` instead of failing.

This design keeps invalid-input handling close to parsing, while expected no-result searches are
handled as normal command outcomes.

#### Alternatives considered

Several alternatives were considered when implementing this feature.

Alternative 1: Support only exact full-bin matching.

This would simplify the matching logic, but it was rejected because users may want to inspect all bins
with the same letter or the same number.

Alternative 2: Use raw substring matching on the stored bin location.

This was rejected because it can produce incorrect overmatching. For example, searching for `A-1`
would incorrectly match `A-10`.

Alternative 3: Maintain a separate map from bin location to items.

This was rejected because it introduces extra state that must be synchronised whenever items are added,
updated, or deleted.

#### Current limitations

The current implementation has some limitations.

- It performs a full scan of the inventory on every search.
- It returns items in their existing inventory order.
- It supports exact, letter, and number matching, but not more advanced patterns such as ranges.

These limitations are acceptable for the current project scope.

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

- Support bin ranges or multi-bin queries.
- Group results by category when displaying matches.
- Introduce an internal location index if inventory size grows significantly.

### Find Item By Quantity Feature

Another search feature in the product is the ability to find items by quantity using the command
`find qty/QUANTITY`.

This feature is useful because inventory reviews often start from stock levels rather than item names.
A user may want to identify low-stock items quickly, restock products below a threshold, or inspect all
items whose quantity is at or below a target level. The quantity search feature solves this by allowing
an inclusive threshold search.

For example, if the user enters `find qty/15`, the system returns items with quantity `15` as well as
items with smaller quantities such as `10` or `5`.

#### High-level design

At a high level, this feature reuses the same command-based architecture as the other `find` features.
The flow is as follows:

1. The user enters a `find` command.
2. `FindItemParser` inspects the prefix before the `/`.
3. If the prefix is `qty`, the parser validates the quantity using `CommonFieldParser.parseQuantity(...)`
   and creates a `FindItemByQtyCommand`.
4. The command is executed with access to the current `Inventory` and `UI`.
5. The command scans the inventory, identifies items whose quantity is less than or equal to the input,
   and displays the result.

This design was chosen because it keeps quantity validation in the parser layer while keeping inventory
scanning and threshold comparison in the command layer.


#### Component-level implementation

The feature is mainly implemented using the following classes:

- `FindItemParser`
- `CommonFieldParser`
- `FindItemByQtyCommand`
- `Inventory`
- `Category`
- `Item`

The responsibilities of these classes are as follows:

- `FindItemParser` recognises that the user wants to search by quantity.
- `CommonFieldParser` validates that the quantity input is a positive integer.
- `FindItemByQtyCommand` performs the inventory scan and threshold matching logic.
- `Inventory` exposes the list of stored categories.
- `Category` exposes the items inside each category.
- `Item` provides the stored quantity used during matching.

The parser does not perform the inventory scan itself. Its responsibility is limited to interpreting the
user input and constructing the correct command object.

#### Command execution flow

When `FindItemByQtyCommand.execute()` is called, the implementation performs the following sequence:

1. Assert that `inventory`, `ui`, and `qtyInput` are valid.
2. Create an empty `List<Item>` named `matches` to store matching items.
3. Retrieve all categories from the `Inventory`.
4. Iterate through each `Category`.
5. Within each category, iterate through each `Item`.
6. Read the item's quantity using `item.getQuantity()`.
7. Compare the item's quantity against the user-provided threshold.
8. If the item quantity is less than or equal to the threshold, add the item to `matches`.
9. After the scan is complete, either:
10. Display a `no items found` message if `matches` is empty, or
11. Print the list of matching items with numbering and dividers.

The key comparison is:

```java
if (item.getQuantity() <= qtyInput) {
    matches.add(item);
}
```

#### Design considerations

Alternative 1: Require an exact quantity match.

This was rejected because the more useful operational workflow is to find low-stock items at or below a
threshold, rather than only items with one exact quantity value.

Alternative 2: Support full comparison operators such as `<`, `<=`, `>`, and `>=`.

This was rejected for now because it would complicate the parser and command syntax beyond the current
scope of the product.

#### Current limitations

The current implementation has some limitations.

- It only supports one-sided inclusive threshold matching using `<=`.
- It returns items in the existing inventory order and does not sort by quantity.
- It does not support combining quantity search with another filter in the same command.

These limitations are acceptable for the current scope, but they indicate possible future extensions.

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

- Support explicit comparison operators such as `<`, `>`, or ranges.
- Sort results by quantity so that the lowest-stock items appear first.
- Allow quantity search to be combined with other filters such as category or expiry date.

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

The main interaction for this flow is illustrated in [AddItemCommandMainFlow.png](diagrams/AddItemCommandMainFlow-Sequence_Diagram_for_AddItemCommand__Main_Control_Flow_Only_.png).

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
7. `Duke` executes `AddItemCommand.execute(inventory, ui)`.
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
to a category-specific parser.

`AddItemCommandParser` and the specialised parsers validate category-specific input. If required
fields are missing or malformed, they throw `DukeException` before an `AddItemCommand` is created.

`AddItemCommand` also performs execution-time checks. If `inventory.findCategoryByName(categoryName)`
returns `null`, the command throws a `DukeException` with the message
`Category not found: <categoryName>`. If the parsed item is unexpectedly `null`, it throws
`Item cannot be null.`

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

#### Current limitations

The current implementation has some limitations.

- Category dispatch in `AddCommandParser` is hard-coded using a `switch` statement.
- Supporting a new category requires updates in multiple places, including parser dispatch and the
  corresponding model subtype.
- Error messages depend on the specific parser branch and are not fully standardised across all item
  types.

These limitations are acceptable for the current project scope, but they may become more noticeable if
the number of item categories continues to grow.

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

- Replace hard-coded category dispatch with a registry-based parser lookup.
- Standardise validation error messages across all category-specific parsers.
- Support optional default values for selected fields where domain rules permit them.
- Separate category definitions from parser code so new item types can be added with less wiring.

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
* If the category is not found, throw a `DukeException`.
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
   throw new DukeException("Category not found: " + categoryName);
}

if (itemIndex < 1 || itemIndex > category.getItemCount()) {
   throw new DukeException("Invalid item index: " + itemIndex);
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

#### Current limitations

The current implementation has some limitations.

* Only shared item fields are updatable: `newItem/`, `bin/`, `qty/`, and `expiryDate/`. Category-specific  
  fields such as `size/`, `brand/`, or `isRipe/` cannot currently be updated.  
* Unsupported fields cause the command to fail instead of being ignored.  
* The command depends on item indices, so the user may need to run `list` or `find` beforehand to  
  determine the correct target item.  
* Updating one item does not currently show the full updated item record after completion.  

These limitations are acceptable for the current project scope, but they highlight possible areas for  
future enhancement.

---

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

* Support updates to category-specific fields such as `size/`, `brand/`, `flavour/`, or `isRipe/`.  
* Show the full updated item details after a successful edit.  
* Support updating multiple matched items in batch mode.  
* Replace index-based targeting with more flexible item matching and disambiguation.  

### Testing update item

* Ensure the inventory contains at least one category with items, for example `fruits`.

* Run  
  `add category/fruits item/apple bin/A1 qty/10 expiryDate/2026-4-01 size/medium isRipe/true`

* Run  
  `update category/fruits index/1 qty/20`

* Verify that the application shows a confirmation message indicating the item was updated.

* Run `list`.

* Verify that the quantity of apple is now updated to 20.


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
3. `Duke` executes the command with the current `Inventory` and `UI`.
4. `ListCommand` delegates rendering to `UI.showInventory(inventory)`.
5. `UI` iterates through the inventory and prints the formatted listing to the user.

The main interaction for this flow is illustrated in [ListCommandMainFlow.png](diagrams/ListCommandMainFlow-Sequence_Diagram_for_ListCommand__Main_Control_Flow_Only_.png).

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

#### Current limitations

The current implementation has some limitations.

- The command always lists the full inventory and does not support optional filters or sorting.
- Output ordering depends on the current order of categories and items stored in memory.
- Formatting is tied to the current console UI implementation.

These limitations are acceptable for the current scope because the command is intended to provide a
simple full-inventory view.

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

- Support optional sorted views such as by category name or expiry date.
- Add pagination or condensed summaries for larger inventories.
- Reuse the same command object for alternate UI front ends if the presentation layer expands.

### Sort Feature (Coming Soon)

The product also supports displaying the current inventory with items sorted within each category using 
the `sort` command.

This feature is useful because users may want to inspect the inventory from a different perspective without changing 
the actual stored order of items. For example, a user may want to see which items are expiring earliest, which items 
have the lowest quantity or items in alphabetical order. The sort command solves this by generating a sorted view 
of the inventory while keeping items group under their original categories.

For example, if the user enters`sort expirydate`, the system displays all categories as usual, but the items inside 
each category are shown in ascending expiry date order, allowing the user to quickly identify items that are
expiring soon.

#### High-level design

This feature extends the existing command-based architecture used by the product. The flow is as follows:

1. The user enters a `sort` command followed by a sort type.
2. `Parser` recognises the sort command word and delegates the argument to `SortCommandParser`.
3. `SortCommandParser` validates the sort type and creates a `SortCommand`.
4. `Duke` executes the command with access to the current `Inventory` and `UI`.
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

The main interaction for this flow is illustrated in [SortingMainFlow.png](diagrams/SortingMainFlow.png).

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

If the user enters `sort` without providing a sort type, the parser throws a `DukeException` indicating that a valid 
sort type is required.

If the user provides an unsupported sort type, the parser throws a `DukeException` listing the valid options, 
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

#### Current limitations

The current implementation has some limitations.

- It only supports one sorting criterion at a time.
- It preserves category order and does not sort categories themselves.
- It depends on valid item data for correct field comparison, especially for expiry-date sorting.
- It currently only support fix direction of sorting, user cannot choose ascending or descending.

These limitations are acceptable for the current project scope.

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

- Support multi-level sorting such as sorting by expiry date and then by name.
- Allow categories themselves to be sorted optionally.
- Support ascending and descending variants for each sort type.

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

The main interaction for this flow is illustrated in [StorageSavingMainFlow.png](diagrams/StorageSavingMainFlow.png).

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

This design allows the application to reconstruct the same logical inventory state from the 
saved text data.

The loading process depends on the file format remaining consistent with the save format.
Since both directions are controlled by `Storage`, the implementation can ensure that the data
written is also the data that can be read back correctly.

This approach reuses existing parsing logic, ensuring consistency between user input handling and 
stored data reconstruction.

The main interaction for this flow is illustrated in [StorageLoadingMainFlow.png](diagrams/StorageLoadingMainFlow.png).

#### Error handling and validation

The storage component also handles cases where the save file is missing, or contains invalid data.

If the file does not exist, the application can start with an empty inventory instead of
crashing. This is because the absence of a save file may simply mean that the program is being
run for the first time.

If a line is malformed, the exception is caught and the line is skipped. A warning is 
logged and the user is informed via the UI, detailing the line that was skipped and the 
reason for skipping. 

#### Why the storage component is implemented this way

The simple text-based format is chosen instead of a more complex format such as JSON or database
for several reasons.

First, this keeps the implementation lightweight. The project does not require any external 
libraries or database setup, which makes the application easier to develop and test.

Second, the saved data is readable which is useful during debugging because we can inspect the 
contents of the file directly and verify whether items are being written correctly.

Third, the amount of data in the application is relatively small. Hence, a plain text file is 
sufficient and avoids unnecessary complexity.

#### Alternatives considered

Alternative 1: Store each category in a separate file.

This could improve file organization, especially if categories become large. It was rejected because
it would make file management more complicated and require the application to coordinate multiple 
save files instead of just one.

Alternative 2: Use JSON format.

This would make the file structure more standardized. However, it was rejected because it would
introduce additional complexity which is unnecessary for our project scope.

#### Current limitations

The current storage implementation has several limitations. 

1. The storage system relies on a fixed text format with prefixes such as `category/`, `item/`.
If the format is modified or corrupted, the parser may fail to reconstruct the item correctly.
2. During loading, each stored line is converted into a Command using `AddItemCommandParser` and executed.
While this ensures consistency with user input handling, it introduces coupling between storage logic and 
command parsing logic. Any changes in parsing behaviour may affect the loading process.
3. Malformed or corrupted lines are skipped during loading. While this prevents crashes, it may result
in data loss and incomplete reconstruction of the inventory.

For the current version of the application, these limitations are acceptable, but they may become
relevant if the system grows more complex.

#### Possible future improvements

The following enhancements can be considered to improve the storage component.

1. Introduce structured storage format, such as JSON. This will help to improve robustness and 
make format easier to extend.
2. The system could directly construct `Item` objects instead of converting stored lines into 
commands. This would reduce coupling and improve clarity of storage logic.
3. Instead of skipping malformed lines completely, the system could attempt partial recovery and
provide more detailed diagnostics to the user. This would reduce potential data loss.

### Delete Item Feature

Another core feature of the product is the ability to delete an item from a specific category using
the command `delete category/CATEGORY index/INDEX`.

This feature is necessary because users need to remove items that are no longer available, have been
fully consumed, or were added by mistake. Without a targeted delete operation, users would have no way
to keep the inventory accurate over time. The delete-item command solves this by allowing the user to
specify the category and the 1-based index of the item to remove.

For example, if the user enters `delete category/fruits index/1`, the system locates the `fruits`
category, removes the first item in it, and displays a confirmation message showing which item was
deleted.

#### High-level design

At a high level, this feature fits into the existing command-based architecture of the application.
The flow is as follows:

1. The user enters a `delete` command with both `category/` and `index/` fields.
2. `Parser` recognises the `delete` command word and delegates the remaining input to
   `DeleteCommandParser`.
3. `DeleteCommandParser` extracts the category name and index string, validates them, and creates a
   `DeleteItemCommand`.
4. The command is executed with access to the current `Inventory` and `UI`.
5. The command looks up the category, validates the index, removes the item, and shows a confirmation
   message.

The main interaction for this flow is illustrated in
[DeleteItemCommandMainFlow.png](diagrams/DeleteItemCommandMainFlow-Sequence_Diagram_for_DeleteItemCommand__Main_Control_Flow_Only_.png).

This design was chosen because it follows the same separation of concerns used throughout the project:

- `Parser` and `DeleteCommandParser` interpret user input.
- `DeleteItemCommand` performs the inventory mutation.
- Model classes such as `Inventory`, `Category`, and `Item` hold the application state.
- `UI` presents confirmation or error messages to the user.

As a result, the delete-item feature integrates cleanly into the existing command pipeline without
requiring changes to the overall architecture.

#### Component-level implementation

The feature is mainly implemented using the following classes:

- `Parser`
- `DeleteCommandParser`
- `DeleteItemCommand`
- `Inventory`
- `Category`
- `Item`
- `UI`

The responsibilities of these classes are as follows:

- `Parser` detects the `delete` command word and delegates to `DeleteCommandParser`.
- `DeleteCommandParser` tokenises the arguments, extracts `category/` and `index/` fields, validates
  that the index is a positive integer, and constructs a `DeleteItemCommand`.
- `DeleteItemCommand` performs the actual removal of the item from the inventory.
- `Inventory` provides category lookup through `findCategoryByName(...)`.
- `Category` provides item access through `getItem(...)` and removal through `removeItem(...)`.
- `Item` provides the name of the deleted item for the confirmation message.
- `UI` displays the result to the user.

The parser logic deliberately separates field extraction from index validation.
`DeleteCommandParser.parse(...)` handles tokenisation and field extraction, while the private helper
`parseDeleteItem(...)` is responsible for converting the index string into a valid integer. This keeps
each method focused on a single concern.

#### Command execution flow

When `DeleteItemCommand.execute()` is called, the implementation performs the following sequence:

1. Assert that `inventory`, `ui`, and `categoryName` are not `null`.
2. Call `inventory.findCategoryByName(categoryName)` to locate the target category.
3. If the category is not found, call `ui.showCategoryNotFound(categoryName)` and return.
4. Check whether `itemIndex` is within the valid range (1 to `category.getItemCount()`).
5. If the index is out of range, call `ui.showError(...)` with a message describing the valid range
   and return.
6. Retrieve the item at position `itemIndex - 1` using `category.getItem(...)`.
7. Remove the item at position `itemIndex - 1` using `category.removeItem(...)`.
8. Log the deletion at `INFO` level.
9. Call `ui.showItemDeleted(item.getName(), category.getName())` to confirm the deletion to the user.

#### Error handling and validation

Validation is split across the parser layer and the command layer.

`DeleteCommandParser` rejects input that is empty, contains unrecognised fields, or is missing the
required `category/` field. If `index/` is provided, `parseDeleteItem(...)` rejects non-integer values
and non-positive integers before a `DeleteItemCommand` is created.

`DeleteItemCommand` performs execution-time checks. If the category does not exist in the inventory,
the command shows a category-not-found message. If the index is out of bounds for the resolved
category, the command shows an error message indicating the valid range.

This layered approach ensures that syntactically invalid input is caught at parse time, while
semantically invalid operations such as referencing a missing category or an out-of-range index are
caught at execution time.

#### Alternatives considered

Several alternatives were considered when implementing this feature.

Alternative 1: Delete by item name instead of index.

This was rejected because multiple items can share the same name across or within categories. Using an
index removes ambiguity and ensures the user can target a specific item.

Alternative 2: Require a confirmation prompt before every item deletion.

This was rejected because individual item deletions are low-risk and easily reversible by re-adding the
item. The confirmation prompt is reserved for the higher-impact `DeleteCategoryCommand`, which clears
all items in a category at once.

Alternative 3: Let `DeleteCommandParser` also handle the category-not-found check.

This was rejected because category existence is a runtime concern that depends on the current inventory
state. Keeping this check in the command layer preserves the separation between parsing and execution.

#### Current limitations

The current implementation has some limitations.

- There is no undo mechanism. Once an item is deleted, it must be manually re-added.
- The command uses a 1-based index, which requires the user to run `list` or `find` beforehand to
  determine the correct index.
- Deleting an item shifts the indices of subsequent items, which may confuse users performing multiple
  consecutive deletions.

These limitations are acceptable for the current project scope.

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

- Add an undo or soft-delete mechanism that allows recently deleted items to be restored.
- Support deletion by item name with a disambiguation prompt when multiple matches exist.
- Display the updated item list after a successful deletion so the user can see the new indices.
- Support batch deletion by accepting multiple indices in a single command.

### Delete Category Feature

Another core feature of the product is the ability to clear all items within a category using the
command `delete category/CATEGORY`.

This feature is necessary because users sometimes need to remove an entire category's worth of items
at once, for example when a product line is discontinued or when restocking requires a full reset of
a category. Without a bulk-delete operation, users would have to remove each item individually using
`delete category/CATEGORY index/INDEX`, which is tedious and error-prone for categories with many items.

For example, if the user enters `delete category/fruits`, the system locates the `fruits` category,
prompts the user for confirmation if items exist, and clears all items upon receiving a `yes` response.

#### High-level design

At a high level, this feature reuses the same command-based architecture and parser pipeline as the
single-item delete feature. The flow is as follows:

1. The user enters a `delete` command with only the `category/` field and no `index/` field.
2. `Parser` recognises the `delete` command word and delegates to `DeleteCommandParser`.
3. `DeleteCommandParser` detects that no `index/` field is present and creates a
   `DeleteCategoryCommand` instead of a `DeleteItemCommand`.
4. The command is executed with access to the current `Inventory` and `UI`.
5. If the category is not empty, the command prompts the user for confirmation via `UI`.
6. If the user confirms, the command clears all items from the category.

The main interaction for this flow is illustrated in
[DeleteCategoryCommandMainFlow.png](diagrams/DeleteCategoryCommandMainFlow-Sequence_Diagram_for_DeleteCategoryCommand__Main_Control_Flow_Only_.png).

This design was chosen because it follows the same separation of concerns used throughout the project:

- `DeleteCommandParser` interprets user input and decides which delete command to create.
- `DeleteCategoryCommand` performs the confirmation and bulk-clear logic.
- Model classes such as `Inventory` and `Category` hold the application state.
- `UI` handles the confirmation prompt and result messages.

The key design distinction from `DeleteItemCommand` is the confirmation prompt. Because clearing an
entire category is a higher-risk operation than removing a single item, the command requires the user
to type `yes` before proceeding. This prevents accidental data loss.

#### Component-level implementation

The feature is mainly implemented using the following classes:

- `Parser`
- `DeleteCommandParser`
- `DeleteCategoryCommand`
- `Inventory`
- `Category`
- `UI`

The responsibilities of these classes are as follows:

- `Parser` detects the `delete` command word and delegates to `DeleteCommandParser`.
- `DeleteCommandParser` determines that no `index/` field is present and constructs a
  `DeleteCategoryCommand` with the category name.
- `DeleteCategoryCommand` performs the category lookup, confirmation prompt, and item clearing.
- `Inventory` provides category lookup through `findCategoryByName(...)`.
- `Category` provides `isEmpty()`, `getItemCount()`, and `getItems().clear()` for the clearing logic.
- `UI` displays the confirmation prompt, cancellation message, or cleared-category message.

#### Command execution flow

When `DeleteCategoryCommand.execute()` is called, the implementation performs the following sequence:

1. Assert that `inventory`, `ui`, and `categoryName` are not `null`.
2. Call `inventory.findCategoryByName(categoryName)` to locate the target category.
3. If the category is not found, call `ui.showCategoryNotFound(categoryName)` and return.
4. If the category is not empty:
   a. Call `ui.showDeleteCategoryConfirmation(categoryName, category.getItemCount())` to display the
   prompt.
   b. Read the user's response via `ui.readCommand()`.
   c. If the response is not `yes` (case-insensitive), call
   `ui.showDeleteCategoryCancelled(categoryName)` and return.
   d. Call `category.getItems().clear()` to remove all items.
   e. Call `ui.showCategoryItemsCleared(categoryName)`.
5. Log the deletion at `INFO` level.
6. Call `ui.showCategoryDeleted(categoryName)`.

The core confirmation logic is:

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

This ensures that the user is always informed of the consequences before a bulk deletion proceeds.

#### Error handling and validation

Validation is split across the parser layer and the command layer.

`DeleteCommandParser` handles syntax-level validation. It rejects empty input, unrecognised fields,
and missing `category/` fields before any command object is created.

`DeleteCategoryCommand` handles execution-time validation. If the category does not exist in the
inventory, the command shows a category-not-found message via `UI`. If the user does not confirm the
deletion (including providing a `null` response), the command cancels gracefully.

The confirmation check uses `equalsIgnoreCase("yes")`, which means responses such as `Yes`, `YES`,
and `YeS` are all accepted.

#### Alternatives considered

Several alternatives were considered when implementing this feature.

Alternative 1: Remove the category object from the inventory entirely instead of clearing its items.

This was rejected because categories in the application are predefined. Removing the category object
would prevent users from adding items back into the same category later without recreating it.

Alternative 2: Skip the confirmation prompt and clear immediately.

This was rejected because clearing all items in a category is a high-impact operation. A confirmation
prompt prevents accidental data loss and gives the user a chance to reconsider.

Alternative 3: Require a different command word such as `clear` instead of reusing `delete`.

This was rejected because reusing the `delete` command word with different argument patterns is more
consistent with the existing command structure. The parser can distinguish between item deletion and
category clearing based on the presence or absence of the `index/` field.

#### Current limitations

The current implementation has some limitations.

- There is no undo mechanism. Once items are cleared, they must be re-added manually.
- The confirmation prompt accepts only `yes` as a positive response. Other affirmative phrases are
  treated as cancellations.
- If the category is already empty, the command still calls `showCategoryDeleted` without informing
  the user that no items were actually removed.

These limitations are acceptable for the current project scope.

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

- Add an undo or soft-delete mechanism to restore recently cleared categories.
- Display the list of items that will be removed before the confirmation prompt.
- Inform the user explicitly when the category was already empty.
- Support clearing multiple categories in a single command.

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

---

### Find Item By Keyword Feature

Another search feature in the product is the ability to find items by keyword using the command
`find keyword/KEYWORD`.

This feature is useful because users often remember part of an item name but not its exact name or
which category it belongs to. A keyword search provides a fast, flexible way to locate items across
the entire inventory without needing to browse each category individually.

For example, if the user enters `find keyword/apple`, the system returns all items whose names
contain `apple`, such as `apple`, `pineapple`, and `apple_juice`, regardless of category.

#### High-level design

At a high level, this feature reuses the existing command pipeline of the application. The flow is
as follows:

1. The user enters a `find` command.
2. `FindItemParser` inspects the prefix before the `/`.
3. If the prefix is `keyword`, the parser creates a `FindItemByKeywordCommand`.
4. The command is executed with access to the current `Inventory` and `UI`.
5. The command scans all categories and items, collects matches, and displays the result.

The main interaction for this flow is illustrated in
[FindItemByKeywordCommandMainFlow.png](diagrams/FindItemByKeywordCommandMainFlow-Sequence_Diagram_for_FindItemByKeywordCommand__Main_Control_Flow_Only_.png).

This design was chosen because it follows the same separation of concerns already used throughout
the project:

- Parsers interpret user input.
- Command classes implement the application behaviour.
- Model classes store inventory data.
- `UI` is responsible for displaying the result to the user.

As a result, the keyword-search feature integrates cleanly into the existing find-command family
without requiring a separate search subsystem.

#### Component-level implementation

The feature is mainly implemented using the following classes:

- `FindItemParser`
- `FindItemByKeywordCommand`
- `Inventory`
- `Category`
- `Item`

The responsibilities of these classes are as follows:

- `FindItemParser` recognises that the user wants to search by keyword and creates the command.
- `FindItemByKeywordCommand` performs the full inventory scan and substring matching.
- `Inventory` exposes the list of categories currently stored.
- `Category` exposes the list of items belonging to that category.
- `Item` provides the item name used during matching.

The parser logic remains intentionally small. It only determines the requested find type and
constructs the appropriate command object. The actual search is performed inside the command layer.

#### Command execution flow

When `FindItemByKeywordCommand.execute()` is called, the implementation performs the following
sequence:

1. Assert that `inventory`, `ui`, and `keywordInput` are not `null`.
2. Create an empty `List<String>` named `matches` to store formatted search results.
3. Retrieve all categories from the `Inventory`.
4. Iterate through each `Category`.
5. Within each category, iterate through each `Item`.
6. Compare the item's name (lowercased) against the keyword (lowercased) using `contains(...)`.
7. If the item name contains the keyword, add a formatted string
   `category.getName() + ": " + item` to `matches`.
8. After the scan is complete, either:
    - Display `No items found matching keyword: ...` if `matches` is empty, or
    - Display dividers, a heading, and the numbered list of matching items.

The key comparison logic is:

```java
if (item.getName().toLowerCase().contains(keywordInput.toLowerCase())) {
    matches.add(category.getName() + ": " + item);
}
```

This means the search is case-insensitive and supports partial matches. A keyword like `apple`
matches `apple`, `pineapple`, and `apple_juice`.

#### Why the feature is implemented this way

The most important design choice in this feature is that the command performs a full scan of the
inventory using case-insensitive substring matching instead of relying on exact-name matching or
a precomputed index.

This was chosen for three reasons.

First, substring matching is more practical for real-world use. Users often remember only part of an
item name, and exact matching would miss items like `pineapple` when searching for `apple`.

Second, it keeps the implementation simple. The inventory is already organised by category, and each
category stores its own list of items. A linear scan through this existing structure avoids adding
new state that must be maintained whenever items are added or removed.

Third, the expected inventory size is modest. A linear scan is acceptable for the current scale of
the application.

Another deliberate design choice is that keyword matching operates across all categories rather than
within a single category. This makes the feature more useful because users searching by keyword
typically do not know which category the item belongs to.

#### Error handling and validation

Input validation is handled mainly by `FindItemParser`.

If the user enters `find` with no target, the parser throws a `DukeException` explaining the
supported find formats.

If the user enters `find keyword/` with no value after the slash, the parser throws a `DukeException`
for the missing keyword before any command object is created.

At execution time, `FindItemByKeywordCommand` handles the no-match case gracefully by displaying
`No items found matching keyword: ...` instead of failing.

This makes the feature robust without treating common no-result situations as errors.

#### Alternatives considered

Several alternatives were considered when implementing this feature.

Alternative 1: Support only exact-name matching.

This was rejected because it significantly reduces the usefulness of the search. Users who remember
only part of an item name would not benefit from the feature.

Alternative 2: Restrict keyword search to a single category at a time.

This was rejected because it weakens the feature. If the user already knows the category, they can
use `find category/CATEGORY` instead. Keyword search is most valuable when the user does not know
which category an item belongs to.

Alternative 3: Maintain a separate keyword index.

This was rejected because it introduces extra state that must be synchronised whenever items change.
The linear scan is sufficient for the expected inventory size.

#### Current limitations

The current implementation has some limitations.

- It performs a full scan of the inventory each time the command is run.
- It matches only against the item name and does not search other fields such as bin location or
  brand.
- Results follow the current category and item order and are not sorted by relevance.

These limitations are acceptable for the current project scope.

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

- Support searching across multiple item fields such as name, bin location, and brand.
- Highlight the matching keyword in the output.
- Sort results by relevance or group them by category.
- Support multiple keywords in a single search.

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

---

### Help Feature

The product also supports displaying help information using the `help` command.

This feature is important because new users need a quick reference to discover which commands are
available without reading external documentation first. The help command provides a summary of
available commands and directs the user to the full User Guide for detailed usage and examples.

For example, when the user enters `help`, the system displays the list of command words and a URL
to the User Guide.

#### High-level design

At a high level, the feature is intentionally minimal and fits directly into the existing command
architecture:

1. The user enters a `help` command.
2. `Parser` recognises the command word and constructs a `HelpCommand`.
3. `Duke` executes the command with the current `Inventory` and `UI`.
4. `HelpCommand` delegates rendering to `UI.showHelp()`.
5. `UI` prints the available commands and the User Guide link.

The main interaction for this flow is illustrated in
[HelpCommandMainFlow.png](diagrams/HelpCommandMainFlow-Sequence_Diagram_for_HelpCommand__Main_Control_Flow_Only_.png).

This design was chosen because displaying help does not require separate parsing logic beyond
recognising the command word. The command object acts as a bridge between the parser and the UI,
consistent with the architecture used for `ListCommand` and other simple commands.

#### Component-level implementation

The feature is mainly implemented using the following classes:

- `Parser`
- `HelpCommand`
- `UI`

The responsibilities of these classes are as follows:

- `Parser` detects the `help` command and returns a new `HelpCommand`.
- `HelpCommand` represents the help operation and triggers the display behaviour.
- `UI` formats and prints the help message including the command summary and User Guide link.

This design keeps the command itself lightweight. Since help is a read-only operation that does not
interact with the inventory, the command simply delegates to `UI.showHelp()`.

#### Command execution flow

When `HelpCommand.execute()` is called, the implementation performs the following sequence:

1. Call `ui.showHelp()`.
2. Inside `UI.showHelp()`:
   a. Display a divider.
   b. Print the list of available command words: `add, delete, update, find, list, help, bye`.
   c. Print a blank line.
   d. Print a message directing the user to the User Guide URL.
   e. Display a closing divider.

The command logic is intentionally short:

```java
public void execute(Inventory inventory, UI ui) {
    ui.showHelp();
}
```

This reflects the design decision that `HelpCommand` should trigger the operation, while formatting
and presentation remain the responsibility of the UI.

#### Why the feature is implemented this way

The most important design choice here is that the help command shows a brief summary of command words
and a User Guide link instead of displaying detailed usage for every command inline.

This was chosen for two reasons.

First, it keeps the help output concise. Displaying full command formats, examples, and notes for
every command would produce a very long output that is difficult to scan quickly. A short summary
with a link to external documentation strikes a better balance.

Second, it avoids duplication. If detailed usage were maintained both in the help output and in the
User Guide, any change to a command format would need to be updated in two places.

#### Alternatives considered

Several alternatives were considered when implementing this feature.

Alternative 1: Display full usage details for every command directly in the help output.

This was rejected because it produces a long wall of text that is hard to read in a CLI environment.
Users who need detailed guidance are better served by the User Guide.

Alternative 2: Support `help COMMAND` to show usage for a specific command.

This is a reasonable enhancement for the future, but was not implemented in the current version to
keep the feature simple.

Alternative 3: Remove the help command entirely and rely on the User Guide alone.

This was rejected because users expect a `help` command in a CLI application. Even a brief response
reassures the user that help is available and points them to the right resource.

#### Current limitations

The current implementation has some limitations.

- The command does not support targeted help for individual commands.
- The command summary is hard-coded in `UI.showHelp()`, so adding a new command requires updating
  the help output manually.

These limitations are acceptable for the current project scope.

#### Possible future improvements

If this feature is extended in future versions, the following improvements could be considered:

- Support `help COMMAND` to display detailed usage for a specific command.
- Auto-generate the command list from a registry instead of hard-coding it.

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
2. Compile and run the `Duke` class.
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


### Testing updating multiple fields

1. Run  
  `update category/fruits index/1 newItem/green_apple bin/A2 expiryDate/2026-5-01`

2. Verify that:
  * The item name is updated to green_apple  
  * The bin location is updated to A2  
  * The expiry date is updated to 2026-5-01  

3. Run `list`.

4. Verify that all updated fields are reflected correctly.

---

### Testing invalid category

5. Run `update category/unknown index/1 qty/10`

6. Verify that the application shows  `Category not found: unknown` or the corresponding error message.

---

### Testing invalid index

7. Run `update category/fruits index/100 qty/10`

8. Verify that the application shows an error indicating the index is out of range.

9. Run `update category/fruits index/abc qty/10`

10. Verify that the application shows  
  `Item index must be an integer.`

---

### Testing missing fields

11. Run `update category/fruits index/1`

12. Verify that the application shows `Provide at least one field to update.`

13. Run `update index/1 qty/10`

14. Verify that the application shows `Missing category.`

15. Run `update category/fruits qty/10`

16. Verify that the application shows `Missing item index.`

---

### Testing invalid field values

17. Run `update category/fruits index/1 qty/-5`

18. Verify that the application shows `Quantity must be a positive integer.`

19. Run `update category/fruits index/1 expiryDate/2026/05/01`

20. Verify that the application shows `Invalid date. Please use yyyy-M-d.`

21. Run `update category/fruits index/1 bin/`

22. Verify that the application shows an error for missing bin location.

---

### Testing unsupported fields

23. Run  
  `update category/fruits index/1 size/large`

24. Verify that the application shows  
  `Unsupported field: size` (or corresponding error message).

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



