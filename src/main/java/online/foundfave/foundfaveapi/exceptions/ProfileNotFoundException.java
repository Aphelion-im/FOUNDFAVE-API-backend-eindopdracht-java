package online.foundfave.foundfaveapi.exceptions;

import java.io.Serial;

public class ProfileNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProfileNotFoundException() {
        super();
    }

    public ProfileNotFoundException(String message) {
        super(message);
    }
}
