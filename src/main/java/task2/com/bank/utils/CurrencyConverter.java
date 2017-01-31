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

    public BigDecimal convert(User user, String convertValue, Currency convertCurrency, Currency resultCurrency) {

        Map<Currency, BigDecimal> userBalance = getUserBalance(user);

        BigDecimal convertAmount = stringToBigDecimal(convertValue);
        BigDecimal convertCurrencyOldValue = userBalance.get(convertCurrency);
        BigDecimal resultAmount = new BigDecimal(0);

        if (convertAmount.compareTo(convertCurrencyOldValue) == 1) {
            try {
                throw new InvalidAmountException("Конвертируемая сумма больше остатка");
            } catch (InvalidAmountException e) {
                logger.error("Недостаточно денег на балансе " + convertCurrency + " " + resultCurrency);
            }
        } else {

            Map<String, BigDecimal> rates = getExchangeRates();

            String convertTag = getConvertTagAsString(convertCurrency, resultCurrency);

            BigDecimal course = rates.get(convertTag);
            resultAmount = getResultAmount(convertAmount, course);

            updateUserBalance(convertCurrency, resultCurrency, userBalance, convertAmount, convertCurrencyOldValue, resultAmount);

            try {
                UserUtils.updateUser(user);
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
        }
        logger.info(convertAmount + StringUtils.SPACE + convertCurrency + StringUtils.SPACE + "to" + StringUtils.SPACE + resultCurrency + ": " + resultAmount);
        return resultAmount;
    }

    private void updateUserBalance(Currency convertCurrencyTag, Currency resultCurrencyTag, Map<Currency, BigDecimal> userBalance, BigDecimal convertAmount, BigDecimal convertCurrencyOldValue, BigDecimal resultAmount) {
        BigDecimal resultCurrencyOldValue = userBalance.get(resultCurrencyTag);
        userBalance.put(convertCurrencyTag, convertCurrencyOldValue.subtract(convertAmount));
        userBalance.put(resultCurrencyTag, resultAmount.add(resultCurrencyOldValue));
    }

    private BigDecimal getResultAmount(BigDecimal convertAmount, BigDecimal course) {
        return convertAmount.multiply(course);
    }

    private String getConvertTagAsString(Currency convertCurrency, Currency resultCurrency) {
        return convertCurrency.toString() + "_" + resultCurrency.toString();
    }

    private BigDecimal stringToBigDecimal(String convertValue) {
        return new BigDecimal(convertValue);
    }

    private Map<Currency, BigDecimal> getUserBalance(User user) {
        return user.getCurrencies();
    }

    private Map<String, BigDecimal> getExchangeRates() {
        return ExchangeRates.getRates();
    }


}
