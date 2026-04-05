# Wang Chuhao - Project Portfolio Page

## Overview

InventoryDock is a CLI inventory management application for store managers to track stock by category, quantity, expiry date, and bin location. It supports fast keyboard-driven workflows for adding items, listing the full inventory, and locating stock based on storage-related attributes.

My main contributions focused on inventory creation and retrieval workflows. I implemented the `add` command, the `list` command, and three search features: `find category`, `find bin`, and `find qty`. I also documented these features in the User Guide and Developer Guide, including the diagrams used to explain their control flow and structure.

## Summary of Contributions

### Code Contributed

- [Code contribution dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=w09&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=w-chuhao&tabRepo=AY2526S2-CS2113-W09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements Implemented

#### 1. Add item command and parsing support

I implemented the core `add` command flow so that users can add inventory items into an existing category using a structured command format. This work included both the command execution logic and the parser pipeline needed to validate common fields, dispatch by category, and construct the correct item subtype.

Key aspects of the implementation:

- Added `AddItemCommand` to insert parsed items into the target category.
- Extended `Parser`, `AddCommandParser`, and `AddItemCommandParser` to support command recognition, validation, and category-based parsing.
- Helped establish the separation between parsing logic and command execution logic, keeping the implementation consistent with the project's command-based architecture.
- Added JUnit coverage for add-command behaviour and parser handling.

This contribution is significant because `add` is one of the product's core write operations. It required coordinating input validation, item construction, category lookup, and error handling across multiple classes.

Representative PRs:

- [#17](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/17)
- [#22](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/22)
- [#34](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/34)

#### 2. Full inventory listing

I implemented the `list` command, which shows the complete inventory grouped by category. This gives users a quick snapshot of current stock after adding, updating, deleting, or loading items from storage.

Key aspects of the implementation:

- Added `ListCommand` as a lightweight read-only command.
- Integrated it into the parser flow so the command can be triggered directly from user input.
- Added tests to verify expected listing behaviour.

Although the feature is simple from the user's point of view, it is important because it is a foundational read operation that supports many other workflows, such as checking current inventory state before running update or delete operations.

#### 3. Search by category

I implemented the `find category/CATEGORY` feature, allowing users to retrieve all items stored in a specified category without scanning the full inventory manually.

Key aspects of the implementation:

- Added `FindItemByCategoryCommand`.
- Extended `FindItemParser` so that it can recognise category-based search input.
- Designed the feature to distinguish between a missing category and an existing but empty category.
- Added JUnit tests for valid matches, empty-category cases, and invalid input handling.

This feature is meaningful because it reuses the product's category-based data model directly instead of doing a less precise global scan, which keeps the implementation small and the behaviour clear.

Representative PRs:

- [#51](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/51)
- [#68](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/68)

#### 4. Search by bin location

I implemented the `find bin/BIN_INPUT` feature, which lets users search by exact bin location, bin letter, or bin number. This is useful for real inventory retrieval because users often remember where an item is stored before they remember its exact name.

Key aspects of the implementation:

- Added `FindItemByBinCommand`.
- Added `BinLocationParser` to validate and normalise bin-related input before command execution.
- Extended `FindItemParser` to dispatch bin searches correctly.
- Implemented matching logic for three search modes: exact bin, bin letter, and bin number.
- Added JUnit tests for parser behaviour and matching logic.

This feature required more than a straightforward exact-match search because it had to support flexible matching while still preventing incorrect overmatching such as treating `A-1` as matching `A-10`.

Representative PR:

- [#77](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/77)

#### 5. Search by quantity threshold

I implemented the `find qty/QUANTITY` feature, which lets users search for items whose quantity is less than or equal to a specified threshold. This is useful for stock review workflows because users often need to identify low-stock items quickly instead of searching for one exact quantity.

Key aspects of the implementation:

- Added `FindItemByQtyCommand`.
- Extended `FindItemParser` to dispatch quantity searches correctly.
- Reused existing quantity validation so the command accepts only positive integers.
- Implemented inclusive threshold matching using `<=`.
- Added JUnit tests for parser behaviour and threshold-based matching.

This feature is meaningful because it turns quantity search into an operational low-stock check rather than a narrow exact-match lookup.

Representative PRs:

- [#120](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/120)
- [#128](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/128)

### Contributions to the User Guide

I contributed the user-facing documentation for the features I implemented. In particular, I wrote or substantially updated the sections covering:

- [Adding an item](../UserGuide.md#adding-an-item-add)
- [Listing all items](../UserGuide.md#listing-all-items-list)
- [Finding items by category](../UserGuide.md#finding-items-by-category-find-category)
- [Finding items by bin](../UserGuide.md#finding-items-by-bin-find-bin)
- [Finding items by quantity](../UserGuide.md#finding-items-by-quantity-find-qty)

These updates included command formats, examples, expected outcomes, and command-format notes so that end users can use the features without needing to inspect the code.

### Contributions to the Developer Guide

I contributed the technical documentation for the main features I worked on. In particular, I wrote or substantially updated the Developer Guide sections for:

- Add Item feature
- List feature
- Find Item By Category feature
- Find Item By Bin feature
- Find Item By Quantity feature

My contributions covered:

- feature motivation and user value
- high-level design and component interaction
- execution flow and implementation details
- design decisions and alternatives considered
- current limitations and future improvements
- manual testing instructions

I also added and updated the UML diagrams used to support these Developer Guide sections. These diagrams document both the dynamic execution flow and the static relationships behind the features I implemented.

Sequence diagrams I contributed:
- [AddItemCommandMainFlow.puml](../diagrams/sequence/AddItemCommandMainFlow.puml)
- [ListCommandMainFlow.puml](../diagrams/sequence/ListCommandMainFlow.puml)
- [FindItemByCategoryCommandMainFlow.puml](../diagrams/sequence/FindItemByCategoryCommandMainFlow.puml)
- [FindItemByBinCommandMainFlow.puml](../diagrams/sequence/FindItemByBinCommandMainFlow.puml)
Class diagrams I contributed:
- [AddItemCommandClassDiagram.puml](../diagrams/class/AddItemCommandClassDiagram.puml)
- [ListCommandClassDiagram.puml](../diagrams/class/ListCommandClassDiagram.puml)
- [FindItemByCategoryCommandClassDiagram.puml](../diagrams/class/FindItemByCategoryCommandClassDiagram.puml)
- [FindItemByBinCommandClassDiagram.puml](../diagrams/class/FindItemByBinCommandClassDiagram.puml)
Object diagrams I contributed:
- [AddItemCommandObjectDiagram.puml](../diagrams/object/AddItemCommandObjectDiagram.puml)
- [ListCommandObjectDiagram.puml](../diagrams/object/ListCommandObjectDiagram.puml)
- [FindItemByCategoryCommandObjectDiagram.puml](../diagrams/object/FindItemByCategoryCommandObjectDiagram.puml)
- [FindItemByBinCommandObjectDiagram.puml](../diagrams/object/FindItemByBinCommandObjectDiagram.puml)

I also updated the diagram set later to keep the generated documentation consistent after the product name was changed from Duke to InventoryDock.

### Contributions to Team-Based Tasks

- Helped set up the team's GitHub repository and project collaboration workflow during the early project stage.
- Helped maintain project quality by fixing Gradle or style-check issues during integration.
- Reorganised test files to align the test package structure with the source package structure, improving maintainability.

### Review and Mentoring Contributions

- Reviewed teammates' PRs and gave concrete code-review feedback on parser structure, model design, logging, storage behaviour, and test quality. Examples include:
- [#28](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/28) Suggested combining parser-related classes and extracting shared validation/parsing logic to improve abstraction and readability.
- [#32](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/32) Reviewed the refactor of `AddItemCommandParser` and related UI changes, with feedback on abstraction and code clarity.
- [#39](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/39) Reviewed assertion and logging changes for delete flows, commenting on consistency and debuggability.
- [#50](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/50) Reviewed the expiry-date search feature, including parser behaviour, logging, error handling, and test coverage.
- [#55](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/55) Gave feedback on storage logging noise, reuse of parsing helpers, and maintainability of category-handling logic.
- [#57](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/57) Commented on the logging feature changes during team integration.
- [#63](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/63) Helped refine the design of newly added item categories by pushing for a more consistent data model across item types.
- [#86](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/86) Reviewed parsing improvements and suggested ways to keep item-field design more consistent for future iterations.





