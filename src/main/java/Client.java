import Config.AppConfigManager;
import Config.ConfigManager;
import Config.GroupConfigManager;
import Model.EmailAddress;
import Model.Group;
import Model.Message;
import Smtp.Email;
import Smtp.Sender;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    static final Logger LOG = Logger.getLogger(Client.class.getName());

    public static void main(String [] args) {
        System.out.println("~~~ Super client ready to prank ~~~\n");

        try{
            Message message = new Message("Test", "Super message spam");

            AppConfigManager cm = new AppConfigManager(Client.class.getResourceAsStream("config.properties"));

            GroupConfigManager gm = new GroupConfigManager(Client.class.getResourceAsStream("group.properties"));

            System.out.println("Which configured group would you like to use: ");

            int i = 0;
            for (Group group : gm.getGroups()) {
                System.out.println(i++ + " sender:" + group.getSender());
            }

            Scanner input = new Scanner(System.in);
            Group group = gm.getGroups().get(input.nextInt());
            input.close();

            for (EmailAddress ea : group.getVictims()) {
                Smtp.Client client = new Smtp.Client(cm.serverHost(), cm.serverPort());
                Sender sender = new Sender(client, new Email(message, group.getSender(), ea));

                if(sender.sendCmd()){
                    System.out.println("OK");
                } else {
                    System.out.println("Error");
                }

                client.close();
            }


        } catch (ConfigManager.ConfigException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
