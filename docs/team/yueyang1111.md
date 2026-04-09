# Tan Yue Yang - Project Portfolio Page

## Overview
InventoryDock is a CLI based inventory management application that enables users to manage stock through structured 
commands. It supports category-based organisation, field validation, and persistent storage, allowing efficient 
tracking of items by quantity, expiry date, and bin location.

### Summary of Contributions

### Code Contributed

- [Code contribution dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=yueyang1111&tabRepo=AY2526S2-CS2113-W09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~other~test-code~functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements Implemented
#### 1. Storage support for persistent inventory data

I worked on the storage component so that inventory data can be saved and loaded across application sessions. 
This allows InventoryDock to retain item information after the program exits, which is essential for practical use.

Key aspects of the implementation:

1. Contributed to the Storage component for reading inventory data from file during startup.
2. Helped implement file creation and loading logic so the application can recover existing saved data automatically.
3. Supported parsing of stored item records back into in-memory objects using the add-item parsing flow.
4. Improved handling of invalid or malformed storage lines so loading failures can be managed more safely.

This contribution is important because persistence is one of the core requirements of an inventory management 
application. Without storage support, users would lose all item data whenever the program closes.

Representative PRs:

- [#55](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/55)

#### 2. Logging support for debugging and maintainability

I added logging support to improve traceability during development and debugging. This made it easier to diagnose 
parser, storage, and command issues without relying only on console prints.

Key aspects of the implementation:

1. Added logging configuration for the application using Java’s logging utilities.
2. Helped set up log file creation and logger initialization.
3. Added log statements in key components such as parsing and storage-related flows.
4. Improved maintainability by making internal execution flow easier to trace during testing and debugging.

This contribution is meaningful because logging is especially useful in a multi-component CLI application where bugs 
may originate from parsing, storage, or command execution rather than a single isolated class.

Representative PRs:

- [#57](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/57)

#### 3. Parsing support for add item functionality

I helped with the parsing logic for the add item command, particularly in validation and supporting the command flow 
that converts structured user input into the correct item objects.

Key aspects of the implementation:

1. Helped refine parsing logic used by the add-item feature.
2. Supported validation of required fields and field ordering for structured command input.
3. Contributed to parser side checks so malformed commands are rejected before execution.
4. Helped improve the reliability of category-specific parsing for item creation.

This contribution is significant because the add item feature is one of the product’s main write operations. It depends 
heavily on correct parsing and validation to ensure that user input is translated into valid inventory records.

Representative PRs:

- [#28](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/28)
- [#31](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/31)
- [#37](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/37)
- [#46](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/46)
- [#86](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/86)

#### 4. Sorting feature

I contributed to the sorting feature by implementing the parsing logic and integrating it into the command pipeline.
This feature allows users to view the inventory with items sorted within each category based on different criteria. 
This enhances the usability of the application by enabling users to analyse inventory data more effectively without 
modifying the stored order.

Key aspects of the implementation:

1. Implemented SortCommandParser to handle user input for the sort command.
2. Added validation to ensure that only supported sort types (name, expirydate, qty) are accepted.
3. Provided clear error messages for missing or invalid sort types to improve user experience.
4. Integrated the parser with the existing command architecture to correctly construct and return a SortCommand.
5. Ensured consistency with other command parsers in terms of input handling, validation, and logging.

This contribution is important because it enables the sorting feature to be correctly triggered and used by users, 
ensuring reliable command interpretation and error handling.

Representative PRs:

- [#119](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/119)

### Contributions to the User Guide

I contributed to the user-facing documentation for features related to my implementation work. In particular, I wrote or 
updated sections related to:

1. Error handling and expected invalid-input behaviour.
2. Storage and data persistence behaviour.
3. Sorting feature, including command usage, supported sort types, and expected behaviour.

These updates helped make the product easier for users to understand without needing to inspect the source code.

These are the parts in the user guide that I have wrote or updated:

- [Storage Feature](../UserGuide.md#data-storage)
- [Sorting feature](../UserGuide.md#sorting-items-sort)

### Contributions to the Developer Guide

I contributed technical documentation for the components I worked on, specifically the storage implementation, 
saving and loading flow, as well as the sorting feature.

I was responsible for writing the Developer Guide sections related to storage, including explaining how data is 
persisted and reconstructed during application startup.

For the sorting feature, I documented the design and control flow of the sort command, including how user input is
parsed, validated, and passed into the command pipeline, as well as how the sorted view is generated and displayed.

In addition, I contributed to the non-technical sections of the documentation, such as the overall project description, 
product scope, and user stories, to provide context on the system’s purpose and intended usage.

My contributions included:

1. Implementation details and design rationale (Storage and sorting)
2. Component interaction and control flow
3. Handling of invalid or malformed input
4. Limitations and possible future improvements
5. Manual testing considerations for storage and sorting features

I also contributed to and updated UML sequence diagrams to illustrate the storage loading flow and overall interaction 
between components, as well as the sorting command execution flow.

Class diagrams I contributed:
- [ArchitectureDiagram.puml](../diagrams/ArchitectureDiagram.puml)
- [SortingClassDiagram.puml](../diagrams/class/SortingClassDiagram.puml)
- [StorageClassDiagram.puml](../diagrams/class/StorageClass.puml)

Object diagrams I contributed:
- [SortingObjectDiagram.puml](../diagrams/object/SortingObjectDiagram.puml)
- [StorageLoadingObjectDiagram.puml](../diagrams/object/StorageLoadingObjectDiagram.puml)
- [StorageSavingObjectDiagram.puml](../diagrams/object/StorageSavingObjectDiagram.puml)
  
Sequence diagrams I contributed:
- [SortCommandDisplayFlow.puml](../diagrams/sequence/SortCommandDisplayFlow.puml)
- [SortCommandParseFlow.puml](../diagrams/sequence/SortCommandParseFlow.puml)
- [SortCommadSortingFlow.puml](../diagrams/sequence/SortCommandSortingFlow.puml)
- [StorageLoadingMainFlow.puml](../diagrams/sequence/StorageLoadingMainFlow.puml)
- [StorageSavingMainFlow.puml](../diagrams/sequence/StorageSavingMainFlow.puml)

### Contributions to Team-Based Tasks

1. Helped improve project reliability by refining core infrastructure such as exception handling, storage, and logging.
2. Assisted with integration work related to parser logic and feature interaction.
3. Helped maintain code quality through cleanup, validation improvements, and debugging support.
4. Added automated tests for `Storage`, `LoggerConfig`, `DateParser`, and some of the category parsing.

### Review and Mentoring Contributions

1. Reviewed and responded to feedback related to parser behaviour, validation logic, and maintainability.
2. Helped teammates troubleshoot issues involving parsing, storage, and project structure.
3. Contributed to improving consistency across shared components used by multiple features.

The following are the reviews I have made to help improve the structure or quality of our code:

- [#26](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/26) Reviewed delete feature and suggested ways to use index finding instead of name.
- [#41](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/41) Reviewed the use of logging and assertion.
- [#75](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/75) Reviewed update feature and suggested ways to use index finding instead of name.
- [#77](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/77) Reviewed find by bin feature and suggested ways to improve code quality.
- [#120](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/120) Reviewed find by quantity feature and suggested ways to improve code quality.
