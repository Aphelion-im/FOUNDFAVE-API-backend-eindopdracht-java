package online.foundfave.foundfaveapi.exceptions;

import java.io.Serial;

public class ContactFormNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ContactFormNotFoundException() {
        super();
    }

    public ContactFormNotFoundException(String message) {
        super(message);
    }
}
