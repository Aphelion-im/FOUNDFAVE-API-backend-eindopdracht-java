package online.foundfave.foundfaveapi.exceptions;

import java.io.Serial;

public class ProfileAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProfileAlreadyExistsException() {
        super();
    }

    public ProfileAlreadyExistsException(String message) {
        super(message);
    }
}
