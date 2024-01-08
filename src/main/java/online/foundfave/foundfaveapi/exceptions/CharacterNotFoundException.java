package online.foundfave.foundfaveapi.exceptions;

import java.io.Serial;

public class CharacterNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CharacterNotFoundException() {
        super();
    }

    public CharacterNotFoundException(String message) {
        super(message);
    }
}
