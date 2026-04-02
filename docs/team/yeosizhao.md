# Yeo Si Zhao's Project Portfolio Page

## Project: InventoryDock

InventoryDock is a desktop inventory management application for users who prefer working with a Command Line Interface (CLI). It is written in Java and helps users manage categorized inventory items with details such as quantity, bin location, and expiry date.

Given below are my contributions to the project.

### Project Management and Architecture

**Role:** I was the lead designer of the overall initial architecture, allowing others to start their work based on the overall system. I conscientiously left code reviews for others' PRs to ensure code quality and consistency across the project.

### New Feature: Added the ability to update existing items

**What it does:** Allows the user to update an existing item in a category by specifying its index. The user can modify shared fields such as item name, bin location, quantity, and expiry date without deleting and re-adding the item.

**Justification:** This feature improves the product significantly because inventory records often change over time. Users need a fast way to correct mistakes or revise item details while preserving the rest of the record.

**Highlights:** This enhancement integrates with the existing command-based architecture and required careful validation design. It reuses existing validation logic for quantity and expiry date so that the update command remains consistent with the add command. It also supports updating multiple fields in a single command.

### New Feature: Added the ability to find items by expiry date

**What it does:** Allows the user to search for all items whose expiry date is on or before a specified cutoff date.

**Justification:** This feature improves the product significantly because expiry tracking is a core inventory use case. Users need to quickly identify items that are expiring soon without manually scanning the full inventory.

**Highlights:** This enhancement extends the existing `find` command flow and adds date-based comparison logic across the full inventory. It uses proper date parsing instead of raw string comparison so that invalid inputs are rejected consistently and date comparison remains correct.

### Code contributed

[RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=YeoSiZhao&tabRepo=AY2526S2-CS2113-W09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Documentation

**User Guide:**

- Added documentation for the `update` feature.
- Added documentation for the `find expiryDate` feature.

**Developer Guide:**

- Added implementation details for the `update item` feature.
- Added implementation details for the `find by expiry date` feature.
- Added manual testing instructions for both features.

### Testing

- Added tests for `UpdateItemCommand` and `UpdateCommandParser`.
- Added tests for `FindItemByExpiryDateCommand`.

