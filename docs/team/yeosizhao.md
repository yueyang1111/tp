# Yeo Si Zhao's Project Portfolio Page

## Project: InventoryDock

InventoryDock is a desktop inventory management application for users who prefer working with a Command Line Interface (CLI). It is written in Java and helps users manage categorized inventory items with details such as quantity, bin location, and expiry date.

Given below are my contributions to the project.

### Code contributed 

[RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=YeoSiZhao&tabRepo=AY2526S2-CS2113-W09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements implemented

**Feature 1: Update existing items**

- **Parsing and command handling:** Implemented support for parsing optional update fields in a single command and mapping them into the existing command pipeline cleanly.
- **Model update logic:** Designed the update flow so the correct item can be identified by category and index, while unchanged fields are preserved safely.
- **Validation reuse:** Reused existing validation logic for shared fields such as quantity and expiry date so update behaviour stays consistent with add behaviour.
- **Feature completeness:** Made the feature practical by supporting multiple common field updates in one command rather than forcing users to re-enter or recreate the item.
- **Implementation challenge:** The command had to reject invalid partial updates cleanly and avoid leaving the inventory in an inconsistent intermediate state.

**Feature 2: Find items by expiry date**

- **Command extension:** Extended the existing `find` command flow to support a semantic date-based search instead of only text-based matching.
- **Date parsing and comparison:** Implemented proper date parsing and comparison logic so results are based on actual dates rather than unsafe string comparison.
- **Inventory-wide scanning:** Designed the feature to scan the full inventory and evaluate all relevant items against a user-supplied cutoff date.
- **Feature completeness:** Made the enhancement useful for realistic inventory workflows by accepting a clear cutoff date and rejecting invalid input reliably.
- **Implementation challenge:** The logic had to work correctly across heterogeneous inventory data while still enforcing consistent date validation and comparison rules.

**Feature 3: Sort items within each category**

- **Sorting logic:** Implemented comparator-based sorting for multiple fields, specifically name, expiry date, and quantity, within the `SortCommand` logic.
- **Design choice:** Structured the sort behaviour so it preserves the original stored ordering of the inventory and generates a sorted view instead of mutating the underlying data.
- **Feature completeness:** Contributed the core logic needed to support three meaningful sort modes for realistic inventory workflows.
- **Implementation challenge:** The command had to generate a sorted view without mutating the real inventory order, since changing stored order could affect index-based commands such as update and delete.

**Feature 4: Exception hierarchy and error handling**

- **Custom exception design:** Created the project's custom exception hierarchy around `InventoryDockException`, including specific exception types such as `MissingArgumentException`, `InvalidCommandException`, `InvalidFilterException`, `InvalidDateException`, `InvalidIndexException`, `CategoryNotFoundException`, `ItemNotFoundException`, and `StorageException`.
- **Error-handling alignment:** Updated error handling across parsers, commands, and storage-related paths so the code throws the correct specific exception instead of relying on overly generic exceptions.
- **Design choice:** Kept the shared base exception so `InventoryDock` can handle failures through one catch path, while still making the source of each failure clearer in the implementation and documentation.
- **Codebase consistency:** Helped clean up stale exception usage and aligned the documented behaviour with the actual exception subtypes used in code.

### Contributions to the UG

- Documented the `update` feature.
- Documented the `find expiryDate` feature.
- Documented the `sort` feature.
- Documented exception-related features.

### Contributions to the DG

- Wrote implementation details for the `update item`, `find by expiry date`, `sort`, and exception hierarchy features.
- Drew the following PlantUML diagrams:
- Class diagrams: `UpdateItemCommandClassDiagram`, `FindItemByExpiryDateCommandClassDiagram`, `ExceptionHierarchyParserClassDiagram`, `ExceptionHierarchyStorageClassDiagram`, and `ExceptionHierarchyInventoryClassDiagram`.
- Object diagrams: `UpdateItemCommandObjectDiagram` and `FindItemByExpiryDateCommandObjectDiagram`.
- Sequence diagrams: `UpdateItemCommandMainFlow`, `FindItemByExpiryDateCommandMatchingFlow`, `FindItemByExpiryDateCommandParseFlow`, and `FindItemByExpiryDateCommandDisplayFlow`.

### Contributions to team-based tasks

- Led the design of the project's initial architecture so teammates could build on a consistent command-driven structure.
- Helped define the responsibilities and interactions of key classes such as `Parser`, `Command`, `Inventory`, `Category`, `Item`, `Storage`, and `UI`.
- Established an extensible model design that relied on polymorphism in the `Item` hierarchy, so shared behaviour could be handled through common abstractions while specific item types could still define their own fields and constraints.
- Reviewed Java logging API documentation and helped standardise how loggers are declared across classes, as well as which logging levels should be used for different situations so logging behaviour stays more consistent across the codebase.

### Review/mentoring contributions

I contributed to team code quality by reviewing pull requests with attention to correctness, architecture consistency, and edge cases in CLI behaviour. Here are the more prominent examples:

- Reviewed [PR #17](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/17), with feedback on parser simplification, reducing duplicate error handling, and refactoring item creation toward a clearer factory-style design pattern.
- Reviewed [PR #10](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/10), with feedback on aligning model code with coding standards, including clearer boolean naming, consistency in state design, and basic defensive checks.
- Reviewed [PR #37](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/37), with feedback on simplifying validation logic by removing redundant checks after required-field validation.
- Reviewed [PR #77](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/77), with feedback on logging levels, separation of parsing and command logic, and import organisation.
