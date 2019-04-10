package Config;

import java.io.*;
import java.util.Properties;

public abstract class ConfigManager {
    protected Properties prop = new Properties();

    public ConfigManager(InputStream configStream) throws ConfigException {
        try {
            prop.load(configStream);
        } catch (IOException e) {
            throw new ConfigException("Error while reading the configuration file.");
        }
    }

    public static class ConfigException extends Exception{
        public ConfigException(String message) {
            super(message);
        }
    }
}
