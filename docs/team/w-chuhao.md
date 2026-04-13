# Wang Chuhao's Project Portfolio Page

## Project: InventoryDock

InventoryDock is a CLI inventory management application for store managers to track stock by category, quantity, expiry date, and bin location through keyboard-driven workflows.

Given below are my contributions to the project.

- **New Feature**: Added the ability to add inventory items into an existing category using a structured command format.
  - What it does: Supports the `add` command with common-field parsing, category-based dispatch, subtype creation, duplicate-batch detection, and exception-aware validation.
  - Justification: `add` is a core write operation and required coordination across validation, item construction, category lookup, duplicate checks, and invalid input error handling.
  - Highlights: Added `AddItemCommand`; extended `Parser`, `AddCommandParser`, and `AddItemCommandParser`; preserved separation between parsing and execution; added exception handling and JUnit coverage.
  - Representative PRs: [#17](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/17), [#22](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/22), [#34](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/34)

- **New Feature**: Added the ability to list the full inventory grouped by category.
  - What it does: Supports the `list` command to show the full inventory after add, update, delete, or load operations.
  - Justification: This is a foundational read operation that helps users review current inventory state before other actions.
  - Highlights: Added `ListCommand`, integrated it into the parser flow, added tests, and aligned it with the application's exception-based handling.
  - Representative PR: [#34](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/34)

- **New Feature**: Added the ability to find items by category.
  - What it does: Supports `find category/CATEGORY` to retrieve all items in a given category.
  - Justification: This improves retrieval efficiency by using the category-based data model directly.
  - Highlights: Added `FindItemByCategoryCommand`, extended `FindItemParser`, handled missing versus empty categories, and added JUnit tests and validation paths.
  - Representative PRs: [#51](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/51), [#68](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/68)

- **New Feature**: Added the ability to find items by bin location.
  - What it does: Supports `find bin/BIN_INPUT` for exact bin, bin letter, or bin number matching.
  - Justification: This is practical for inventory retrieval because users often remember storage location before item name.
  - Highlights: Added `FindItemByBinCommand`, `BinLocationParser`, matching logic for three bin modes, and JUnit tests with exception handling for invalid formats.
  - Representative PR: [#77](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/77)

- **New Feature**: Added the ability to find items by quantity threshold.
  - What it does: Supports `find qty/QUANTITY` to retrieve items with quantity less than or equal to a threshold.
  - Justification: This supports low-stock review workflows more effectively than exact-quantity search.
  - Highlights: Added `FindItemByQtyCommand`, extended `FindItemParser`, reused quantity validation, implemented inclusive `<=` matching, and added JUnit tests and explicit exception handling.
  - Representative PRs: [#120](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/120), [#128](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/128)

- **Code contributed**: [Code contribution dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=w09&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=w-chuhao&tabRepo=AY2526S2-CS2113-W09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

- **Project management**:
  - Helped set up the team's GitHub repository and collaboration workflow.

- **Enhancements to existing features**:
  - Added exception-based error handling across my inventory creation and retrieval features.
  - Helped maintain separation between parsing logic and command execution logic.
  - Reorganised test files to match the source package structure.
  - Helped fix Gradle and style-check issues during integration.

- **Community**:
  - PRs reviewed (with non-trivial review comments): [#28](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/28), [#32](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/32), [#39](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/39), [#50](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/50), [#55](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/55), [#57](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/57), [#63](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/63), [#86](https://github.com/AY2526S2-CS2113-W09-2/tp/pull/86)
  - Reported bugs and suggestions for other teams in the class: helped team `CS2113-T11-2` identify bugs and improvement areas.

### Contributions to the User Guide

I wrote or substantially updated the User Guide sections for:

- [Adding an item](../UserGuide.md#adding-an-item-add)
- [Listing all items](../UserGuide.md#listing-all-items-list)
- [Finding items by category](../UserGuide.md#finding-items-by-category-find-category)
- [Finding items by bin](../UserGuide.md#finding-items-by-bin-find-bin)
- [Finding items by quantity](../UserGuide.md#finding-items-by-quantity-find-qty)

These updates covered command formats, examples, expected outcomes, usage notes, and the shared add-command structure used across categories with different boolean fields.

### Contributions to the Developer Guide

I wrote or substantially updated the Developer Guide sections for Add Item, [List Command](../diagrams/sequence/ListCommandMainFlow.png), Find Item By Category, Find Item By Bin, and Find Item By Quantity.

My contributions covered feature motivation, the split between the fruit-category parse example and the continued execution flow, implementation details, alternatives considered, limitations, future improvements, and manual testing.

I also added and updated UML diagrams for these features.

Diagram files I contributed:
- Sequence diagrams: [AddItemCommandParseRoutingFlow](../diagrams/sequence/AddItemCommandParseRoutingFlow.png) (fruit-category parse example), [AddItemCommandExecutionDisplayFlow](../diagrams/sequence/AddItemCommandExecutionDisplayFlow.png) (continued execution and error handling with the preserved UI -> InventoryDock call chain), [ListCommandMainFlow](../diagrams/sequence/ListCommandMainFlow.png) (main list-command flow), [FindItemByCategoryCommandParseFlow](../diagrams/sequence/FindItemByCategoryCommandParseFlow.png), [FindItemByCategoryCommandMatchingFlow](../diagrams/sequence/FindItemByCategoryCommandMatchingFlow.png), [FindItemByCategoryCommandDisplayFlow](../diagrams/sequence/FindItemByCategoryCommandDisplayFlow.png), [FindItemByBinCommandParseFlow](../diagrams/sequence/FindItemByBinCommandParseFlow.png), [FindItemByBinCommandMatchingFlow](../diagrams/sequence/FindItemByBinCommandMatchingFlow.png), [FindItemByBinCommandDisplayFlow](../diagrams/sequence/FindItemByBinCommandDisplayFlow.png), [FindItemByQtyCommandParseFlow](../diagrams/sequence/FindItemByQtyCommandParseFlow.png), [FindItemByQtyCommandMatchingFlow](../diagrams/sequence/FindItemByQtyCommandMatchingFlow.png), [FindItemByQtyCommandDisplayFlow](../diagrams/sequence/FindItemByQtyCommandDisplayFlow.png)
- Class diagrams: [AddItemCommandClassDiagram](../diagrams/class/AddItemCommandClassDiagram.png), [ListCommandClassDiagram](../diagrams/class/ListCommandClassDiagram.png), [FindItemByCategoryCommandClassDiagram](../diagrams/class/FindItemByCategoryCommandClassDiagram.png), [FindItemByBinCommandClassDiagram](../diagrams/class/FindItemByBinCommandClassDiagram.png), [FindItemByQtyCommandClassDiagram](../diagrams/class/FindItemByQtyCommandClassDiagram.png)
- Object diagrams: [AddItemCommandObjectDiagram](../diagrams/object/AddItemCommandObjectDiagram.png), [ListCommandObjectDiagram](../diagrams/object/ListCommandObjectDiagram.png), [FindItemByCategoryCommandObjectDiagram](../diagrams/object/FindItemByCategoryCommandObjectDiagram.png), [FindItemByBinCommandObjectDiagram](../diagrams/object/FindItemByBinCommandObjectDiagram.png), [FindItemByQtyCommandObjectDiagram](../diagrams/object/FindItemByQtyCommandObjectDiagram.png)





