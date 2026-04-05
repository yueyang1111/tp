package seedu.inventorydock.parser;

/**
 * Utility class for extracting field values from a command string.
 */
public class FieldParser {

    /**
     * Extracts the value between a start marker and an end marker.
     * If endMarker is null, extracts until end of string.
     *
     * @param input    the full input string
     * @param startKey the start marker e.g. "item/"
     * @param endKey   the end marker or null for end of string
     * @return the extracted value trimmed, or null if not found
     */
    public static String extractField(String input,
                                      String startKey,
                                      String endKey) {
        int startIndex = input.indexOf(startKey);
        if (startIndex == -1) {
            return null;
        }

        startIndex += startKey.length();

        int endIndex;
        if (endKey != null) {
            endIndex = input.indexOf(endKey, startIndex);
            if (endIndex == -1) {
                endIndex = input.length();
            }
        } else {
            endIndex = input.length();
        }

        return input.substring(startIndex, endIndex).trim();
    }
}
