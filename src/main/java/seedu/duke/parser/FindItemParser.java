package seedu.duke.parser;

import seedu.duke.command.Command;
import seedu.duke.command.FindItemByKeywordCommand;
import seedu.duke.command.FindItemByCategoryCommand;
import seedu.duke.command.FindItemByExpiryDateCommand;
import seedu.duke.command.FindItemByBinCommand;
import seedu.duke.exception.DukeException;
import seedu.duke.ui.UI;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses {@code find} commands and creates the command object for the requested
 * search mode such as keyword, category, expiry date, or bin.
 */
public class FindItemParser {
    private static final Logger logger = Logger.getLogger(FindItemParser.class.getName());

    private final UI ui;

    /**
     * Creates a find-command parser.
     *
     * @param ui user interface instance associated with the parser workflow.
     */
    public FindItemParser(UI ui) {
        this.ui = ui;
    }

    /**
     * Parses the arguments following {@code find} and returns the matching search command.
     * Bin searches are normalized and validated before the command is created.
     *
     * @param input raw arguments following the {@code find} command word.
     * @return parsed command ready for execution.
     * @throws DukeException if the search mode or target value is missing or invalid.
     */
    public Command parse(String input) throws DukeException {
        assert input != null : "FindCommandParser received null input.";
        if (input.isEmpty()) {
            logger.log(Level.WARNING, "Find command missing target.");
            throw new DukeException("Please specify what to find. Use: find keyword/KEYWORD, "
                    + "find category/CATEGORY, find expiryDate/DATE, or find bin/BIN.");
        }

        String[] parts = input.split("/", 2);

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            logger.log(Level.WARNING, "Find command missing name.");
            throw new DukeException("Missing name. Use: find keyword/KEYWORD, "
                    + "find category/CATEGORY, find expiryDate/DATE, or find bin/BIN.");
        }

        String type = parts[0].trim().toLowerCase();
        String name = parts[1].trim().toLowerCase();

        switch (type) {
        case "keyword":
            return new FindItemByKeywordCommand(name);
        case "category":
            return new FindItemByCategoryCommand(name);
        case "expirydate":
            return new FindItemByExpiryDateCommand(name);
        case "bin":
            return new FindItemByBinCommand(BinLocationParser.parseSearchInput(name));
        default:
            logger.log(Level.WARNING, "Unknown find type: " + type);
            throw new DukeException("Unknown find type: '" + type + "'. "
                    + "Use: find keyword/KEYWORD, find category/CATEGORY, "
                    + "find expiryDate/DATE, or find bin/BIN.");
        }
    }
}
