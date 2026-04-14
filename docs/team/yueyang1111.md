# Tan Yue Yang - Project Portfolio Page
## Overview
InventoryDock is a CLI inventory management application for store managers to track stock by category, quantity, expiry date, and other structured item fields. It supports persistent storage, validated command parsing, and category-level inventory analysis so users can manage stock efficiently across sessions. I focused on storage, logging, add-item parsing, sorting, and the summary feature.

## Summary of Contributions
### Code Contributed
- [Code Contribution](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=yueyang1111&tabRepo=AY2526S2-CS2113-W09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~other~test-code~functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements Implemented
1. **Storage support** - Implemented and improved persistent storage so inventory data is saved to and loaded from disk across application sessions. Added file creation and loading logic, reconstructed stored records into in memory objects, and handled invalid or malformed storage lines safely.
2. **Logging support** - Added logging infrastructure to improve traceability and debugging across parser, storage, and command-related flows. Set up logger initialization, log file creation, and logging statements in key components.
3. **Add-item parsing improvements** - Strengthened parsing and validation for the `add` command so structured user input is converted into valid item objects. Added checks for required fields, malformed input, and parser side rejection of invalid commands.
4. **Sort command parsing** - Implemented `SortCommandParser` to support the `sort` command with validation for supported sort types such as `name`, `expirydate`, and `qty`. Added clear error handling for missing or invalid sort arguments.
5. **Summary feature** - Implemented `SummaryCommand` to support `summary`, `summary stock`, and `summary expirydate`, giving users a quick category-level overview by item count, lowest stock items, and earliest expiry items.

### Contributions to the User Guide
Wrote or substantially updated the UG sections for: storage, sorting, and summary features - including command formats, usage examples, supported options, expected outcomes, and invalid-input handling.

### Contributions to the Developer Guide
Wrote or substantially updated the DG sections for: `Storage`, `Sorting`, and `Summary` - covering implementation details, design rationale, component interactions, execution flow, malformed-input handling, limitations, future improvements, and manual testing considerations. Also contributed to broader non-technical documentation such as the project description, product scope, and user stories.

### Contributions to Team-Based Tasks
- Helped improve project reliability by refining core infrastructure such as storage and logging.
- Assisted with integration work related to parser logic and feature interaction.
- Helped maintain code quality through cleanup, validation improvements, and debugging support.

### Review and Mentoring Contributions
The following are the reviews I have made to help improve the structure or quality of our code:
[PR #26](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/26), [PR #41](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/41), [PR #75](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/75), [PR #77](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/77), [PR #120](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/120)

### Contributions beyond the Project Team
- Reported 18 bugs / areas of improvement during the PE Dry Run for another team's (W13-3) product.

## Contributions to the User Guide (Extracts)
For the User Guide I wrote the sections for the following:

- [Storage feature](../UserGuide.md#data-storage)
- [Sorting feature](../UserGuide.md#sorting-items-sort)
- [Summary feature](../UserGuide.md#viewing-inventory-summary-summary)

These updates covered storage and data persistence behaviour, sorting command usage, supported sort types, summary command usage,
expected behaviour, and invalid-input handling.

## Contributions to the Developer Guide (Extracts)

As for the Developer Guide, I wrote / updated sections for `Storage`, `Summary` and `Sorting`, detailing the implementation details,
design rationale, component interaction and control flow, handling of invalid or malformed input, limitations,
future improvements, and manual testing considerations.

- [Storage feature](../DeveloperGuide.md#storage-feature)
- [Sorting feature](../DeveloperGuide.md#sort-feature)
- [Summary feature](../DeveloperGuide.md#summary-feature)

I also contributed to non-technical documentation sections such as the overall project description, product scope, and user stories.

Added UML diagrams for these features:

Class diagrams:
- [ArchitectureDiagram](../diagrams/ArchitectureDiagram.png)
- [SortingClassDiagram](../diagrams/class/SortingClassDiagram.png)
- [StorageClassDiagram](../diagrams/class/StorageClassDiagram.png)
- [SummaryClassDiagram](../diagrams/class/SummaryClassDiagram.png)

Object diagrams:
- [SortingObjectDiagram](../diagrams/object/SortingObjectDiagram.png)
- [SummaryObjectDiagram](../diagrams/object/SummaryObjectDiagram.png)

Sequence diagrams:
- [SortCommandDisplayFlow](../diagrams/sequence/SortCommandDisplayFlow.png)
- [SortCommandParseFlow](../diagrams/sequence/SortCommandParseFlow.png)
- [SortCommandSortingFlow](../diagrams/sequence/SortCommandSortingFlow.png)
- [StorageLoadingMainFlow](../diagrams/sequence/StorageLoadingMainFlow.png)
- [StorageSavingMainFlow](../diagrams/sequence/StorageSavingMainFlow.png)
- [SummaryCommandMainFlow](../diagrams/sequence/SummaryCommandMainFlow.png)
