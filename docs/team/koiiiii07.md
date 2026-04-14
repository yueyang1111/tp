# KOIiiii07 - Project Portfolio Page
## Overview
InventoryDock is a CLI inventory management application for store managers to track stock by category, quantity, expiry date, and bin location. It supports fast keyboard-driven workflows for adding items, listing the full inventory, and locating stock based on storage-related attributes. I focused on the user interface layer, item and category deletion, keyword-based search, and the help system.

## Summary of Contributions
### Code Contributed
- [Code Contribution](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=KOIiiii07&tabRepo=AY2526S2-CS2113-W09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements Implemented
1. **UI class (`UI.java`)** — Implemented and maintained the central `UI` class, encapsulating all `System.out` and `Scanner` operations. Provided semantically named methods (e.g. `showItemDeleted`, `showClearCategoryConfirmation`, `showInventory`, `showHelp`) so command classes stay free from direct I/O formatting.
2. **Delete item command** — Implemented `DeleteItemCommand` and `DeleteCommandParser` with layered validation: syntactic checks (missing fields, non-integer index) at parse time and semantic checks (non-existent category, out-of-range index) at execution time. Added comprehensive JUnit tests.
3. **Clear category command** — Implemented `ClearCategoryCommand` with a user confirmation prompt before clearing all items in a category. Integrated confirmation flow with `UI` methods and added JUnit tests covering acceptance, rejection, and case-insensitive matching.
4. **Find item by keyword** — Implemented `FindItemByKeywordCommand` for case-insensitive partial-match searching across all categories. Extended `FindItemParser` for dispatch and added JUnit tests for various match scenarios.
5. **Help command** — Implemented the `help` command and `showHelp()` in `UI`, displaying a command summary and User Guide link.
6. **FieldParser utility** — Implemented a reusable `extractField` method used across multiple parsers, with comprehensive JUnit tests covering all branches.

### Contributions to the User Guide
Wrote or substantially updated the UG sections for: deleting an item, clearing a category, finding items by keyword, help command, and field parser — including command formats, examples, expected outcomes, and edge-case notes.

### Contributions to the Developer Guide
Wrote or substantially updated the DG sections for: Delete Item, Clear Category, Find Item By Keyword, and Help — each covering feature motivation, design, implementation, execution flow, error handling, alternatives considered, and manual testing. Also updated the table of contents and manual testing instructions. Added 20 UML diagrams: 8 sequence, 4 class, 4 object diagrams across all four features.

### Contributions to Team-Based Tasks
- Maintained the `UI` class as a shared component, updating it when teammates needed new output methods.
- Helped fix Checkstyle and code style issues during integration.
- Helped merge teammates' PRs after reviewing them.

### Review and Mentoring Contributions
- [PR #101 review comment](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/101#issuecomment-4174049960)
- [PR #103 review comment](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/103#issuecomment-4174048971)

### Contributions beyond the Project Team
- Reported 10 bugs during the PE Dry Run for another team's product ([Issues #1–#10](https://github.com/NUS-CS2113-AY2526-S2/ped-KOIiiii07/issues))

## Contributions to the User Guide (Extracts)

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

## Contributions to the Developer Guide (Extracts)
I added the following UML diagrams:

Sequence diagrams:
- [FindItemByKeywordCommandParseFlow.puml](../diagrams/sequence/FindItemByKeywordCommandParseFlow.puml) — shows how `FindItemParser` dispatches a keyword search and constructs the command.
- [FindItemByKeywordCommandMatchingFlow.puml](../diagrams/sequence/FindItemByKeywordCommandMatchingFlow.puml) — shows the inventory scan and case-insensitive substring matching logic across all categories.
- [FindItemByKeywordCommandDisplayFlow.puml](../diagrams/sequence/FindItemByKeywordCommandDisplayFlow.puml) — shows the category-grouped result display and the no-match output path.
- [HelpCommandMainFlow.puml](../diagrams/sequence/HelpCommandMainFlow.puml) — shows the parse-to-execution flow including the argument validation branch.
- [ClearCategoryCommandParseFlow.puml](../diagrams/sequence/ClearCategoryCommandParseFlow.puml) — shows how `ClearCommandParser` extracts and validates the category field.
- [ClearCategoryCommandExecutionFlow.puml](../diagrams/sequence/ClearCategoryCommandExecutionFlow.puml) — shows the confirmation prompt logic, the early return for empty categories, and the clear operation.
- [DeleteItemCommandParseFlow.puml](../diagrams/sequence/DeleteItemCommandParseFlow.puml) — shows how `DeleteCommandParser` tokenises input, extracts `category/` and `index/`, and validates the index.
- [DeleteItemCommandExecutionFlow.puml](../diagrams/sequence/DeleteItemCommandExecutionFlow.puml) — shows the category lookup, index validation, item removal, and confirmation display.

Class diagrams:
- [DeleteItemCommandClassDiagram.puml](../diagrams/class/DeleteItemCommandClassDiagram.puml) — shows relationships between `DeleteCommandParser`, `DeleteItemCommand`, `Inventory`, `Category`, `Item`, and `UI`.
- [ClearCategoryCommandClassDiagram.puml](../diagrams/class/ClearCategoryCommandClassDiagram.puml) — shows relationships between `ClearCommandParser`, `ClearCategoryCommand`, `Inventory`, `Category`, `Item`, and `UI` including the confirmation-related UI methods.
- [FindItemByKeywordCommandClassDiagram.puml](../diagrams/class/FindItemByKeywordCommandClassDiagram.puml) — shows relationships between `FindItemParser`, `FindItemByKeywordCommand`, `Inventory`, `Category`, `Item`, and `UI` including the format helper methods.
- [HelpCommandClassDiagram.puml](../diagrams/class/HelpCommandClassDiagram.puml) — shows the relationship between `Parser`, `HelpCommand`, and `UI` including the `showHelp()` and `showError()` delegation paths.

Object diagrams:
- [DeleteItemCommandObjectDiagram.puml](../diagrams/object/DeleteItemCommandObjectDiagram.puml) — shows a representative runtime snapshot with a specific category name and item index.
- [ClearCategoryCommandObjectDiagram.puml](../diagrams/object/ClearCategoryCommandObjectDiagram.puml) — shows a representative runtime snapshot with a specific category name.
- [FindItemByKeywordCommandObjectDiagram.puml](../diagrams/object/FindItemByKeywordCommandObjectDiagram.puml) — shows a representative runtime snapshot with a specific keyword input.
- [HelpCommandObjectDiagram.puml](../diagrams/object/HelpCommandObjectDiagram.puml) — shows a representative runtime snapshot with an empty arguments field.
