package task2.com.bank.dto;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Isaev_M.M. on 1/9/2017.
 */
public class Account {

    private User user;
    private Map<Currency, BigDecimal> currencies;

    public Account() {
    }

    public Account(User user, Map<Currency, BigDecimal> currencies) {
        this.user = user;
        this.currencies = currencies;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Currency, BigDecimal> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<Currency, BigDecimal> currencies) {
        this.currencies = currencies;
    }
}
