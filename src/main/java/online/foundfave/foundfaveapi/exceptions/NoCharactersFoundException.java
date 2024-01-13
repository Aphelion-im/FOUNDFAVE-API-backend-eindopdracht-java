package online.foundfave.foundfaveapi.exceptions;

import java.io.Serial;

public class NoCharactersFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NoCharactersFoundException() {
        super();
    }

    public NoCharactersFoundException(String message) {
        super(message);
    }
}
