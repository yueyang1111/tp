package seedu.duke.logging;

import seedu.duke.exception.DukeException;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {
    private final File logFile;

    public LoggerConfig(String filePath) {
        assert filePath != null : "LoggerConfig received null file path.";
        this.logFile = new File(filePath);
    }

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
