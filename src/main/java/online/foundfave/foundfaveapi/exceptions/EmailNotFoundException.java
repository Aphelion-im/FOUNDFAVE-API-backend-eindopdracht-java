package online.foundfave.foundfaveapi.exceptions;

import java.io.Serial;

public class EmailNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmailNotFoundException() {
        super();
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
