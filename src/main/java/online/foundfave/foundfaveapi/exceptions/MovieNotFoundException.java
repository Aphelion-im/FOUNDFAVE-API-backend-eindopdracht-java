package online.foundfave.foundfaveapi.exceptions;

import java.io.Serial;

public class MovieNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public MovieNotFoundException() {
        super();
    }

    public MovieNotFoundException(String message) {
        super(message);
    }
}
