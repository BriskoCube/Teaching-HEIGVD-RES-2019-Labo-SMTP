package Smtp;

import Model.EmailAddress;
import Model.Message;

public class Email {
    private Message message;
    private EmailAddress from, to;

    public Email(Message message, EmailAddress from, EmailAddress to){
        this.message = message;
        this.from = from;
        this.to = to;
    }
}
