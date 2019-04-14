package Config;

import java.io.*;
import java.util.Properties;

public class ConfigManager {
    private Properties prop = new Properties();

    public ConfigManager(InputStream configStream) throws ConfigException {
        try {
            prop.load(configStream);
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

    public int groupCount() throws ConfigException {
        try{
            return Integer.parseInt(prop.getProperty("groupcount"));
        } catch (NumberFormatException e) {
            throw new ConfigException("Group count is not a number.");
        }
    }

    public static class ConfigException extends Exception{
        public ConfigException(String message) {
            super(message);
        }
    }

}
