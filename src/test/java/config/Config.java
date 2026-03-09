package config;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    static {

        try (InputStream input =
                     Config.class
                             .getClassLoader()
                             .getResourceAsStream("config.properties")) {

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Cannot load config.properties", e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("BASE_URL");
    }

    public static String getToken() {
        return properties.getProperty("YANDEX_TOKEN");
    }
}
