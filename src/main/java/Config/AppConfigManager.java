package Config;

import Model.Group;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class AppConfigManager extends ConfigManager {
    public AppConfigManager(InputStream configStream) throws ConfigException {
        super(configStream);
    }

    public static AppConfigManager fromFile(File filePath) throws ConfigException {
        try {
            return new AppConfigManager(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            throw new ConfigException("The configuration file is missing.");
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
}
