package seedu.duke.parser;
import seedu.duke.command.Command;
import seedu.duke.command.ListCommand;

public class ListCommandParser {
    public Command parse(){
        return new ListCommand();
    }

}
