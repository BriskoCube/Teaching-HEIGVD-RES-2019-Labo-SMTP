package Model;

import java.util.regex.Pattern;

public class EmailAddress {
    private String email;

    public EmailAddress(String email) throws EmailBadFormat {
        if(isValidEmailAddress(email)) {
            this.email = email;
        } else {
            throw new EmailBadFormat("Email format isn't conformed");
        }
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object obj) {
        EmailAddress ea = (EmailAddress)obj;
        return this.email.equals(ea.email);
    }

    @Override
    public String toString() {
        return email;
    }

    public String forHeader() {
        return "<" + email + ">";
    }

    private boolean isValidEmailAddress(String email) {
        // OWASP email regex validation
        Pattern validEmailAddress = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

        return validEmailAddress.matcher(email).matches();
    }

    public static class EmailBadFormat extends Exception{
        public EmailBadFormat(String message) {
            super(message);
        }
    }
}
