package online.foundfave.foundfaveapi.exceptions;

import java.io.Serial;

public class UsernameNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException() {
        super();
    }

    public UsernameNotFoundException(String message) {
        super(message);
    }


}
