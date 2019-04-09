package Config;

import Model.EmailAddress;
import Model.Group;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

public class ConfigManagerTest {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.-_";

    protected static Random random = new Random();

    @BeforeAll
    public static void CreateTestConfig() {

    }

    @Test
    public void GroupConfigTest() {

        final List<String> senderEmails = randomEmailList();

        StringBuilder testConfig = new StringBuilder("group_count="+senderEmails.size());

        int listCounter = 1;
        for(String email : senderEmails){
            testConfig.append(String.format("\n%d.sender=%s\n%d.victims_file=victims.utf8", listCounter, email, listCounter));
            listCounter++;
        }

        InputStream inputStream = new ByteArrayInputStream(testConfig.toString().getBytes(StandardCharsets.UTF_8));
        try {

            final List<String> emails = randomEmailList();


            GroupConfigManager conf = new GroupConfigManager(inputStream) {
                @Override
                public InputStream emailListStream(File file) throws ConfigException {
                    String emailConfig = String.join("\n", emails);
                    InputStream emailStream = new ByteArrayInputStream(emailConfig.getBytes(StandardCharsets.UTF_8));
                    return emailStream;
                }
            };

            ArrayList<Group> groups = conf.getGroups();

            assertEquals(senderEmails.size(), groups.size());

            // Check if all emails in config file are in parsed groups
            for (Group group: groups) {
                for (String email: emails) {
                    assertTrue(group.getVictims().contains(new EmailAddress(email)));
                }
            }
        } catch (ConfigManager.ConfigException e) {
            e.printStackTrace();
        }
    }

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

    @Test
    public void AppConfigTest() {
        String testConfig = "serverhost=localhost\nserverport=2525";

        InputStream inputStream = new ByteArrayInputStream(testConfig.getBytes(StandardCharsets.UTF_8));

        try {
            AppConfigManager appConfigManager = new AppConfigManager(inputStream);
            assertEquals(appConfigManager.serverHost(), "localhost");
            assertEquals(appConfigManager.serverPort(), 2525);
        } catch (ConfigManager.ConfigException e) {
            e.printStackTrace();
        }

    }

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {

            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());

            builder.append(ALPHA_NUMERIC_STRING.charAt(character));

        }
        return builder.toString();
    }


    @AfterAll
    public static void RevertTestConfig() {
        System.out.println("yay2");

    }
}
