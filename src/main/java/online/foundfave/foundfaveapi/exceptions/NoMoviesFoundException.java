package online.foundfave.foundfaveapi.exceptions;

import java.io.Serial;

public class NoMoviesFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NoMoviesFoundException() {
        super();
    }

    public NoMoviesFoundException(String message) {
        super(message);
    }
}
