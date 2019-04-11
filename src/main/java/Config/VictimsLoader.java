package Config;

import Model.EmailAddress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VictimsLoader {

    static final Logger LOG = Logger.getLogger(VictimsLoader.class.getName());


    private List<EmailAddress> emails = new ArrayList<>();

    public VictimsLoader(InputStream configStream) throws ConfigManager.ConfigException {
        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(configStream));

            String line;
            while((line = bufferedReader.readLine()) != null){

                try{
                    EmailAddress email = new EmailAddress(line);
                    emails.add(email);
                }catch (EmailAddress.EmailBadFormat emailBadFormat) {
                    LOG.log(Level.WARNING, String.format("The value '%s' is not an email", line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<EmailAddress> getEmails(){
        return new ArrayList<>(emails);
    }

}
