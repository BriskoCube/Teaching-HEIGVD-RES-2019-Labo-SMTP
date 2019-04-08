package Model;

import java.util.LinkedList;

public class Group {
    private Message message;
    private EmailAddress sender;
    private LinkedList<EmailAddress> victims;

    public Group(Message message, EmailAddress sender, LinkedList<EmailAddress> victims) {
        this.message = message;
        this.sender = sender;
        this.victims = victims;
    }

    public Message getMessage() {
        return message;
    }

    public EmailAddress getSender() {
        return sender;
    }

    public LinkedList<EmailAddress> getVictims() {
        return victims;
    }
}
