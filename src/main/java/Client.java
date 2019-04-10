import Config.AppConfigManager;
import Config.ConfigManager;
import Smtp.ServerResponse;

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

        try{
            ConfigManager cm = AppConfigManager.fromFile(new File("config.properties"));

            System.out.println(cm.serverHost());
            System.out.println(cm.serverPort());
        } catch (ConfigManager.ConfigException e) {
            e.printStackTrace();
        }

        ServerResponse sr = new ServerResponse("220 smtp.example.com ESMTP Postfix");
        System.out.println(sr.getMessage());
        System.out.println(sr.getStatus());


        /*Client cli = new Client("localhost", 2525);

        String str = "EHLO";

        cli.writer.write(str.getBytes());
        cli.writer.flush();

        String line = cli.reader.readLine();

        System.out.println(line);

        System.out.println("HEY");*/
    }
}
