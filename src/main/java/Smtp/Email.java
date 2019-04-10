package Smtp;

import Model.EmailAddress;
import Model.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Email {
    private final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private Message message;
    private EmailAddress from, to;
    private Date date;

    private List<Header> headers = new ArrayList<>();

    public Email(Message message, EmailAddress from, EmailAddress to){
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = new Date();

        addHeader(Header.HeaderType.FROM, from.getEmail());
        addHeader(Header.HeaderType.TO, to.getEmail());
        addHeader(Header.HeaderType.DATE, DATE_FORMATTER.format(this.date));
        addHeader(Header.HeaderType.SUBJECT, message.getSubject());
    }

    public void addHeader(Header.HeaderType headerType, String value){
        headers.add(new Header(headerType, value));
    }
}
