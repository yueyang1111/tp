package seedu.inventorydock.parser;

import seedu.inventorydock.model.Item;

/**
 * Builds normalized identity keys used for duplicate-item checks.
 * The key ignores qty and bin fields so only logical batch identity is compared.
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
            if (token.startsWith("qty/") || token.startsWith("bin/")) {
                continue;
            }
            key.append(token.toLowerCase()).append(" ");
        }

        return key.toString().trim();
    }
}
