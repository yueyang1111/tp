# Tan Yue Yang - Project Portfolio Page

## Overview
InventoryDock is a CLI based inventory management application that enables users to manage stock through structured 
commands. It supports category-based organisation, field validation, and persistent storage, allowing efficient 
tracking of items by quantity, expiry date, etc.

### Summary of Contributions

### Code Contributed

- [Code contribution dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=yueyang1111&tabRepo=AY2526S2-CS2113-W09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~other~test-code~functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements Implemented
- **New Feature**: Added storage support for persistent inventory data.
    - What it does: Supports saving and loading inventory data across application sessions.
    - Justification: Persistence is a core requirement for an inventory management application because users should not lose all item data whenever the program closes.
    - Highlights: Contributed to the `Storage` component for loading inventory data on startup, file creation and loading logic, reconstruction of stored records into in memory objects using the add item parsing flow, and safer handling of invalid or malformed storage lines.
    - Representative PR: [#55](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/55)

- **New Feature**: Added logging support for debugging and maintainability.
    - What it does: Improves traceability of parser, storage, and command-related flows during development and debugging.
    - Justification: Logging is especially useful in a multi component CLI application where issues may originate from parsing, storage, or command execution rather than a single isolated class.
    - Highlights: Added logging configuration using Java's logging utilities, helped set up log file creation and logger initialization, and added log statements in key components such as parsing and storage related flows.
    - Representative PR: [#57](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/57)

- **Enhancement**: Improved parsing support for add item functionality.
    - What it does: Strengthens validation and parsing for the add item command so structured user input is converted into the correct item objects.
    - Justification: The add item feature is one of the product's main write operations and depends heavily on correct parsing and validation to ensure valid inventory records are created.
    - Highlights: Refined parsing logic used by the add item feature, supported validation of required fields and field ordering, contributed parser side checks so malformed commands are rejected before execution.
    - Representative PRs: [#28](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/28), [#31](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/31), [#37](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/37), [#46](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/46), [#86](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/86)

- **New Feature**: Added parsing support for the sorting feature.
    - What it does: Supports the `sort` command so users can view inventory items sorted within each category based on different criteria.
    - Justification: This improves usability by allowing users to analyse inventory data more effectively without modifying the stored order.
    - Highlights: Implemented `SortCommandParser`, added validation for supported sort types (`name`, `expirydate`, `qty`), provided clear error messages for missing or invalid sort types, and integrated the parser with the existing command architecture.
    - Representative PR: [#119](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/119)

- **New Feature**: Added summary support for category-based inventory overview.
  - What it does: Supports the `summary`, `summary stock`, and `summary expirydate` commands so users can quickly review each category by item count, tied lowest stock items, and tied earliest expiry items.
  - Justification: This improves usability by giving users a faster way to identify categories that may need attention without scanning the full inventory listing.
  - Highlights: Implemented `SummaryCommand`, added support for multiple summary modes, handled tied lowest stock and earliest expiry items.
  - Representative PR: [#248](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/248)

### Contributions to Team-Based Tasks

1. Helped improve project reliability by refining core infrastructure such as storage, and logging.
2. Assisted with integration work related to parser logic and feature interaction.
3. Helped maintain code quality through cleanup, validation improvements, and debugging support.

### Review and Mentoring Contributions

The following are the reviews I have made to help improve the structure or quality of our code:
[#26](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/26), [#41](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/41), [#75](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/75), [#77](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/77), [#120](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/120)

### Contributions to the User Guide

For the User Guide I wrote the sections for the following:

- [Storage feature](../UserGuide.md#data-storage)
- [Sorting feature](../UserGuide.md#sorting-items-sort)
- [Summary feature](../UserGuide.md#viewing-inventory-summary-summary)

These updates covered storage and data persistence behaviour, sorting command usage, supported sort types, summary command usage,
expected behaviour, and invalid-input handling.

### Contributions to the Developer Guide

As for the Developer Guide, I wrote sections for `Storage`, `Summary` and `Sorting`, detailing the implementation details, 
design rationale, component interaction and control flow, handling of invalid or malformed input, limitations, 
future improvements, and manual testing considerations.

I also contributed to non-technical documentation sections such as the overall project description, product scope, and user stories.

Diagram files I contributed:
- Class diagrams: [ArchitectureDiagram](../diagrams/ArchitectureDiagram.png), [SortingClassDiagram](../diagrams/class/SortingClassDiagram.png), [StorageClassDiagram](../diagrams/class/StorageClassDiagram.png), [SummaryCommandClassDiagram](../diagrams/class/SummaryClassDiagram.png)
- Object diagrams: [SortingObjectDiagram](../diagrams/object/SortingObjectDiagram.png), [SummaryObjectDiagram](../diagrams/object/SummaryObjectDiagram.png)
- Sequence diagrams: [SortCommandDisplayFlow](../diagrams/sequence/SortCommandDisplayFlow.png), [SortCommandParseFlow](../diagrams/sequence/SortCommandParseFlow.png), [SortCommandSortingFlow](../diagrams/sequence/SortCommandSortingFlow.png), [StorageLoadingMainFlow](../diagrams/sequence/StorageLoadingMainFlow.png), [StorageSavingMainFlow](../diagrams/sequence/StorageSavingMainFlow.png), [SummaryCommandMainFlow](../diagrams/sequence/SummaryCommandMainFlow.png)
