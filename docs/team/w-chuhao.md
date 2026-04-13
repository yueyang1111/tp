# Wang Chuhao's Project Portfolio Page

## Project: InventoryDock

InventoryDock is a CLI inventory management application for store managers to track stock by category, quantity, expiry date, and bin location. It supports fast keyboard-driven workflows for adding items, listing the full inventory, and locating stock based on storage-related attributes.

Given below are my contributions to the project.

- **New Feature**: Added the ability to add inventory items into an existing category using a structured command format.
  - What it does: Allows users to create inventory items through the `add` command, with parsing support for common fields, category-based dispatch, subtype construction, duplicate-batch detection, and exception-aware validation.
  - Justification: This is one of the product's core write operations. It is necessary because users need a reliable way to populate the inventory, and the feature has to coordinate validation, item construction, category lookup, duplicate-batch detection, and error handling across multiple classes.
  - Highlights: The implementation covered both command execution and parser flow. I added `AddItemCommand`, extended `Parser`, `AddCommandParser`, and `AddItemCommandParser`, helped maintain separation between parsing and execution logic, included exception handling for invalid input and duplicate logical batches, and added JUnit coverage for add-command behaviour and parser handling.
  - Representative PRs: [#17](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/17), [#22](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/22), [#34](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/34)

- **New Feature**: Added the ability to list the full inventory grouped by category.
  - What it does: Allows users to use the `list` command to view the complete inventory after adding, updating, deleting, or loading items from storage.
  - Justification: This is a foundational read operation that gives users a quick snapshot of current stock and supports many other workflows, such as checking inventory state before update or delete operations.
  - Highlights: I added `ListCommand`, integrated it into the parser flow, added tests to verify expected listing behaviour, and ensured it cooperates with the application's exception-based error-handling flow.
  - Representative PR: [#34](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/34)

- **New Feature**: Added the ability to find items by category.
  - What it does: Allows users to retrieve all items stored in a specified category using `find category/CATEGORY`.
  - Justification: This makes retrieval more efficient by reusing the product's category-based data model directly instead of requiring users to scan the full inventory manually.
  - Highlights: I added `FindItemByCategoryCommand`, extended `FindItemParser` to recognise category-based search input, designed the feature to distinguish between a missing category and an existing but empty category, added JUnit tests for valid matches and invalid input handling, and included exception-aware validation paths.
  - Representative PRs: [#51](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/51), [#68](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/68)

- **New Feature**: Added the ability to find items by bin location.
  - What it does: Allows users to search by exact bin location, bin letter, or bin number using `find bin/BIN_INPUT`.
  - Justification: This is useful for real inventory retrieval because users often remember where an item is stored before they remember its exact name.
  - Highlights: I added `FindItemByBinCommand`, added `BinLocationParser` to validate and normalise bin input, extended `FindItemParser` to dispatch bin searches correctly, implemented matching logic for exact bin, bin letter, and bin number modes, added JUnit tests, and included exception-based handling for invalid bin formats.
  - Representative PR: [#77](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/77)

- **New Feature**: Added the ability to find items by quantity threshold.
  - What it does: Allows users to search for items whose quantity is less than or equal to a specified threshold using `find qty/QUANTITY`.
  - Justification: This supports stock review workflows by helping users identify low-stock items quickly instead of searching for one exact quantity.
  - Highlights: I added `FindItemByQtyCommand`, extended `FindItemParser` to dispatch quantity searches, reused existing quantity validation so only positive integers are accepted, implemented inclusive threshold matching using `<=`, added JUnit tests, and ensured invalid quantity values are handled through explicit exceptions.
  - Representative PRs: [#120](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/120), [#128](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/128)

- **Code contributed**: [Code contribution dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=w09&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=w-chuhao&tabRepo=AY2526S2-CS2113-W09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

- **Project management**:
  - Helped set up the team's GitHub repository and project collaboration workflow during the early project stage.

- **Enhancements to existing features**:
  - Added exception-based error handling across my inventory creation and retrieval features so invalid input and failure cases could be detected and reported more safely.
  - Helped establish the separation between parsing logic and command execution logic, keeping the implementation consistent with the project's command-based architecture.
  - Reorganised test files to align the test package structure with the source package structure, improving maintainability.
  - Helped maintain project quality by fixing Gradle or style-check issues during integration.

- **Community**:
  - Reviewed teammates' PRs and gave concrete code-review feedback on parser structure, model design, logging, storage behaviour, and test quality. Examples include:
  - [#28](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/28) Suggested combining parser-related classes and extracting shared validation/parsing logic to improve abstraction and readability.
  - [#32](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/32) Reviewed the refactor of `AddItemCommandParser` and related UI changes, with feedback on abstraction and code clarity.
  - [#39](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/39) Reviewed assertion and logging changes for delete flows, commenting on consistency and debuggability.
  - [#50](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/50) Reviewed the expiry-date search feature, including parser behaviour and error handling, suggested that user-input errors should throw a `DukeException` instead of being handled directly in-place using ui.show(), to keep failure handling more consistent across the codebase.
  - [#55](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/55) Gave feedback on storage logging noise, reuse of parsing helpers, and maintainability of category-handling logic.
  - [#57](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/57) Commented on the logging feature changes during team integration.
  - [#63](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/63) Helped refine the design of newly added item categories by pushing for a more consistent data model across item types.
  - [#86](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/86) Reviewed parsing improvements and suggested ways to keep item-field design more consistent for future iterations.

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

Diagram files I contributed:
- Sequence diagrams:
- [AddItemCommandParseRoutingFlow](../diagrams/sequence/AddItemCommandParseRoutingFlow.png)
- [AddItemCommandSingleCategoryParsingFlow](../diagrams/sequence/AddItemCommandSingleCategoryParsingFlow.png)
- [AddItemCommandExecutionDisplayFlow](../diagrams/sequence/AddItemCommandExecutionDisplayFlow.png)
- [ListCommandMainFlow](../diagrams/sequence/ListCommandMainFlow.png)
- [FindItemByCategoryCommandParseFlow](../diagrams/sequence/FindItemByCategoryCommandParseFlow.png)
- [FindItemByCategoryCommandMatchingFlow](../diagrams/sequence/FindItemByCategoryCommandMatchingFlow.png)
- [FindItemByCategoryCommandDisplayFlow](../diagrams/sequence/FindItemByCategoryCommandDisplayFlow.png)
- [FindItemByBinCommandParseFlow](../diagrams/sequence/FindItemByBinCommandParseFlow.png)
- [FindItemByBinCommandMatchingFlow](../diagrams/sequence/FindItemByBinCommandMatchingFlow.png)
- [FindItemByBinCommandDisplayFlow](../diagrams/sequence/FindItemByBinCommandDisplayFlow.png)
- [FindItemByQtyCommandParseFlow](../diagrams/sequence/FindItemByQtyCommandParseFlow.png)
- [FindItemByQtyCommandMatchingFlow](../diagrams/sequence/FindItemByQtyCommandMatchingFlow.png)
- [FindItemByQtyCommandDisplayFlow](../diagrams/sequence/FindItemByQtyCommandDisplayFlow.png)
- Class diagrams:
- [AddItemCommandClassDiagram](../diagrams/class/AddItemCommandClassDiagram.png)
- [ListCommandClassDiagram](../diagrams/class/ListCommandClassDiagram.png)
- [FindItemByCategoryCommandClassDiagram](../diagrams/class/FindItemByCategoryCommandClassDiagram.png)
- [FindItemByBinCommandClassDiagram](../diagrams/class/FindItemByBinCommandClassDiagram.png)
- [FindItemByQtyCommandClassDiagram](../diagrams/class/FindItemByQtyCommandClassDiagram.png)
- Object diagrams:
- [AddItemCommandObjectDiagram](../diagrams/object/AddItemCommandObjectDiagram.png)
- [ListCommandObjectDiagram](../diagrams/object/ListCommandObjectDiagram.png)
- [FindItemByCategoryCommandObjectDiagram](../diagrams/object/FindItemByCategoryCommandObjectDiagram.png)
- [FindItemByBinCommandObjectDiagram](../diagrams/object/FindItemByBinCommandObjectDiagram.png)
- [FindItemByQtyCommandObjectDiagram](../diagrams/object/FindItemByQtyCommandObjectDiagram.png)
