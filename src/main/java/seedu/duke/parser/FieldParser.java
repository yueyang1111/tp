package seedu.duke.parser;

public class FieldParser {
    public static String extractField(String input, String field, String nextField) {
        int start = input.indexOf(field);

        if (start == -1) {
            return null;
        }

        start += field.length();
        int end = 0;
        if (nextField == null) {
            end = input.length();
        } else {
            end = input.indexOf(nextField, start);
            if (end == -1) {
                end = input.length();
            }
        }
        return input.substring(start, end).trim();
    }
}
