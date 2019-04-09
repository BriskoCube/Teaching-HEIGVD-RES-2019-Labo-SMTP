package Config;

import Model.EmailAddress;
import Model.Group;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class GroupConfigManager extends ConfigManager {

    private ArrayList<Group> groups = new ArrayList<Group>();

    public GroupConfigManager(InputStream configStream) throws ConfigException {
        super(configStream);
        loadGroups();
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    private void loadGroups() throws ConfigException {
        for (int i = 0; i < Integer.parseInt(prop.getProperty("group_count")); ++i) {
            createGroup(i + 1);
        }
    }

    public InputStream emailListStream(File file) throws ConfigException {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new ConfigException("Error while reading one of the victims file.");
        }
    }

    private void createGroup(int groupId) throws ConfigException {
        try {
            LinkedList<EmailAddress> victims = new LinkedList<EmailAddress>();

            File file = new File(prop.getProperty(groupId + ".victims_file"));

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(emailListStream(file), "UTF-8")
            );

            String line;
            while ((line = reader.readLine()) != null) {
                victims.add(new EmailAddress(line));
            }

            reader.close();

            groups.add(new Group(new EmailAddress(prop.getProperty(groupId + ".sender")), victims));
        } catch (FileNotFoundException e) {
            throw new ConfigException("One of the victims file is missing.");
        } catch (IOException e) {
            throw new ConfigException("Error while reading one of the victims file.");
        }
    }
}
