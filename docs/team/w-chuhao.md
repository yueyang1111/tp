# Wang Chuhao - Project Portfolio Page

## Overview

InventoryDock is a CLI inventory management application for store managers to track stock by category, quantity, expiry date, and bin location. It supports fast keyboard-driven workflows for adding items, listing the full inventory, and locating stock based on storage-related attributes.

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

### Contributions to the User Guide

I contributed the user-facing documentation for the features I implemented. In particular, I wrote or substantially updated the sections covering:

- [Adding an item](../UserGuide.md#adding-an-item-add)
- [Listing all items](../UserGuide.md#listing-all-items-list)
- [Finding items by category](../UserGuide.md#finding-items-by-category-find-category)
- [Finding items by bin](../UserGuide.md#finding-items-by-bin-find-bin)

These updates included command formats, examples, expected outcomes, and command-format notes so that end users can use the features without needing to inspect the code.

Representative PR:


### Contributions to the Developer Guide

I contributed the technical documentation for the main features I worked on. In particular, I wrote or substantially updated the Developer Guide sections for:

- Add Item feature
- List feature
- Find Item By Category feature
- Find Item By Bin feature

My contributions covered:

- feature motivation and user value
- high-level design and component interaction
- execution flow and implementation details
- design decisions and alternatives considered
- current limitations and future improvements
- manual testing instructions

I also added or updated the following UML sequence diagrams:

- [AddItemCommandMainFlow.puml](../diagrams/AddItemCommandMainFlow.puml)
- [ListCommandMainFlow.puml](../diagrams/ListCommandMainFlow.puml)
- [FindItemByCategoryCommandMainFlow.puml](../diagrams/FindItemByCategoryCommandMainFlow.puml)
- [FindItemByBinCommandMainFlow.puml](../diagrams/FindItemByBinCommandMainFlow.puml)


### Contributions to Team-Based Tasks

- Helped set up the team's GitHub repository and project collaboration workflow during the early project stage.
- Helped maintain project quality by fixing Gradle or style-check issues during integration.
- Reorganised test files to align the test package structure with the source package structure, improving maintainability.

### Review and Mentoring Contributions

- Reviewed and responded to code-review feedback on my feature PRs, especially for parser design, search behaviour, and documentation clarity.
- Helped smooth integration of related features by refactoring and cleaning up parser and test structure where needed.


