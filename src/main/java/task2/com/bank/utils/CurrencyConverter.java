package task2.com.bank.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import task2.com.bank.dto.Currency;
import task2.com.bank.dto.User;
import task2.com.bank.dto.exceptions.InvalidAmountException;
import task2.com.bank.dto.exceptions.UserNotFoundException;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Isaev_M.M. on 1/10/2017.
 */
public class CurrencyConverter {

    private static final Logger logger = Logger.getLogger(CurrencyConverter.class);

    public static synchronized BigDecimal convert(User user, String convertValue, Currency convertCurrency, Currency resultCurrency) throws InvalidAmountException, UserNotFoundException {

        Map<Currency, BigDecimal> userBalance = getUserBalance(user);

        BigDecimal convertAmount = stringToBigDecimal(convertValue);
        BigDecimal convertCurrencyOldValue = userBalance.get(convertCurrency);

        if (convertAmount.compareTo(convertCurrencyOldValue) == 1) {
            throw new InvalidAmountException("Конвертируемая сумма больше остатка");
        }

        Map<String, BigDecimal> rates = getExchangeRates();

        String convertTag = getConvertTagAsString(convertCurrency, resultCurrency);

        BigDecimal course = rates.get(convertTag);
        BigDecimal resultAmount = getResultAmount(convertAmount, course);

        updateUserBalance(convertCurrency, resultCurrency, userBalance, convertAmount, convertCurrencyOldValue, resultAmount);

        UserUtils.updateUser(user);
        logger.info(convertAmount + StringUtils.SPACE + convertCurrency + StringUtils.SPACE + "to" + StringUtils.SPACE + resultCurrency + ": " + resultAmount);
        return resultAmount;
    }

    private static synchronized void updateUserBalance(Currency convertCurrencyTag, Currency resultCurrencyTag, Map<Currency, BigDecimal> userBalance, BigDecimal convertAmount, BigDecimal convertCurrencyOldValue, BigDecimal resultAmount) {
        BigDecimal resultCurrencyOldValue = userBalance.get(resultCurrencyTag);
        userBalance.put(convertCurrencyTag, convertCurrencyOldValue.subtract(convertAmount));
        userBalance.put(resultCurrencyTag, resultAmount.add(resultCurrencyOldValue));
    }

    private static BigDecimal getResultAmount(BigDecimal convertAmount, BigDecimal course) {
        return convertAmount.multiply(course);
    }

    private static String getConvertTagAsString(Currency convertCurrency, Currency resultCurrency) {
        return convertCurrency.toString() + "_" + resultCurrency.toString();
    }

    private static BigDecimal stringToBigDecimal(String convertValue) {
        return new BigDecimal(convertValue);
    }

    private static Map<Currency, BigDecimal> getUserBalance(User user) {
        return user.getCurrencies();
    }

    private static Map<String, BigDecimal> getExchangeRates() {
        return ExchangeRates.getRates();
    }


}
