package Smtp;


import java.io.IOException;

public class Sender {
    Client client;
    Email email;

    public static boolean debug = false;

    public Sender(Client client) throws Exception {
        this.client = client;

        hello();
    }

    public boolean send(Email email) {

        this.email = email;

        try {
            sendServerHeader();

            beginData();
            sendData();
            endData();

            return true;
        } catch (IOException e) {
            if(debug)
                System.out.println("EX: " + e.getMessage());
            return false;
        } catch (Exception e) {
            if(debug)
                System.out.println("EX: " + e.getMessage());
            return false;
        }
    }

    private void hello() throws Exception {

        readServer(220);

        sendCmd("EHLO SuperClient\r\n");

        while (readServer(250).hasMore()) ;
    }

    private ServerResponse readServer(int expectedCode) throws Exception {
        String line = client.getReader().readLine();
        ServerResponse serverResponse = new ServerResponse(line);

        if (serverResponse.getStatus() != expectedCode) {
            throw new Exception(line);
        }

        if(debug)
            System.out.println("S: " + line);

        return serverResponse;
    }

    private void sendServerHeader() throws Exception {
        sendCmd("MAIL FROM: " + email.getFrom().forHeader() + "\r\n");
        readServer(250);

        sendCmd("RCPT TO: " + email.getTo().forHeader() + "\r\n");
        readServer(250);
    }

    private void sendData() throws Exception {
        for (Header header: email.getHeaders()) {
            client.getWriter().write((header.toString() + "\r\n").getBytes());
        }

        // end headers
        client.getWriter().write(("\r\n").getBytes());

        client.getWriter().write(email.getMessage().getBody().getBytes());
        client.getWriter().flush();
    }

    private void beginData() throws Exception {
        sendCmd("DATA\r\n");

        readServer(354);
    }

    private void endData() throws Exception {
        sendCmd("\r\n.\r\n");

        readServer(250);
    }

    public void quit() throws Exception {
        sendCmd("QUIT\r\n");

        readServer(221);
    }

    private void sendCmd(String str) throws Exception {
        if(debug)
            System.out.print("C: " + str);

        client.getWriter().write(str.getBytes());
        client.getWriter().flush();
    }
}
