# KOIiiii07 - Project Portfolio Page
## Overview
InventoryDock is a CLI inventory management application for store managers to track stock by category, quantity, expiry date, and bin location. It supports fast keyboard-driven workflows for adding items, listing the full inventory, and locating stock based on storage-related attributes. I focused on the user interface layer, item and category deletion, keyword-based search, and the help system.

## Summary of Contributions
### Code Contributed
- [Code contribution dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=KOIiiii07&tabRepo=AY2526S2-CS2113-W09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements Implemented
#### 1. UI class (`UI.java`)
I implemented and maintained the central `UI` class. This class handles welcome and goodbye messages, dividers, error formatting, inventory display, and confirmation prompts.
Key aspects of the implementation:
- Designed `UI` to encapsulate all `System.out` and `Scanner` operations, keeping the rest of the codebase free from direct I/O calls.
- Provided dedicated methods for common display patterns such as `showItemDeleted`, `showItemAdded`, `showCategoryCleared`, `showClearCategoryConfirmation`, `showInventory`, and `showHelp`, so that command classes can call semantically named methods rather than formatting output themselves.
- Ensured consistency in output formatting (dividers, error prefixes, numbered lists) across all features.

#### 2. Delete item command
I implemented the `delete` command for removing a single item from the inventory by category and index. This work included the command execution logic, the parser pipeline in `DeleteCommandParser`, and comprehensive JUnit tests.
Key aspects of the implementation:
- Added `DeleteItemCommand` to look up the target category, validate the index, remove the item, and display a confirmation message.
- Extended `DeleteCommandParser` to tokenise the input, extract `category/` and `index/` fields, and validate that the index is a positive integer before constructing the command.
- Implemented layered validation: syntactic checks (missing fields, non-integer index) at parse time and semantic checks (non-existent category, out-of-range index) at execution time.
- Added JUnit tests covering valid deletions, out-of-range indices, non-existent categories, and sequential deletions.

#### 3. Clear category command
I implemented the `clear category/CATEGORY` command, which clears all items within a specified category after a user confirmation prompt.
Key aspects of the implementation:
- Added `ClearCategoryCommand` to prompt the user for confirmation before clearing items.
- Integrated the confirmation flow with `UI` methods (`showClearCategoryConfirmation`, `showClearCategoryCancelled`, `showCategoryItemsCleared`).
- Added JUnit tests covering confirmation acceptance, rejection, case-insensitive matching, and the check that other categories remain unaffected.

#### 4. Find item by keyword
I implemented the `find keyword/KEYWORD` feature, which searches all categories for items whose names contain the given keyword. The search is case-insensitive and supports partial matches.
Key aspects of the implementation:
- Added `FindItemByKeywordCommand` to iterate through all categories and items, collecting matches based on a case-insensitive substring check.
- Extended `FindItemParser` to dispatch keyword-based searches correctly.
- Added JUnit tests for exact matches, partial matches, case-insensitive matches, no-match scenarios, and verification that the search does not mutate the inventory.

#### 5. Help command
I implemented the `help` command and the `showHelp()` method in `UI`, which displays a summary of available commands and a link to the full User Guide.

#### 6. FieldParser utility
I implemented the `FieldParser` utility class, which provides a reusable `extractField` method for extracting values between markers in a command string. This utility is used across multiple parsers including `CommonFieldParser` and the category-specific parsers. I also added comprehensive JUnit tests covering all branches: valid extraction with start and end markers, missing markers, null end keys, empty values, trimming behaviour, and multi-word values.

### Contributions to the User Guide
I contributed the user-facing documentation for the features I implemented. In particular, I wrote or substantially updated the sections covering:

- Deleting an item
- Clearing a category
- Finding items by keyword
- Help command
- Field parser

These updates included command formats, examples, expected outcomes, and notes on edge cases so that end users can use the features without inspecting the code.

### Contributions to the Developer Guide
I contributed the technical documentation for the features I implemented. Specifically, I wrote or substantially updated the Developer Guide sections for:
- Delete Item feature — covering feature motivation, high-level design, component-level implementation, command execution flow, error handling and validation, alternatives considered, and manual testing instructions.
- Clear Category feature — covering feature motivation, high-level design, component-level implementation, command execution flow with confirmation prompt logic, error handling and validation, alternatives considered, and manual testing instructions.
- Find Item By Keyword feature — covering feature motivation, high-level design, component-level implementation, command execution flow with case-insensitive substring matching, error handling and validation, alternatives considered, and manual testing instructions.
- Help feature — covering feature motivation, high-level design, component-level implementation, command execution flow, alternatives considered, and current limitations.

I also added the following UML diagrams:
Sequence diagrams:
- [DeleteItemCommandMainFlow.puml](../diagrams/sequence/DeleteItemCommandMainFlow.puml)
- [ClearCategoryCommandMainFlow.puml](../diagrams/sequence/ClearCategoryCommandMainFlow.puml)
- [FindItemByKeywordCommandParseFlow.puml](../diagrams/sequence/FindItemByKeywordCommandParseFlow.puml)
- [FindItemByKeywordCommandMatchingFlow.puml](../diagrams/sequence/FindItemByKeywordCommandMatchingFlow.puml)
- [FindItemByKeywordCommandDisplayFlow.puml](../diagrams/sequence/FindItemByKeywordCommandDisplayFlow.puml)
- [HelpCommandMainFlow.puml](../diagrams/sequence/HelpCommandMainFlow.puml)

Class diagrams:
- [DeleteItemCommandClassDiagram.puml](../diagrams/class/DeleteItemCommandClassDiagram.puml)
- [ClearCategoryCommandClassDiagram.puml](../diagrams/class/ClearCategoryCommandClassDiagram.puml)
- [FindItemByKeywordCommandClassDiagram.puml](../diagrams/class/FindItemByKeywordCommandClassDiagram.puml)
- [HelpCommandClassDiagram.puml](../diagrams/class/HelpCommandClassDiagram.puml)

Object diagrams:
- [DeleteItemCommandObjectDiagram.puml](../diagrams/object/DeleteItemCommandObjectDiagram.puml)
- [ClearCategoryCommandObjectDiagram.puml](../diagrams/object/ClearCategoryCommandObjectDiagram.puml)
- [FindItemByKeywordCommandObjectDiagram.puml](../diagrams/object/FindItemByKeywordCommandObjectDiagram.puml)
- [HelpCommandObjectDiagram.puml](../diagrams/object/HelpCommandObjectDiagram.puml)

### Contributions to Team-Based 

- Maintained the `UI` class as a shared component, updating it when teammates needed new output methods for their features.
- Helped fix Checkstyle and code style issues during integration.
- Helped merge teammates' PRs after reviewing them.
- Helped teammate with PR settings.

### Review and Mentoring Contributions

- [PR #101 review comment](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/101#issuecomment-4174049960)
- [PR #103 review comment](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/103#issuecomment-4174048971)


