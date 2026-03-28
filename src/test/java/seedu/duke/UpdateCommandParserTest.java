package seedu.duke;

import org.junit.jupiter.api.Test;
import seedu.duke.command.UpdateItemCommand;
import seedu.duke.exception.DukeException;
import seedu.duke.parser.UpdateCommandParser;
import seedu.duke.ui.UI;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdateCommandParserTest {
    @Test
    public void parse_validInput_returnsUpdateItemCommand() throws DukeException {
        UpdateCommandParser parser = new UpdateCommandParser(new UI());

        assertInstanceOf(UpdateItemCommand.class, parser.parse(
                "category/vegetables index/1 qty/25"));
    }

    @Test
    public void parse_missingCategory_throwsException() {
        UpdateCommandParser parser = new UpdateCommandParser(new UI());

        assertThrows(DukeException.class,
                () -> parser.parse("index/1 qty/25"));
    }

    @Test
    public void parse_noFieldsToUpdate_throwsException() {
        UpdateCommandParser parser = new UpdateCommandParser(new UI());

        assertThrows(DukeException.class,
                () -> parser.parse("category/vegetables index/1"));
    }
}
