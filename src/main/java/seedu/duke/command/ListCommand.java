package seedu.duke.command;

import seedu.duke.model.Inventory;

public class ListCommand extends Command {
    @Override
    public void execute(Inventory inventory) {

        inventory.printInventory();
    }
}
