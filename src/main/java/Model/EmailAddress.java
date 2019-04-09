package Model;

public class EmailAddress {
    private String email;

    public EmailAddress(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object obj) {
        EmailAddress ea = (EmailAddress)obj;
        return this.email.equals(ea.email);
    }
}
