package task2.com.bank.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Isaev_M.M. on 1/10/2017.
 */
public class ExchangeRates {

    private static final BigDecimal ONE_TO_ONE_INDEX = new BigDecimal(1);

    private static final BigDecimal USD_RUR_INDEX = new BigDecimal(60);
    private static final BigDecimal RUR_USD_INDEX;

    private static final BigDecimal EUR_RUR_INDEX = new BigDecimal(80);
    private static final BigDecimal RUR_EUR_INDEX;

    private static final BigDecimal USD_EUR_INDEX;
    private static final BigDecimal EUR_USD_INDEX;

    private static final Map<String, BigDecimal> rates = new HashMap<String, BigDecimal>(2);


    static {
        RUR_USD_INDEX = getExchangeRate(ONE_TO_ONE_INDEX, USD_RUR_INDEX);
        RUR_EUR_INDEX = getExchangeRate(ONE_TO_ONE_INDEX, EUR_RUR_INDEX);
        USD_EUR_INDEX = getExchangeRate(USD_RUR_INDEX, EUR_RUR_INDEX);
        EUR_USD_INDEX = getExchangeRate(EUR_RUR_INDEX, USD_RUR_INDEX);

        rates.put("USD_RUR", USD_RUR_INDEX);
        rates.put("RUR_USD", RUR_USD_INDEX);
        rates.put("EUR_RUR", EUR_RUR_INDEX);
        rates.put("RUR_EUR", RUR_EUR_INDEX);
        rates.put("USD_EUR", USD_EUR_INDEX);
        rates.put("EUR_USD", EUR_USD_INDEX);
    }

    private static BigDecimal getExchangeRate(BigDecimal dividend, BigDecimal divider) {
        if (divider.compareTo(BigDecimal.ZERO) > 0) {
            return dividend.divide(divider, 5, RoundingMode.CEILING);
        }
        return BigDecimal.valueOf(0);
    }

    public static Map<String, BigDecimal> getRates() {
        return rates;
    }
}
