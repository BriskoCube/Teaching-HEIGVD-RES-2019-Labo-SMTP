package Config;

import Model.EmailAddress;
import Model.Group;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;

public class ConfigManager {
    protected Properties prop = new Properties();
    private InputStream input;

    private ArrayList<Group> groups = new ArrayList<Group>();

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

    public static class ConfigException extends Exception{
        public ConfigException(String message) {
            super(message);
        }
    }
}
