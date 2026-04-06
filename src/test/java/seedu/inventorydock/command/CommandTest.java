package seedu.inventorydock.command;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.exception.DukeException;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.ui.UI;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandTest {

    @Test
    public void isExit_defaultImplementation_returnsFalse() {
        Command command = new RecordingCommand();

        assertFalse(command.isExit());
    }

    @Test
    public void execute_concreteSubclass_runsSuccessfully() {
        RecordingCommand command = new RecordingCommand();

        assertDoesNotThrow(() -> command.execute(null, null));
        assertTrue(command.wasExecuted);
    }

    @Test
    public void execute_concreteSubclass_receivesSameArguments() throws DukeException {
        RecordingCommand command = new RecordingCommand();

        Inventory inventory = null;
        UI ui = null;

        command.execute(inventory, ui);

        assertSame(inventory, command.receivedInventory);
        assertSame(ui, command.receivedUi);
    }

    @Test
    public void execute_subclassThrowsDukeException_exceptionPropagates() {
        Command command = new FailingCommand();

        DukeException e = assertThrows(DukeException.class,
                () -> command.execute(null, null));

        assertTrue(e.getMessage().contains("Simulated execute failure"));
    }

    /**
     * Test double that records whether execute() was called
     * and what arguments were passed in.
     */
    private static class RecordingCommand extends Command {
        private boolean wasExecuted = false;
        private Inventory receivedInventory;
        private UI receivedUi;

        @Override
        public void execute(Inventory inventory, UI ui) {
            wasExecuted = true;
            receivedInventory = inventory;
            receivedUi = ui;
        }
    }

    /**
     * Test double that always throws, to verify exception propagation.
     */
    private static class FailingCommand extends Command {
        @Override
        public void execute(Inventory inventory, UI ui) throws DukeException {
            throw new DukeException("Simulated execute failure");
        }
    }
}
