package Config;

import Model.EmailAddress;
import Model.Group;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;

public class ConfigManager {
    private Properties prop = new Properties();
    private Properties group = new Properties();
    private InputStream input;

    private ArrayList<Group> groups = new ArrayList<Group>();

    public ConfigManager() throws ConfigException {
        load_config("config.properties");
        load_groups("group.properties");
    }

    private void load_config(String configFile) throws ConfigException {
        try{
            input = new FileInputStream(configFile);
            prop.load(input);
            input.close();
        } catch (FileNotFoundException e) {
            throw new ConfigException("The configuration file is missing.");
        } catch (IOException e) {
            throw new ConfigException("Error while reading the configuration file.");
        }
    }

    private void load_groups(String groupsFile) throws ConfigException {
        try{
            input = new FileInputStream(groupsFile);
            group.load(input);
            input.close();

            for (int i = 1; i < Integer.parseInt(group.getProperty("groupnumber")); ++i) {
                createGroup(i);
            }

        } catch (FileNotFoundException e) {
            throw new ConfigException("The group file is missing.");
        } catch (IOException e) {
            throw new ConfigException("Error while reading the group file.");
        }
    }

    private void createGroup(int groupId) throws ConfigException {
        try {
            LinkedList<EmailAddress> victims = new LinkedList<EmailAddress>();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    group.getProperty(groupId + ".victimsfile")),
                            "UTF-8"
                    )
            );

            String line;
            while ((line = reader.readLine()) != null) {
                victims.add(new EmailAddress(line));
            }

            reader.close();

            groups.add(new Group(new EmailAddress(group.getProperty(groupId + ".sender")), victims));
        } catch (FileNotFoundException e) {
            throw new ConfigException("One of the victims file is missing.");
        } catch (IOException e) {
            throw new ConfigException("Error while reading one of the victims file.");
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
