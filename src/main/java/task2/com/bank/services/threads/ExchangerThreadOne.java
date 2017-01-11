package task2.com.bank.services.threads;

import org.apache.log4j.Logger;
import task2.com.bank.dto.Currency;
import task2.com.bank.dto.User;
import task2.com.bank.dto.exceptions.InvalidAmountException;
import task2.com.bank.dto.exceptions.UserNotFoundException;
import task2.com.bank.utils.CurrencyConverter;
import task2.com.bank.utils.UserUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Isaev_M.M. on 1/11/2017.
 */
public class ExchangerThreadOne implements Runnable {

    User user;
    private final AtomicInteger integer = new AtomicInteger(5);

    private static final Logger logger = Logger.getLogger(ExchangerThreadOne.class);

    public ExchangerThreadOne(String usedId) {
        try {
            user = UserUtils.findUser(usedId);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            CurrencyConverter.convert(user, "300", Currency.RUR, Currency.USD);
            logger.info("Thread 1");
        } catch (InvalidAmountException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
