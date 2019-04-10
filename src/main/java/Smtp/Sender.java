package Smtp;


public class Sender {
    Client client;
    Email email;

    public Sender(Client client, Email email) {
        this.client = client;
        this.email = email;
    }

    public boolean send(){
        return true;
    }

    private boolean hello(){
        return true;
    }
}
