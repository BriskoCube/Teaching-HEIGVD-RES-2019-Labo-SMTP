package Config;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigManagerTest {

    @Test
    public void AppConfigOkTest() {
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

    @Test
    public void AppPortError(){
        String testConfig = "serverhost=localhost\nserverport=sdsa";

        InputStream inputStream = new ByteArrayInputStream(testConfig.getBytes(StandardCharsets.UTF_8));

        AppConfigManager appConfigManager = new AppConfigManager(inputStream);
        System.out.println();
        assertThrows(ConfigManager.ConfigException.class, () -> appConfigManager.serverPort(), "The server port is not a number.")

    }

}