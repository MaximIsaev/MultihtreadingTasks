package task2.com.bank.dto;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Isaev_M.M. on 1/9/2017.
 */
public class User {

    public static long NUMBER_OF_USERS = 0;


    private String id;
    private String name;
    private int age;
    private Map<Currency, BigDecimal> currencies;

    public User() {
        generateAndSetId();
    }

    public User(String name, int age) {
        generateAndSetId();
        this.name = name;
        this.age = age;
    }

    public User(String name, int age, Map<Currency, BigDecimal> currencies) {
        generateAndSetId();
        this.name = name;
        this.age = age;
        this.currencies = currencies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public static long getNumberOfUsers() {
        return NUMBER_OF_USERS;
    }

    public Map<Currency, BigDecimal> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<Currency, BigDecimal> currencies) {
        this.currencies = currencies;
    }

    private void generateAndSetId() {
        this.id = String.valueOf(NUMBER_OF_USERS);
        NUMBER_OF_USERS++;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", currencies=" + currencies +
                '}';
    }
}
