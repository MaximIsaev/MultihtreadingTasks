package task2.com.bank.services.threads;

import org.apache.log4j.Logger;
import task2.com.bank.dto.Currency;
import task2.com.bank.dto.User;
import task2.com.bank.dto.exceptions.InvalidAmountException;
import task2.com.bank.dto.exceptions.UserNotFoundException;
import task2.com.bank.utils.CurrencyConverter;
import task2.com.bank.utils.UserUtils;

/**
 * @author Isaev_M.M. on 1/11/2017.
 */
public class ExchangerThreadTwo implements Runnable {

    User user;
    private static final Logger logger = Logger.getLogger(ExchangerThreadTwo.class);

    public ExchangerThreadTwo(String userId) {
        try {
            user = UserUtils.findUser(userId);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            CurrencyConverter.convert(user, "5", Currency.USD, Currency.RUR);
            logger.info("Thread 2");
        } catch (InvalidAmountException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
