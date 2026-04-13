package seedu.inventorydock.command;

import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.ui.UI;

/**
 * Represents a command to display the help message,
 * directing the user to the User Guide.
 */
public class HelpCommand extends Command {

    private final String arguments;

    /**
     * Constructs a HelpCommand with the given arguments.
     *
     * @param arguments The arguments following the help
     *                  command word.
     */
    public HelpCommand(String arguments) {
        this.arguments = arguments;
    }

    /**
     * Executes the help command by displaying available
     * commands and a link to the User Guide.
     * Rejects any extra arguments after the command word.
     *
     * @param inventory The inventory (unused).
     * @param ui        The UI to display the help message.
     */
    @Override
    public void execute(Inventory inventory, UI ui) {
        if (!arguments.trim().isEmpty()) {
            ui.showError("Invalid input",
                    "help command does not take arguments. Use: help");
            return;
        }
        ui.showHelp();
    }
}
