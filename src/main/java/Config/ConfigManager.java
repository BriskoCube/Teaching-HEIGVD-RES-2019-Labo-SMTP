package Config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    Properties prop = new Properties();
    InputStream input = null;

    public ConfigManager(){
        try{
            input = new FileInputStream("config.properties");
            prop.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String serverHost(){
        return prop.getProperty("serverhost");
    }

    public int serverPort(){
        return Integer.parseInt(prop.getProperty("serverhost"));
    }
}
