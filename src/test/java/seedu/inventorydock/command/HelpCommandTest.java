package seedu.inventorydock.command;

import org.junit.jupiter.api.Test;
import seedu.inventorydock.model.Inventory;
import seedu.inventorydock.ui.UI;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HelpCommandTest {

    @Test
    public void execute_validUi_callsShowHelpOnce() {
        HelpCommand helpCommand = new HelpCommand("");
        SpyUi ui = new SpyUi();

        helpCommand.execute(null, ui);

        assertEquals(1, ui.showHelpCallCount);
    }

    @Test
    public void execute_inventoryIsUnused_doesNotThrow() {
        HelpCommand helpCommand = new HelpCommand("");
        SpyUi ui = new SpyUi();
        Inventory inventory = null;

        assertDoesNotThrow(() -> helpCommand.execute(inventory, ui));
        assertEquals(1, ui.showHelpCallCount);
    }

    @Test
    public void execute_nullUi_throwsNullPointerException() {
        HelpCommand helpCommand = new HelpCommand("");

        assertThrows(NullPointerException.class, () -> helpCommand.execute(null, null));
    }

    @Test
    public void isExit_defaultImplementation_returnsFalse() {
        HelpCommand helpCommand = new HelpCommand("");

        assertFalse(helpCommand.isExit());
    }

    @Test
    public void execute_extraArguments_showsErrorAndDoesNotCallShowHelp() {
        HelpCommand helpCommand = new HelpCommand("me");
        SpyUi ui = new SpyUi();

        helpCommand.execute(null, ui);

        assertEquals(0, ui.showHelpCallCount);
        assertEquals(1, ui.showErrorCallCount);
    }

    @Test
    public void execute_whitespaceOnlyArguments_callsShowHelp() {
        HelpCommand helpCommand = new HelpCommand("   ");
        SpyUi ui = new SpyUi();

        helpCommand.execute(null, ui);

        assertEquals(1, ui.showHelpCallCount);
        assertEquals(0, ui.showErrorCallCount);
    }

    /**
     * Test double for UI that records whether showHelp()
     * and showError() were called.
     */
    private static class SpyUi extends UI {
        private int showHelpCallCount = 0;
        private int showErrorCallCount = 0;

        @Override
        public void showHelp() {
            showHelpCallCount++;
        }

        @Override
        public void showError(String label, String message) {
            showErrorCallCount++;
        }
    }
}
