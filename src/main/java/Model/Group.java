package Model;

import java.util.LinkedList;
import java.util.List;

public class Group {
    private Message message;
    private EmailAddress sender;
    private List<EmailAddress> victims;

    public Group(EmailAddress sender, List<EmailAddress> victims) {
        this.sender = sender;
        this.victims = victims;
    }

    public Message getMessage() {
        return message;
    }

    public EmailAddress getSender() {
        return sender;
    }

    public List<EmailAddress> getVictims() {
        return victims;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
