package Config;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigManagerTest {

    @Test
    public void AppConfigOkTest() throws ConfigManager.ConfigException {
        String testConfig = "serverhost=localhost\nserverport=2525";

        InputStream inputStream = new ByteArrayInputStream(testConfig.getBytes(StandardCharsets.UTF_8));

        AppConfigManager appConfigManager = new AppConfigManager(inputStream);
        assertEquals(appConfigManager.serverHost(), "localhost");
        assertEquals(appConfigManager.serverPort(), 2525);

    }

    @Test
    public void AppConfigIpTest() throws ConfigManager.ConfigException {
        String testConfig = "serverhost=192.167.19.23\nserverport=12345";

        InputStream inputStream = new ByteArrayInputStream(testConfig.getBytes(StandardCharsets.UTF_8));

        AppConfigManager appConfigManager = new AppConfigManager(inputStream);
        assertEquals(appConfigManager.serverHost(), "192.167.19.23");
        assertEquals(appConfigManager.serverPort(), 12345);

    }

    @Test
    public void ConfigError() throws ConfigManager.ConfigException {
        String testConfig = "serverhost=\nserverport=sdsa";

        InputStream inputStream = new ByteArrayInputStream(testConfig.getBytes(StandardCharsets.UTF_8));

        AppConfigManager appConfigManager = new AppConfigManager(inputStream);
        assertThrows(ConfigManager.ConfigException.class, () -> appConfigManager.serverPort(), "The server port is not a number.");

        assertThrows(ConfigManager.ConfigException.class, () -> appConfigManager.serverHost(), "The proprety 'serverhost' is not defined or empty.");

    }

}