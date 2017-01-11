package task2.com.bank.dto;

/**
 * @author Isaev_M.M. on 1/9/2017.
 */
public enum Currency {

    RUR, USD, EUR;

    private Currency[] getCurrencies() {
        return Currency.values();
    }

}
