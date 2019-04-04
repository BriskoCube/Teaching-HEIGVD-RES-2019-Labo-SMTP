package Config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    Properties prop = new Properties();
    InputStream input = null;

    public ConfigManager() throws ConfigException {
        try{
            input = new FileInputStream("config.properties");
            prop.load(input);
        } catch (FileNotFoundException e) {
            throw new ConfigException("The configuration file is missing.");
        } catch (IOException e) {
            throw new ConfigException("Error while reading the configuration file.");
        }
    }

    public String serverHost() throws ConfigException {
        String serverHost = prop.getProperty("serverhost");

        if(serverHost == null || serverHost.length() == 0)
            throw new ConfigException( "The proprety 'serverhost' is not defined or empty.");

        return serverHost;
    }

    public int serverPort() throws ConfigException {
        try{
            return Integer.parseInt(prop.getProperty("serverport"));
        } catch (NumberFormatException e) {
            throw new ConfigException("The server port is not a number.");
        }
    }

    public class ConfigException extends Exception{
        public ConfigException(String message) {
            super(message);
        }
    }
}
