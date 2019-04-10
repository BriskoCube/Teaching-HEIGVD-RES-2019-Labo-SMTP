package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailAddressTest {
    @Test
    public void EmailAddressConstruct() throws EmailAddress.EmailBadFormat {
        String mail = "test@test.ch";

        EmailAddress emailAddress = new EmailAddress(mail);

        assertEquals(emailAddress.getEmail(), mail);
    }

    @Test
    public void EmailAddressForHeader() throws EmailAddress.EmailBadFormat {
        String mail = "test@test.ch";

        EmailAddress emailAddress = new EmailAddress(mail);

        assertEquals(emailAddress.forHeader(), "<" + mail + ">");
    }

    @Test
    public void EmailAddressEquals() throws EmailAddress.EmailBadFormat {
        String mail = "test@test.ch";

        EmailAddress emailAddress = new EmailAddress(mail);

        assertTrue(emailAddress.equals(new EmailAddress(mail)));
    }

    @Test
    public void EmailError() {
        String mail = "~";

        assertThrows(EmailAddress.EmailBadFormat.class, () -> new EmailAddress(mail), "Email format isn't conformed");
    }
}
