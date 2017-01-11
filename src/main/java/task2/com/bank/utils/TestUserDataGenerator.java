package task2.com.bank.utils;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import task2.com.bank.dto.Currency;
import task2.com.bank.dto.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Isaev_M.M. on 1/11/2017.
 */
public class TestUserDataGenerator {



    private static final int HIGH_CURRENCY_BORDER = 3000;
    private static final int LOW_CURRENCY_BORDER = 0;

    public static String generate(String name, int age) {

        if (StringUtils.isEmpty(name) || age <= 0) {
            throw new NullPointerException("Введите имя и возраст пользователя!");
        }

        Map<Currency, BigDecimal> balance = new HashMap<Currency, BigDecimal>();
        Currency[] currencies = Currency.values();
        BigDecimal amount;

        for (int i = 0; i < currencies.length; i++) {
            amount = generateCurrency();
            writeCurrencyToUserBalance(amount, currencies[i], balance);
        }
        User user = new User(name, age, balance);
        UserUtils.createUser(user);
        return user.getId();
    }

    private static BigDecimal generateCurrency() {
        return BigDecimal.valueOf(RandomUtils.nextInt(LOW_CURRENCY_BORDER, HIGH_CURRENCY_BORDER));
    }

    private static void writeCurrencyToUserBalance(BigDecimal amount, Currency currency, Map<Currency, BigDecimal> balance) {
        balance.put(currency, amount);
    }
}
