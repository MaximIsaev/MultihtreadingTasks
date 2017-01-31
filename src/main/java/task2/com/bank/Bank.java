package task2.com.bank;

import org.apache.log4j.Logger;
import task2.com.bank.dto.Currency;
import task2.com.bank.dto.User;
import task2.com.bank.dto.exceptions.UserNotFoundException;
import task2.com.bank.utils.CurrencyConverter;
import task2.com.bank.utils.TestUserDataGenerator;
import task2.com.bank.utils.UserUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Isaev_M.M. on 1/9/2017.
 */
public class Bank {

    private static final Logger logger = Logger.getLogger(Bank.class);


    public static void main(String[] args) throws UserNotFoundException {

        String userId;
        if (args.length > 1) {
            userId = TestUserDataGenerator.generate(args[0], Integer.parseInt(args[1]));
        } else {
            userId = "0";
        }

        /**
         * Find user and create him with hardcoded properties
         * if not exist
         */
        User user = UserUtils.findUser(userId);
        if (user == null) {
            Map<Currency, BigDecimal> currency = new HashMap<>();
            currency.put(Currency.RUR, new BigDecimal(3000));
            currency.put(Currency.USD, new BigDecimal(50));
            currency.put(Currency.EUR, new BigDecimal(50));

            user = new User("Акакий", 100500, currency);

            UserUtils.createUser(user);
        }

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(8);

        User finalUser = user;

        executor.scheduleAtFixedRate(() -> new CurrencyConverter().convert(finalUser, "300", Currency.RUR, Currency.USD), 500, 1500, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(() -> new CurrencyConverter().convert(finalUser, "15", Currency.USD, Currency.RUR), 1000, 1500, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(() -> new CurrencyConverter().convert(finalUser, "10", Currency.USD, Currency.EUR), 1500, 1500, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(() -> new CurrencyConverter().convert(finalUser, "400", Currency.RUR, Currency.EUR), 600, 1500, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(() -> new CurrencyConverter().convert(finalUser, "15", Currency.EUR, Currency.RUR), 500, 1500, TimeUnit.MILLISECONDS);


    }


}
