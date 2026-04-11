package seedu.inventorydock.parser;

import seedu.inventorydock.command.Command;
import seedu.inventorydock.command.FindItemByQtyCommand;
import seedu.inventorydock.command.FindItemByKeywordCommand;
import seedu.inventorydock.command.FindItemByCategoryCommand;
import seedu.inventorydock.command.FindItemByExpiryDateCommand;
import seedu.inventorydock.command.FindItemByBinCommand;
import seedu.inventorydock.exception.InventoryDockException;
import seedu.inventorydock.exception.InvalidFilterException;
import seedu.inventorydock.exception.MissingArgumentException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses {@code find} commands and creates the command object for the requested
 * search mode such as keyword, category, expiry date, bin, or quantity.
 */
public class FindItemParser {
    private static final Logger logger = Logger.getLogger(FindItemParser.class.getName());

    /**
     * Parses the arguments following {@code find} and returns the matching search command.
     * Bin searches are normalized and validated before the command is created.
     *
     * @param input raw arguments following the {@code find} command word.
     * @return parsed command ready for execution.
     * @throws InventoryDockException if the search mode or target value is missing or invalid.
     */
    public Command parse(String input) throws InventoryDockException {
        assert input != null : "FindItemParser received null input.";
        if (input.trim().isEmpty()) {
            logger.log(Level.WARNING, "Find command missing target.");
            throw new MissingArgumentException("Please specify what to find. Use: find keyword/KEYWORD, "
                    + "find category/CATEGORY, find expiryDate/DATE, find bin/BIN, or find qty/QTY.");
        }

        String[] parts = input.split("/", 2);

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            logger.log(Level.WARNING, "Find command missing name.");
            throw new MissingArgumentException("Missing name. Use: find keyword/KEYWORD, "
                    + "find category/CATEGORY, find expiryDate/DATE, find bin/BIN, or find qty/QTY.");
        }

        String type = parts[0].trim().toLowerCase();
        String name = parts[1].trim().toLowerCase();

        switch (type) {
        case "keyword":
            return new FindItemByKeywordCommand(name);
        case "category":
            return new FindItemByCategoryCommand(FindItemByCategoryCommand.parseCategoryInput(name));
        case "expirydate":
            return new FindItemByExpiryDateCommand(name);
        case "bin":
            return new FindItemByBinCommand(BinLocationParser.parseSearchInput(name));
        case "qty":
            return new FindItemByQtyCommand(FindItemByQtyCommand.parseQtyInput(name));
        default:
            logger.log(Level.WARNING, "Unknown find type: " + type);
            throw new InvalidFilterException("Unknown find type: '" + type + "'. "
                    + "Use: find keyword/KEYWORD, find category/CATEGORY, "
                    + "find expiryDate/DATE, find bin/BIN, or find qty/QTY.");
        }
    }
}


