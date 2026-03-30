package seedu.duke.command;

import seedu.duke.model.Inventory;
import seedu.duke.ui.UI;

/**
 * Represents a command to display the help message,
 * directing the user to the User Guide.
 */
public class HelpCommand extends Command {

    /**
     * Executes the help command by displaying available
     * commands and a link to the User Guide.
     *
     * @param inventory The inventory (unused).
     * @param ui        The UI to display the help message.
     */
    @Override
    public void execute(Inventory inventory, UI ui) {
        ui.showHelp();
    }
}
