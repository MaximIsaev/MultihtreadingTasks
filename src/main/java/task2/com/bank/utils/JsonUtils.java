package task2.com.bank.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import task2.com.bank.dto.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Isaev_M.M. on 1/10/2017.
 */
public class JsonUtils {

    private static final Logger logger = Logger.getLogger(JsonUtils.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String FOLDER;
    private static final String LOWER = "_";
    private static final String FILE_NAME_PREFIX = "User";
    private static final String JSON = ".json";

    static {
        FOLDER = getJarPath();
    }

    private static String getJarPath() {
        return new File(JsonUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getPath() + File.separator;
    }

    public static void write(User user) {
        try {
            objectMapper.writeValue(new File(getFilePath(user.getId())), user);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public static User read(String id) throws IOException {
        return objectMapper.readValue(new File(getFilePath(id)), User.class);
    }

    public static void delete(String id) throws FileNotFoundException {
        File foundFile = find(id);

        if (foundFile == null) {
            throw new FileNotFoundException();
        }

        foundFile.delete();

    }

    public static File find(String id) {
        String fileToDelete = getFileName(id);
        File dir = new File(FOLDER);
        File[] foundFiles = dir.listFiles();
        if (foundFiles == null) {
            return null;
        }

        for (File file : foundFiles) {
            if (file.getName().equals(fileToDelete)) {
                return getFile(fileToDelete);
            }
        }
        return null;
    }

    private static File getFile(String fileName) {
        return new File(fileName);
    }

    private static String getFilePath(String id) {
        return FOLDER + FILE_NAME_PREFIX + LOWER + id + JSON;
    }

    private static String getFileName(String id) {
        return FILE_NAME_PREFIX + LOWER + id + JSON;
    }

}
