package online.foundfave.foundfaveapi.exceptions;

import java.io.Serial;

public class CharacterAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CharacterAlreadyExistsException() {
        super();
    }

    public CharacterAlreadyExistsException(String message) {
        super(message);
    }
}
