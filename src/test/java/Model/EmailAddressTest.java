package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailAddressTest {
    @Test
    public void EmailAddressConstruct() {
        String mail = "test@test.ch";

        try {
            EmailAddress emailAddress = new EmailAddress(mail);

            assertEquals(emailAddress.getEmail(), mail);
        } catch (EmailAddress.EmailBadFormat e) {
            e.printStackTrace();
        }
    }

    @Test
    public void EmailAddressForHeader() {
        String mail = "test@test.ch";

        try {
            EmailAddress emailAddress = new EmailAddress(mail);

            assertEquals(emailAddress.forHeader(), "<" + mail + ">");
        } catch (EmailAddress.EmailBadFormat e) {
            e.printStackTrace();
        }
    }

    @Test
    public void EmailAddressEquals() {
        String mail = "test@test.ch";

        try {
            EmailAddress emailAddress = new EmailAddress(mail);

            assertTrue(emailAddress.equals(new EmailAddress(mail)));
        } catch (EmailAddress.EmailBadFormat e) {
            e.printStackTrace();
        }
    }

    @Test
    public void EmailError() {
        String mail = "~";

        assertThrows(EmailAddress.EmailBadFormat.class, () -> new EmailAddress(mail), "Email format isn't conformed");
    }
}
