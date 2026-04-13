package seedu.inventorydock.parser;

import seedu.inventorydock.command.SummaryCommand;
import seedu.inventorydock.exception.InvalidCommandException;
import seedu.inventorydock.exception.InventoryDockException;

/**
 * Parses user input for the summary command.
 */
public class SummaryCommandParser {
    /**
     * Parses the given summary command arguments and returns the corresponding {@code SummaryCommand}.
     *
     * @param arguments Raw arguments following the {@code summary} command word.
     * @return Summary command configured with the requested summary mode.
     * @throws InventoryDockException If the summary mode is invalid.
     */
    public SummaryCommand parse(String arguments) throws InventoryDockException {
        String trimmed = arguments.trim();

        if (trimmed.isEmpty()) {
            return new SummaryCommand(SummaryCommand.SummaryType.ALL);
        }

        switch (trimmed.toLowerCase()) {
        case "stock":
            return new SummaryCommand(SummaryCommand.SummaryType.STOCK);
        case "expirydate":
            return new SummaryCommand(SummaryCommand.SummaryType.EXPIRYDATE);
        default:
            throw new InvalidCommandException(
                    "summary only supports 'summary', 'summary stock' or 'summary expirydate'."
            );
        }
    }
}
