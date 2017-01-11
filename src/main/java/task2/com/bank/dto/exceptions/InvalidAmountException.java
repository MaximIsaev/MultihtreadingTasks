package task2.com.bank.dto.exceptions;

/**
 * @author Isaev_M.M. on 1/11/2017.
 */
public class InvalidAmountException extends Exception {


    public InvalidAmountException() {
    }

    public InvalidAmountException(String message) {
        super(message);
    }
}
