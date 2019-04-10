package Config;

import Model.EmailAddress;
import Model.Group;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GroupConfigManagerTest {

    //Charset for emails
    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+&*-_";

    protected static Random random = new Random();


    @RepeatedTest(5)
    public void GroupConfigTest() throws ConfigManager.ConfigException, EmailAddress.EmailBadFormat {

        // Create random emails for senders
        final List<String> senderEmails = randomEmailList();

        String groupProperties = createGroupProperties(senderEmails);

        // String stream simulating file IO
        InputStream inputStream = new ByteArrayInputStream(groupProperties.getBytes(StandardCharsets.UTF_8));

        final List<String> emails = randomEmailList();


        // Config manager double. No file IO
        GroupConfigManager conf = groupConfigManagerDouble(inputStream, emails);

        ArrayList<Group> groups = conf.getGroups();

        // Same group count
        assertEquals(senderEmails.size(), groups.size());

        // Check if all emails in config file are in parsed groups
        for (int i = 0; i < groups.size(); i++) {
            assertEquals(groups.get(i).getSender(), new EmailAddress(senderEmails.get(i)));
            for (String email : emails) {
                assertTrue(groups.get(i).getVictims().contains(new EmailAddress(email)));
            }
        }
    }

    private String createGroupProperties(List<String> senderEmails) {
        StringBuilder testConfig = new StringBuilder("group_count=" + senderEmails.size());

        int listCounter = 1;
        for (String email : senderEmails) {
            testConfig.append(String.format("\n%d.sender=%s\n%d.victims_file=victims.utf8", listCounter, email, listCounter));
            listCounter++;
        }

        return testConfig.toString();
    }

    /**
     * Config manager double.
     * Replace email file IO by string stream
     *
     * @param inputStream The group.properties file
     * @param emails      Emails to add in group
     * @return
     * @throws ConfigManager.ConfigException
     */
    private GroupConfigManager groupConfigManagerDouble(InputStream inputStream, List<String> emails) throws ConfigManager.ConfigException {
        return new GroupConfigManager(inputStream) {
            @Override
            public InputStream emailListStream(File file) throws ConfigException {
                String emailConfig = String.join("\n", emails);
                InputStream emailStream = new ByteArrayInputStream(emailConfig.getBytes(StandardCharsets.UTF_8));
                return emailStream;
            }
        };
    }

    /**
     * Generate a random amount of random emails.
     *
     * @return List of random emails
     */
    private List<String> randomEmailList() {

        List<String> emails = new ArrayList<String>();

        for (int i = 0; i < randomRange(2, 100); i++) {
            emails.add(String.format("%s@%s.%s",
                    randomAlphaNumeric(randomRange(2, 30)),
                    randomAlphaNumeric(randomRange(2, 30)),
                    randomAlphaNumeric(randomRange(2, 5)
                    )));
        }

        return emails;
    }

    private int randomRange(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {

            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());

            builder.append(ALPHA_NUMERIC_STRING.charAt(character));

        }
        return builder.toString();
    }

}
