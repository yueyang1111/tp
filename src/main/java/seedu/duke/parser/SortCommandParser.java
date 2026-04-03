package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.SortCommand;
import seedu.duke.exception.DukeException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses {@code sort} commands and creates the command object for the sorting the inventory, based on name, expiry date
 * or quantity.
 */
public class SortCommandParser {
    private static final Logger logger = Logger.getLogger(SortCommandParser.class.getName());

    /**
     * Parses the arguments following {@code sort} and returns the sort command.
     *
     * @param input raw arguments following the {@code sort} command word.
     * @return parsed command ready for execution.
     * @throws DukeException if the sort type is missing or invalid.
     */
    public Command parse(String input) throws DukeException {
        assert input != null : "SortCommandParser received null input.";
        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            logger.log(Level.WARNING, "Sort command missing sort type.");
            throw new DukeException(
                    "Please specify what to sort by. Use: sort name, sort expirydate, or sort qty.");
        }

        String sortType = trimmedInput.toLowerCase();

        switch (sortType) {
        case "name":
        case "expirydate":
        case "qty":
            return new SortCommand(sortType);
        default:
            logger.log(Level.WARNING, "Unknown sort type: " + sortType);
            throw new DukeException("Unknown sort type: '" + sortType + "'. "
                    + "Use: sort name, sort expirydate, or sort qty.");
        }
    }
}
