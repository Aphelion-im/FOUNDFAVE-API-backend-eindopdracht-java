package online.foundfave.foundfaveapi.exceptions;

import java.io.Serial;

public class BadCredentialsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public BadCredentialsException() {
        super();
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
