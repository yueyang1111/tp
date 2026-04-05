package seedu.inventorydock.logging;

import seedu.inventorydock.exception.DukeException;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Sets up logging to write output to a file instead of console.
 */
public class LoggerConfig {
    private final File logFile;

    /**
     * Constructs a LoggerConfig with the specified log file path.
     *
     * @param filePath Path to the log file.
     */
    public LoggerConfig(String filePath) {
        assert filePath != null : "LoggerConfig received null file path.";
        this.logFile = new File(filePath);
    }

    /**
     * Creates the log file if it does not exist
     * .
     * @throws IOException If the file cannot be created.
     */
    private void createFile() throws IOException {
        if (logFile.exists()) {
            return;
        }
        File parent = logFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        logFile.createNewFile();
    }

    /**
     * Set up the global logger configuration. Removes the ConsoleHandler and adds a
     * fileHandler to log output to file.
     *
     * @throws DukeException If logging setup fails.
     */
    public void setup() throws DukeException {
        try {
            createFile();

            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            for (Handler handler : handlers) {
                if (handler instanceof ConsoleHandler) {
                    rootLogger.removeHandler(handler);
                }
            }

            FileHandler fileHandler = new FileHandler(logFile.getPath(), true);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);
        } catch (IOException e) {
            throw new DukeException("Failed to setup logging file.");
        }
    }
}
