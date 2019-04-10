import Config.AppConfigManager;
import Config.ConfigManager;
import Model.EmailAddress;
import Model.Message;
import Smtp.Email;
import Smtp.Sender;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    static final Logger LOG = Logger.getLogger(Client.class.getName());


    private Socket clientSocket = null;
    private BufferedReader reader = null;
    private BufferedOutputStream writer = null;

    public Client(String host, int port) {
        try {
            clientSocket = new Socket(host, port);

            // Prepare output streams
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String [] args) throws IOException {

        System.out.println( );

        try{

            Message message = new Message("Test", "Super message spam");
            Email email = new Email(message, new EmailAddress("julien.quartier@test.ch"), new EmailAddress("sdjh@dg.com"));

            AppConfigManager cm = new AppConfigManager(Client.class.getResourceAsStream("config.properties"));

            Smtp.Client client = new Smtp.Client(cm.serverHost(), cm.serverPort());


            Sender sender = new Sender(client, email);

            if(sender.sendCmd()){
                System.out.println("OK");
            } else {
                System.out.println("Error");
            }


        } catch (ConfigManager.ConfigException e) {
            e.printStackTrace();
        } catch (EmailAddress.EmailBadFormat emailBadFormat) {
            emailBadFormat.printStackTrace();
        }



        /*Client cli = new Client("localhost", 2525);

        String str = "EHLO";

        cli.writer.write(str.getBytes());
        cli.writer.flush();

        String line = cli.reader.readLine();

        System.out.println(line);

        System.out.println("HEY");*/
    }
}
