import Config.ConfigManager;
import Config.MessagesLoader;
import Config.VictimsLoader;
import Model.EmailAddress;
import Model.Group;
import Model.Message;
import Smtp.Email;
import Smtp.Sender;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Spamer {

    static final Logger LOG = Logger.getLogger(Spamer.class.getName());

    static Random random = new Random();

    public static void main(String [] args) {
        System.out.println("Super client ready to prank\n");

        try{
            //Message message = new Message("Test", "Super message spam");

            ConfigManager cm = new ConfigManager(Spamer.class.getResourceAsStream("config.properties"));
            VictimsLoader vl = new VictimsLoader(Spamer.class.getResourceAsStream("victims.list"));
            MessagesLoader ml = new MessagesLoader(Spamer.class.getResourceAsStream("messages.json"));


            List<Message> messages = ml.getMessages();
            List<Group> groups = createGroups(cm.groupCount(), vl.getEmails());

            Smtp.Client client = new Smtp.Client(cm.serverHost(), cm.serverPort());
            Sender sender = new Sender(client);

            for (Group group : groups){
                Message message = messages.get(random.nextInt(messages.size()));
                System.out.printf("\nEmails from: %s\n", group.getSender());

                if( group.getVictims().size() >= 2){
                    for (EmailAddress emailAddress : group.getVictims()){
                        if(sender.send(new Email(message, group.getSender(), emailAddress))){
                            System.out.print("*");
                        } else {
                            LOG.log(Level.SEVERE, "Error while sending");
                        }
                    }
                } else {
                    LOG.log(Level.SEVERE, "You need at least two victims and one sender for each group");
                }
            }

            client.close();

        } catch (ConfigManager.ConfigException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Group> createGroups(int count, List<EmailAddress> emails){
        List<Group> groups = new ArrayList<>();

        int victimCount = emails.size() / count - 1;

        System.out.println(victimCount);

        for (int i = 0; i < count; i++){
            Group group = new Group(getRandomEmail(emails), getMultipleRandomEmail(emails, victimCount));

            groups.add(group);
        }

        return groups;
    }

    public static EmailAddress getRandomEmail(List<EmailAddress> emails){
        int emailId = random.nextInt(emails.size());

        EmailAddress email = emails.get(emailId);
        emails.remove(emailId);

        return email;
    }

    public static List<EmailAddress> getMultipleRandomEmail(List<EmailAddress> emails, int count){
        List<EmailAddress> randomEmails = new ArrayList<>();
        for(int i = 0; i < count; i++){
            randomEmails.add(getRandomEmail(emails));
        }

        return randomEmails;
    }
}
