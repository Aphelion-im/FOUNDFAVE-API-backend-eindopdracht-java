package online.foundfave.foundfaveapi.exceptions;

import java.io.Serial;

public class MovieAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public MovieAlreadyExistsException() {
        super();
    }

    public MovieAlreadyExistsException(String message) {
        super(message);
    }
}
