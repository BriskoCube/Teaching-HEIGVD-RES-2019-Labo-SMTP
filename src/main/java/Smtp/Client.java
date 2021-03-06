package Smtp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public BufferedReader getReader() {
        return reader;
    }

    public BufferedOutputStream getWriter() {
        return writer;
    }

    public void close() {
        try {
            reader.close();
            writer.close();
            clientSocket.close();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
