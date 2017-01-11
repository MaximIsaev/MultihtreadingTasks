package task2.com.bank.dto.exceptions;

import java.io.IOException;

/**
 * @author Isaev_M.M. on 1/10/2017.
 */
public class UserNotFoundException extends IOException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
