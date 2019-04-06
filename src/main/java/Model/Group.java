package Model;

import java.util.LinkedList;

public class Group {
    private Message message;
    private Email sender;
    private LinkedList<Email> victims;

    public Group(Message message, Email sender, LinkedList<Email> victims) {
        this.message = message;
        this.sender = sender;
        this.victims = victims;
    }

    public Message getMessage() {
        return message;
    }

    public Email getSender() {
        return sender;
    }

    public LinkedList<Email> getVictims() {
        return victims;
    }
}
