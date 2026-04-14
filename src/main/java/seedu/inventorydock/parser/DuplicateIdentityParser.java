package seedu.inventorydock.parser;

import seedu.inventorydock.model.Category;
import seedu.inventorydock.model.Item;

/**
 * Builds normalized identity keys used for duplicate-item checks.
 * The key compares the full stored item identity case-insensitively.
 */
public class DuplicateIdentityParser {
    /**
     * Builds a normalized identity key for duplicate checks.
     *
     * @param category Name of the category containing the item.
     * @param target Item whose identity key is built.
     * @return Normalized identity key.
     */
    public static String buildBatchIdentityKey(String category, Item target) {
        assert category != null : "Category cannot be null when building duplicate identity key.";
        assert target != null : "Item cannot be null when building duplicate identity key.";

        String storageString = target.toStorageString(category);
        StringBuilder key = new StringBuilder();

        String[] tokens = storageString.split(" ");
        for (String token : tokens) {
            key.append(token.toLowerCase()).append(" ");
        }

        return key.toString().trim();
    }

    /**
     * Finds an existing item in the category that has the same duplicate identity as the candidate item.
     * The duplicate identity compares all stored fields case-insensitively.
     *
     * @param category Category to scan.
     * @param candidate Item whose duplicate match is being searched.
     * @return Matching duplicate item, or {@code null} if no duplicate exists.
     */
    public static Item findDuplicateItem(Category category, Item candidate) {
        assert category != null : "Category cannot be null while checking duplicates.";
        assert candidate != null : "Candidate item cannot be null while checking duplicates.";

        String candidateIdentity = buildBatchIdentityKey(category.getName(), candidate);
        for (Item existing : category.getItems()) {
            String existingIdentity = buildBatchIdentityKey(category.getName(), existing);
            if (existingIdentity.equals(candidateIdentity)) {
                return existing;
            }
        }
        return null;
    }
}
