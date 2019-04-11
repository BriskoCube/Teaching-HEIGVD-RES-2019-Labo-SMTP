package Smtp;


import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

public class Sender {
    private Client client;
    private Email email;
    private boolean authSupported = false;

    public static boolean debug = false;

    public Sender(Client client) throws Exception {
        this.client = client;

        hello();

        if(authSupported) {
            authentication();
        }
    }

    private void authentication() throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.print("Username: ");
        String username = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        input.close();

        sendCmd("AUTH LOGIN\r\n");
        readServer(334);
        sendCmd(Base64.getEncoder().encodeToString(username.getBytes()) + "\r\n");
        readServer(334);
        sendCmd(Base64.getEncoder().encodeToString(password.getBytes()) + "\r\n");
        readServer(235);
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

        ServerResponse response;

        while ((response = readServer(250)).hasMore()) {
            if(response.getMessage().contains("AUTH") && response.getMessage().contains("PLAIN")) {
                authSupported = true;
            }
        }
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
