package seedu.inventorydock.parser;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.model.Item;
import seedu.inventorydock.model.items.Fruit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DuplicateIdentityParserTest {
    @Test
    public void buildBatchIdentityKey_sameLogicalBatchDifferentQtyAndBin_returnsSameKey() {
        Item firstItem = new Fruit("Apple", 2, "A-01", "2026-01-01", "small", true);
        Item secondItem = new Fruit("apple", 10, "B-09", "2026-01-01", "small", true);

        String firstKey = DuplicateIdentityParser.buildBatchIdentityKey("fruits", firstItem);
        String secondKey = DuplicateIdentityParser.buildBatchIdentityKey("fruits", secondItem);

        assertEquals(firstKey, secondKey);
    }

    @Test
    public void buildBatchIdentityKey_differentLogicalBatch_returnsDifferentKey() {
        Item firstItem = new Fruit("Apple", 2, "A-01", "2026-01-01", "small", true);
        Item secondItem = new Fruit("Apple", 10, "B-09", "2026-02-01", "small", true);

        String firstKey = DuplicateIdentityParser.buildBatchIdentityKey("fruits", firstItem);
        String secondKey = DuplicateIdentityParser.buildBatchIdentityKey("fruits", secondItem);

        assertNotEquals(firstKey, secondKey);
    }
}
