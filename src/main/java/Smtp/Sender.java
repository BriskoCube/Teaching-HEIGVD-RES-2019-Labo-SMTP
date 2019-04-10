package Smtp;


import java.io.IOException;

public class Sender {
    Client client;
    Email email;

    public Sender(Client client, Email email) {
        this.client = client;
        this.email = email;
    }

    public boolean send() {


        try {
            hello();

            sendServerHeader();

            beginData();
            sendData();
            endData();

            quit();

            return true;
        } catch (IOException e) {
            System.out.println("EX: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("EX: " + e.getMessage());
            return false;
        }
    }

    private void hello() throws Exception {

        readServer(220);

        send("EHLO SuperClient\r\n");

        while (readServer(250).hasMore()) ;
    }

    private ServerResponse readServer(int expectedCode) throws Exception {
        String line = client.getReader().readLine();
        ServerResponse serverResponse = new ServerResponse(line);

        if (serverResponse.getStatus() != expectedCode) {
            throw new Exception(line);
        }

        System.out.println("S: " + line);

        return serverResponse;
    }

    private void sendServerHeader() throws Exception {
        send("MAIL FROM: " + email.getFrom().forHeader() + "\r\n");
        readServer(250);

        send("RCPT TO: " + email.getTo().forHeader() + "\r\n");
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
        send("DATA\r\n");

        readServer(354);
    }

    private void endData() throws Exception {
        send("\r\n.\r\n");

        readServer(250);
    }

    private void quit() throws Exception {
        send("QUIT\r\n");

        readServer(221);
    }

    private void send(String str) throws Exception {
        System.out.print("C: " + str);

        client.getWriter().write(str.getBytes());
        client.getWriter().flush();
    }
}
