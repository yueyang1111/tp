# Tan Yue Yang - Project Portfolio Page

## Overview
InventoryDock is a CLI based inventory management application that enables users to manage stock through structured 
commands. It supports category-based organisation, field validation, and persistent storage, allowing efficient 
tracking of items by quantity, expiry date, and bin location.

### Summary of Contributions

### Code Contributed

- [Code contribution dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=yueyang1111&tabRepo=AY2526S2-CS2113-W09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFilesGlob=)

### Enhancements Implemented
#### 1. Exception handling and input validation support

I implemented and refined the project's exception-handling flow to ensure that invalid inputs and runtime issues are 
reported clearly to the user instead of causing the application to fail abruptly. This included designing user-facing 
error propagation through the command and parser pipeline.

Key aspects of the implementation:

1. Added and refined DukeException as the main custom exception type for domain-specific errors.
2. Helped standardize how parsing and command-related errors are thrown and surfaced to the UI.
3. Improved validation flow so invalid or incomplete user inputs are detected early and reported with clearer error messages.
4. Helped support more robust command execution by ensuring exceptional cases are handled consistently across components.

This contribution is significant because exception handling affects almost every user-facing feature. It improves the 
reliability of the application and makes the CLI much easier to use and debug.

#### 2. Storage support for persistent inventory data

I worked on the storage component so that inventory data can be saved and loaded across application sessions. 
This allows InventoryDock to retain item information after the program exits, which is essential for practical use.

Key aspects of the implementation:

1. Contributed to the Storage component for reading inventory data from file during startup.
2. Helped implement file creation and loading logic so the application can recover existing saved data automatically.
3. Supported parsing of stored item records back into in-memory objects using the add-item parsing flow.
4. Improved handling of invalid or malformed storage lines so loading failures can be managed more safely.

This contribution is important because persistence is one of the core requirements of an inventory management 
application. Without storage support, users would lose all item data whenever the program closes.

#### 3. Logging support for debugging and maintainability

I added logging support to improve traceability during development and debugging. This made it easier to diagnose 
parser, storage, and command issues without relying only on console prints.

Key aspects of the implementation:

1. Added logging configuration for the application using Java’s logging utilities.
2. Helped set up log file creation and logger initialization.
3. Added log statements in key components such as parsing and storage-related flows.
4. Improved maintainability by making internal execution flow easier to trace during testing and debugging.

This contribution is meaningful because logging is especially useful in a multi-component CLI application where bugs 
may originate from parsing, storage, or command execution rather than a single isolated class.

#### 4. Parsing support for add item functionality

I helped with the parsing logic for the add item command, particularly in validation and supporting the command flow 
that converts structured user input into the correct item objects.

Key aspects of the implementation:

1. Helped refine parsing logic used by the add-item feature.
2. Supported validation of required fields and field ordering for structured command input.
3. Contributed to parser-side checks so malformed commands are rejected before execution.
4. Helped improve the reliability of category-specific parsing for item creation.

This contribution is significant because the add-item feature is one of the product’s main write operations. It depends 
heavily on correct parsing and validation to ensure that user input is translated into valid inventory records.

### Contributions to the User Guide

I contributed to the user-facing documentation for features related to my implementation work. In particular, I wrote or 
updated sections related to:

1. Error handling and expected invalid-input behaviour
2. Storage and data persistence behaviour

These updates helped make the product easier for users to understand without needing to inspect the source code.

### Contributions to the Developer Guide

I contributed technical documentation for the components I worked on, specifically the storage implementation 
and loading flow.

I was responsible for writing the Developer Guide sections related to storage, including explaining how data is 
persisted and reconstructed during application startup.

In addition, I contributed to the non-technical sections of the documentation, such as the overall project description, 
product scope, and user stories, to provide context on the system’s purpose and intended usage.

My contributions included:

1. Implementation details and design rationale
2. Component interaction and control flow
3. Handling of invalid or malformed input
4. Limitations and possible future improvements
5. Manual testing considerations

I also contributed to and updated UML sequence diagrams to illustrate the storage loading flow and overall interaction 
between components.

### Contributions to Team-Based Tasks

1. Helped improve project reliability by refining core infrastructure such as exception handling, storage, and logging.
2. Assisted with integration work related to parser logic and feature interaction.
3. Helped maintain code quality through cleanup, validation improvements, and debugging support.

### Review and Mentoring Contributions

1. Reviewed and responded to feedback related to parser behaviour, validation logic, and maintainability.
2. Helped teammates troubleshoot issues involving parsing, storage, and project structure.
3. Contributed to improving consistency across shared components used by multiple features.
