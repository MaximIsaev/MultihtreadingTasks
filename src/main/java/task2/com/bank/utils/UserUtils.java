package task2.com.bank.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import task2.com.bank.dto.User;
import task2.com.bank.dto.exceptions.UserNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Isaev_M.M. on 1/10/2017.
 */
public class UserUtils {

    private static final Logger logger = Logger.getLogger(UserUtils.class);

    public static void createUser(User user) {
        isNull(user);
        JsonUtils.write(user);
        logger.info("Created: " + user.toString());
    }

    public static void deleteUser(String id) throws FileNotFoundException, UserNotFoundException {
        isEmpty(id);
        User user = findUser(id);
        JsonUtils.delete(id);
        logger.info("Deleted: " + user.toString());
    }

    public static User findUser(String id) throws UserNotFoundException {
        try {
            return JsonUtils.read(id);
        } catch (IOException e) {
            throw new UserNotFoundException("User not found!");
        }
    }

    public static synchronized void updateUser(User newUser) throws UserNotFoundException {
        User oldUser;
        oldUser = findUser(newUser.getId());
        oldUser.setName(newUser.getName());
        oldUser.setAge(newUser.getAge());
        oldUser.setCurrencies(newUser.getCurrencies());
        JsonUtils.write(oldUser);
    }

    private static void isNull(User user) {
        if (user == null) {
            throw new NullPointerException("Add user");
        }
    }

    private static void isEmpty(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new NullPointerException("add Id");
        }
    }


}
